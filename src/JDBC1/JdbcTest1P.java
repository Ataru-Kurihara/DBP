package JDBC1;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class JdbcTest1P extends Object {
    public static void main(String[] args) {
        String driverClassName, url, user, password;
        Connection connection;
        Statement statement;
        ResultSet resultSet;
        String sql = "SELECT * FROM address";

        Properties props = getProperties("db.properties");
        driverClassName = props.getProperty("driverClassName");
        url = props.getProperty("url");
        user = props.getProperty("user");
        password = props.getProperty("password");

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

                System.out.println(name + "\t" + address + "\t" + tel + "\t" + email);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Properties getProperties(String filename) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(filename));
        } catch (IOException e) {
            System.out.println("Warning: " + filename + " is not found.");
        }
        return props;
    }
}