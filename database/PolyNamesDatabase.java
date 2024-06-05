package database;

import java.sql.SQLException;

public class PolyNamesDatabase extends MySQLDatabase
{
    PolyNamesDatabase() throws SQLException
    {
        super("127.0.0.1", 3306, "polynames", "root", "");
    }
}
