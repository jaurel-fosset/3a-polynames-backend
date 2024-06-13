package controllers;

import java.sql.SQLException;
import java.util.Random;

import dao.GameDAO;
import webserver.WebServerContext;
import models.GameId;
import models.api.request.addPlayer;
import models.api.response.AvailableRole;
import models.database.request.game.AddWordMaster;
import models.database.request.game.AddGuessMaster;

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
        GameId game = new GameId(newJoinId()); // TODO : check for duplicate

        try
        {
            dao.newGame(game);
        }
        catch(SQLException e)
        {
            context.getResponse().serverError("[GameController.newGame] " + e.getMessage());
        }

        context.getResponse().json(game);
    }

    static public void addPlayer(WebServerContext context)
    {
        addPlayer player = context.getRequest().extractBody(addPlayer.class);

        if (player.role() == "Word master")
        {
            String returnCode = newWordMasterReturnCode(); // TODO : check for duplicate
            try
            {
                dao.addWordMaster(new GameId(player.joinCode()), new AddWordMaster(returnCode, player.pseudo()));
            }
            catch(SQLException e)
            {
                context.getResponse().serverError("[GameController.addPlayer] " + e.getMessage());
            }

            context.getResponse().json(new models.api.response.addPlayer(returnCode));
        }
        if (player.role() == "Guess master")
        {
            String returnCode = newGuessMasterReturnCode(); // TODO : check for duplicate
            try
            {
                dao.addGuessMaster(new GameId(player.joinCode()), new AddGuessMaster(returnCode, player.pseudo()));
            }
            catch(SQLException e)
            {
                context.getResponse().serverError("[GameController.addPlayer] " + e.getMessage());
            }

            context.getResponse().json(new models.api.response.addPlayer(returnCode));
        }
    }

    static public void availableRole(WebServerContext context)
    {
        GameId gameId = context.getRequest().extractBody(GameId.class);

        try
        {
            context.getResponse().json(new AvailableRole(dao.isWordMasterAvailable(gameId), dao.isGuessMasterAvailable(gameId)));

        }
        catch(SQLException e)
        {
            context.getResponse().serverError("[GameController.availableRole] " + e.getMessage());
        }
    }
}
