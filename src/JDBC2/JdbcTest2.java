package JDBC2;

import java.sql.*;

class JdbcTest2 extends Object {
    public static void main(String args[]) {
        String driverClassName = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost/test";
        String user = "dbpuser";
        String password = "20fi035";
        Connection connection;
        Statement statement;
        String sql = "SELECT * FROM address";

        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            showTable(statement.executeQuery(sql)); //  1.

            statement.executeUpdate(
                    "INSERT INTO address VALUES('鈴木', 'Tokyo', '0120-00-0000', 'suzuki@tokyo')"); //  2.
            showTable(statement.executeQuery(sql)); //  3.

            statement.executeUpdate(
                    "UPDATE address SET tel = '03-333-3333' WHERE address = 'Tokyo'"); //  4.
            showTable(statement.executeQuery(sql)); //  5.

            statement.executeUpdate(
                    "DELETE FROM address WHERE email = 'suzuki@tokyo'"); //  6.
            showTable(statement.executeQuery(sql)); //  7.

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void showTable(ResultSet resultSet) throws Exception {
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            String tel = resultSet.getString("tel");
            String email = resultSet.getString("email");

            System.out.println(name + "\t" + address + "\t"
                    + tel + "\t" + email);
        }
        System.out.println();

        resultSet.close();
    }
}
