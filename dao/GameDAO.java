package dao;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLDatabase;
import database.PolyNamesDatabase;
import models.GameId;
import models.database.request.game.AddGuessMaster;
import models.database.request.game.AddWordMaster;

public class GameDAO
{
    private MySQLDatabase database;

    public GameDAO() throws SQLException
    {
        this.database = new PolyNamesDatabase();
    }

    public void newGame(GameId gameId) throws SQLException
    {
        PreparedStatement createNewGame = this.database.prepareStatement("INSERT INTO `Games` (joinCode) VALUES (?);");
        createNewGame.setString(1, gameId.joinCode());
        createNewGame.execute();
    }

    public void addWordMaster(GameId gameId, AddWordMaster wordMaster) throws SQLException
    {
        PreparedStatement addPlayer = this.database.prepareStatement("INSERT INTO `Players` (returnCode, pseudo) VALUES (?, ?);");
        addPlayer.setString(1, wordMaster.returnCode());
        addPlayer.setString(2, wordMaster.pseudo());
        addPlayer.execute();

        PreparedStatement updateGame = this.database.prepareStatement("UPDATE `Games` SET idWordMaster = LAST_INSERT_ID() WHERE joinCode = ?;");
        updateGame.setString(1, gameId.joinCode());
        updateGame.execute();
    }

    public void addGuessMaster(GameId gameId, AddGuessMaster guessMaster) throws SQLException
    {
        PreparedStatement addPlayer = this.database.prepareStatement("INSERT INTO `Players` (returnCode, pseudo) VALUES (?, ?);");
        addPlayer.setString(1, guessMaster.returnCode());
        addPlayer.setString(2, guessMaster.pseudo());
        addPlayer.execute();

        PreparedStatement updateGame = this.database.prepareStatement("UPDATE `Games` SET idGuessMaster = LAST_INSERT_ID() WHERE joinCode = ?;");
        updateGame.setString(1, gameId.joinCode());
        updateGame.execute();
    }

    public void addGrid(GameId gameId) throws SQLException
    {
        PreparedStatement addGrid = this.database.prepareStatement("INSERT INTO `Grids` VALUES(DEFAULT)");
        addGrid.execute();

        PreparedStatement updateGame = this.database.prepareStatement("UPDATE `Games` SET idGrid = LAST_INSERT_ID() WHERE joinCode = ?;");
        updateGame.setString(1, gameId.joinCode());
        updateGame.execute();
    }

    public boolean isWordMasterAvailable(GameId gameId) throws SQLException
    {
        PreparedStatement getIdWordMaster = this.database.prepareStatement("SELECT `idWordMaster` FROM `Games` WHERE joinCode = ?");
        getIdWordMaster.setString(1, gameId.joinCode());

        ResultSet result = getIdWordMaster.executeQuery();
        result.next();
        int idWordMaster = result.getInt("idWordMaster");

        if (idWordMaster == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isGuessMasterAvailable(GameId gameId) throws SQLException
    {
        PreparedStatement getIdGuessMaster = this.database.prepareStatement("SELECT `idGuessMaster` FROM `Games` WHERE joinCode = ?");
        getIdGuessMaster.setString(1, gameId.joinCode());

        ResultSet result = getIdGuessMaster.executeQuery();
        result.next();
        int idGuessMaster = result.getInt("idGuessMaster");

        if (idGuessMaster == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
