package controllers;

import java.sql.SQLException;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.GameDAO;
import webserver.WebServerContext;
import models.GameId;
import models.api.request.SendClue;
import models.api.request.addPlayer;
import models.api.response.AvailableRole;

public class GameController
{
    static GameDAO dao = null;
    static Random random = null;

    static void init(WebServerContext context)
    {
        if (dao == null)
        {
            try
            {
                dao = new GameDAO();
            }
            catch(SQLException e)
            {
                context.getResponse().serverError("[GameController.init] " + e.getMessage());
                return;
            }
        }
        if (random == null)
        {
            random = new Random();
        }
    }

    static String newJoinId()
    {
        final int length = 10;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++)
        {
            builder.append((char) (65 + (int) (random.nextFloat() * (90 - 65 + 1))));
        }

        return builder.toString();
    }

    static String newWordMasterReturnCode()
    {
        final int length = 11;
        StringBuilder builder = new StringBuilder();

        builder.append('W');
        for (int i = 0; i < length; i++)
        {
            builder.append((char) (65 + (int) (random.nextFloat() * (90 - 65 + 1))));
        }

        return builder.toString();
    }

    static String newGuessMasterReturnCode()
    {
        final int length = 11;
        StringBuilder builder = new StringBuilder();

        builder.append('G');
        for (int i = 0; i < length; i++)
        {
            builder.append((char) (65 + (int) (random.nextFloat() * (90 - 65 + 1))));
        }

        return builder.toString();
    }

    static public void newGame(WebServerContext context)
    {
        init(context);
        final GameId game = new GameId(newJoinId()); // TODO : check for duplicate

        try
        {
            dao.newGame(game.joinCode());
        }
        catch(SQLException e)
        {
            context.getResponse().serverError("[GameController.newGame] " + e.getMessage());
            return;
        }

        context.getResponse().json(game);
    }

    static public void addPlayer(WebServerContext context)
    {
        init(context);
        final addPlayer player = context.getRequest().extractBody(addPlayer.class);

        if (player.role() == "Word master")
        {
            final String returnCode = newWordMasterReturnCode(); // TODO : check for duplicate
            try
            {
                dao.addWordMaster(player.joinCode(), returnCode, player.pseudo());
            }
            catch(SQLException e)
            {
                context.getResponse().serverError("[GameController.addPlayer] " + e.getMessage());
                return;
            }

            context.getResponse().json(new models.api.response.addPlayer(returnCode));
        }
        if (player.role() == "Guess master")
        {
            final String returnCode = newGuessMasterReturnCode(); // TODO : check for duplicate
            try
            {
                dao.addGuessMaster(player.joinCode(), returnCode, player.pseudo());
            }
            catch(SQLException e)
            {
                context.getResponse().serverError("[GameController.addPlayer] " + e.getMessage());
                return;
            }

            context.getResponse().json(new models.api.response.addPlayer(returnCode));
        }
    }

    static public void availableRole(WebServerContext context)
    {
        init(context);
        final GameId gameId = context.getRequest().extractBody(GameId.class);

        try
        {
            context.getResponse().json(new AvailableRole(dao.isWordMasterAvailable(gameId.joinCode()), dao.isGuessMasterAvailable(gameId.joinCode())));

        }
        catch(SQLException e)
        {
            context.getResponse().serverError("[GameController.availableRole] " + e.getMessage());
            return;
        }
    }

    static void sendClue(WebServerContext context)
    {
        init(context);
        final SendClue sendClue = context.getRequest().extractBody(SendClue.class);

        String returnCode = "";
        try
        {
            String joinCode = dao.getAssociatedJoinCode(returnCode);
            returnCode = dao.guessMasterReturnCode(joinCode);
        }
        catch(SQLException e)
        {
            context.getResponse().serverError("[GameController.sendClue] " + e.getMessage());
            return;
        }

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        context.getSSE().emit(returnCode, gson.toJson(sendClue.clue()));
    }

    static void guessCard(WebServerContext context)
    {
        init(context);


    }
}
