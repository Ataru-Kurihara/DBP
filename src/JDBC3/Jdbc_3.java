package JDBC3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Jdbc_3 extends Object{
    public static void main(String[] args) {
        String driverClassName = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost/test";
        String user = "dbpuser";
        String password = "20fi035";
        Connection connection;

        PreparedStatement prepStmt_S; // SELECT用
        PreparedStatement prepStmt_I; // INSERT用
        PreparedStatement prepStmt_U; // UPDATE用
        PreparedStatement prepStmt_D; // DELETE用

        String strPrepSQL_S = "SELECT * FROM maker";
        String strPrepSQL_I = "INSERT INTO maker VALUES(?, ?)";
        String strPrepSQL_U = "UPDATE maker SET makername = ? WHERE makercode = ?";
        String strPrepSQL_D = "DELETE FROM maker WHERE makercode = ?";

        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, user, password);

            prepStmt_S = connection.prepareStatement(strPrepSQL_S);
            prepStmt_I = connection.prepareStatement(strPrepSQL_I);
            prepStmt_U = connection.prepareStatement(strPrepSQL_U);
            prepStmt_D = connection.prepareStatement(strPrepSQL_D);

            System.out.println("データの表示");
            showTable(prepStmt_S.executeQuery(),"データの表示");



            System.out.println("データの追加");
            prepStmt_I.setString(1, "M04");
            prepStmt_I.setString(2, "C販売");
            prepStmt_I.executeUpdate();
            showTable(prepStmt_S.executeQuery(),"データの追加");



            System.out.println("データの更新");
            prepStmt_U.setString(1, "C総合販売");
            prepStmt_U.setString(2, "M04");
            prepStmt_U.executeUpdate();
            showTable(prepStmt_S.executeQuery(),"データの更新");



            System.out.println("データの削除");
            prepStmt_D.setString(1, "M04");
            prepStmt_D.executeUpdate();
            showTable(prepStmt_S.executeQuery(),"データの削除");

            prepStmt_S.close();
            prepStmt_I.close();
            prepStmt_U.close();
            prepStmt_D.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showTable(ResultSet resultSet,String text) throws Exception {
        FileWriter file = new FileWriter("./jdbc_3.txt", true);
        PrintWriter pw = new PrintWriter(new BufferedWriter(file));
        pw.println(text);
        while (resultSet.next()) {

            String makerName = resultSet.getString("makername");
            String makerCode = resultSet.getString("makercode");
            System.out.println(makerCode + "\t" + makerName);
            try {
                pw.println(makerCode + "\t" + makerName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        pw.println();
        pw.close();
        resultSet.close();
    }
}
