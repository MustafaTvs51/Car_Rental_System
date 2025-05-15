package Car_Rental_System.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_util {

    public DB_util() {
    }

    private static String URL = "jdbc:postgresql://localhost:5432/Car_Rental_System";
    private static String PGUSER = "postgres";
    private static String PGPASSWORD = "Mustafa1";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,PGUSER, PGPASSWORD);
    }
}
