/**
 * Created by ipetrash on 18.10.2016.
 */

import java.sql.*;

public class SQLiteJDBC
{
    public static void main(String[] args) throws ClassNotFoundException
    {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        // create a database connection
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db")) {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

//            statement.executeUpdate("drop/ table if exists person");
            try {
                // Create and fill table
                statement.executeUpdate("create table person (id integer, name string)");
                statement.executeUpdate("insert into person values(1, 'leo')");
                statement.executeUpdate("insert into person values(2, 'yui')");

            } catch (SQLException e) {
                // If error not about create exists table
                if (!e.getMessage().contains("already exists")) {
                    throw new SQLException(e);
                }
            }

            // Read all rows
            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next()) {
                // read the result set
                System.out.println(String.format("id=%s name=%s", rs.getInt("id"), rs.getString("name")));
            }

        } catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }
}
