package dao;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import database.MySQLDatabase;
import database.PolyNamesDatabase;

public class GameDAO
{
    private MySQLDatabase database;

    public GameDAO() throws SQLException
    {
        this.database = new PolyNamesDatabase();
    }

    public void newGame(String joinCode) throws SQLException
    {
        PreparedStatement createNewGame = this.database.prepareStatement("INSERT INTO `Games` (joinCode) VALUES (?);");
        createNewGame.setString(1, joinCode);
        createNewGame.execute();
    }

    public void addWordMaster(String joinCode, String returnCode, String pseudo) throws SQLException
    {
        PreparedStatement addPlayer = this.database.prepareStatement("INSERT INTO `Players` (returnCode, pseudo) VALUES (?, ?);");
        addPlayer.setString(1, returnCode);
        addPlayer.setString(2, pseudo);
        addPlayer.execute();

        PreparedStatement updateGame = this.database.prepareStatement("UPDATE `Games` SET idWordMaster = LAST_INSERT_ID() WHERE joinCode = ?;");
        updateGame.setString(1, joinCode);
        updateGame.execute();
    }

    public void addGuessMaster(String joinCode, String returnCode, String pseudo) throws SQLException
    {
        PreparedStatement addPlayer = this.database.prepareStatement("INSERT INTO `Players` (returnCode, pseudo) VALUES (?, ?);");
        addPlayer.setString(1, returnCode);
        addPlayer.setString(2, pseudo);
        addPlayer.execute();

        PreparedStatement updateGame = this.database.prepareStatement("UPDATE `Games` SET idGuessMaster = LAST_INSERT_ID() WHERE joinCode = ?;");
        updateGame.setString(1, joinCode);
        updateGame.execute();
    }

    public void addGrid(String joinCode) throws SQLException
    {
        PreparedStatement addGrid = this.database.prepareStatement("INSERT INTO `Grids` VALUES(DEFAULT)");
        addGrid.execute();

        PreparedStatement updateGame = this.database.prepareStatement("UPDATE `Games` SET idGrid = LAST_INSERT_ID() WHERE joinCode = ?;");
        updateGame.setString(1, joinCode);
        updateGame.execute();
    }

    public boolean isWordMasterAvailable(String joinCode) throws SQLException
    {
        PreparedStatement getIdWordMaster = this.database.prepareStatement("SELECT `idWordMaster` FROM `Games` WHERE joinCode = ?");
        getIdWordMaster.setString(1, joinCode);

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

    public boolean isGuessMasterAvailable(String joinCode) throws SQLException
    {
        PreparedStatement getIdGuessMaster = this.database.prepareStatement("SELECT `idGuessMaster` FROM `Games` WHERE joinCode = ?");
        getIdGuessMaster.setString(1, joinCode);

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

    public String getAssociatedJoinCode(String returnCode) throws SQLException
    {
        PreparedStatement getJoinCode = this.database.prepareStatement("SELECT `Games`.`joinCode` FROM `Games` CROSS JOIN `Players` WHERE `Players`.`returnCode` = ? ;");
        getJoinCode.setString(1, returnCode);

        ResultSet result = getJoinCode.executeQuery();
        result.next();
        return result.getString("joinCode");
    }

    public String guessMasterReturnCode(String joinCode) throws SQLException
    {
        PreparedStatement getReturnCode = this.database.prepareStatement("SELECT `Players`.`returnCode` FROM `Games` INNER JOIN `Players` ON `Games`.`idGuessMaster` = `Players`.`id` WHERE `Games`.`joinCode` = ? ;");
        getReturnCode.setString(1, joinCode);

        ResultSet result = getReturnCode.executeQuery();
        result.next();
        return result.getString("returnCode");
    }

    public String wordMasterReturnCode(String joinCode) throws SQLException
    {
        PreparedStatement getReturnCode = this.database.prepareStatement("SELECT `Players`.`returnCode` FROM `Games` INNER JOIN `Players` ON `Games`.`idWordMaster` = `Players`.`id` WHERE `Games`.`joinCode` = ? ;");
        getReturnCode.setString(1, joinCode);

        ResultSet result = getReturnCode.executeQuery();
        result.next();
        return result.getString("returnCode");
    }

    public void guessCard(String joinCode) throws SQLException
    {
        
    }
}
