package JDBC2;

import java.sql.*;
import java.util.*;

class JdbcTest2A extends Object {
    public static void main(String args[]) {
        String driverClassName = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost/test";
        String user = "dbpuser";
        String password = "20fi035";
        Connection connection;
        Statement statement;
        String sql = "SELECT * FROM address WHERE name = '";

        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            // ユーザからの入力を得てSQL文を組み立てる
            System.out.print("検索したい名前を入れてね: ");
            Scanner scanner = new Scanner(System.in);
            String inputName = scanner.nextLine();
            sql = sql + inputName + "'";

            System.out.println("問合せ: " + sql);

            ResultSet resultSet = statement.executeQuery(sql); // 実行

            showTable(resultSet);

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showTable(ResultSet resultSet) throws Exception {
        System.out.println("結果: ");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            String tel = resultSet.getString("tel");
            String email = resultSet.getString("email");

            System.out.println(name + "\t" + address + "\t"
                    + tel + "\t" + email);
        }
        resultSet.close();
    }
}