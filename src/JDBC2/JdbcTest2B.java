package JDBC2;

import java.sql.*;
import java.util.*;

class JdbcTest2B extends Object {
    public static void main(String args[]) {
        String driverClassName = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost/test";
        String user = "dbpuser";
        String password = "20fi035";
        Connection connection;
        PreparedStatement prepStmt_S;
        String strPrepSQL_S = "SELECT * FROM address WHERE name = ?";

        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, user, password);
            prepStmt_S = connection.prepareStatement(strPrepSQL_S);

            // ユーザからの入力を得る
            System.out.print("検索したい名前を入れてね: ");
            Scanner scanner = new Scanner(System.in);
            String inputName = scanner.nextLine();

            // 入力をセットして実行
            prepStmt_S.setString(1, inputName);
            ResultSet resultSet = prepStmt_S.executeQuery();

            System.out.println("問合せ: " + prepStmt_S);
            System.out.println("パラメータ: " + inputName);

            showTable(resultSet);

            prepStmt_S.close();
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