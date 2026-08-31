import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection.java
 * Handles the JDBC connection to the MySQL database.
 *
 * IMPORTANT: Update DB_URL, DB_USER, and DB_PASSWORD below to match
 * your local MySQL configuration before running the application.
 */
public class DBConnection {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "your_password_here";

    private static Connection connection = null;

    // Private constructor to prevent instantiation
    private DBConnection() {
    }

    /**
     * Returns a singleton Connection instance.
     */
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Explicitly loading the driver is optional for modern JDBC
                // (JDBC 4.0+ auto-registers via SPI), but kept here for clarity
                // and compatibility with older setups.
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Add mysql-connector-j to your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Closes the active connection, if any.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
