package JDBC1;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

//SQL演習3で作成した 「売上テーブル」の内容を表示するプログラムを作成しなさい。
public class Jdbc_1 extends Object {
    public static void main(String[] args) {
        String driverClassName, url, user, password;
        Properties properties = getProperties("db.properties");
        driverClassName = properties.getProperty("driverClassName");
        url = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");

        Connection connection;
        Statement statement;
        ResultSet resultSet;
        String sql = "select * from uriage";

        try{
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            FileWriter file = new FileWriter("./jdbc_1.txt");
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));
            while (resultSet.next()) {
                String uriageno = resultSet.getString("uriageno");
                String hinmokucode = resultSet.getString("hinmokucode");
                int hanbaitanka = resultSet.getInt("hanbaitanka");
                int uriagesuryo = resultSet.getInt("uriagesuryo");
                Date uriagedate = resultSet.getDate("uriagedate");
                System.out.println(uriageno + "\t" + hinmokucode + hanbaitanka + "\t" + uriagesuryo + "\t" + uriagedate);
                pw.println(uriageno + "\t" + hinmokucode + hanbaitanka + "\t" + uriagesuryo + "\t" + uriagedate);
            }
            pw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Properties getProperties(String filename) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filename));
        } catch (IOException e) {
            System.out.println("Warning: " + filename + "is not found!!");
        }
        return properties;
    }
}
