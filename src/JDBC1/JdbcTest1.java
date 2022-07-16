package JDBC1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class JdbcTest1 extends Object {
    public static void main(String args[]) {
        String driverClassName = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost/test";
        String user = "dbpuser";      // ここはユーザ名
        String password = "20fi035"; // ここはパスワード
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        String sql = "SELECT * FROM address";

        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String tel = resultSet.getString("tel");
                String email = resultSet.getString("email");

                System.out.println(name + "\t" + address + "\t"
                        + tel + "\t" + email);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}