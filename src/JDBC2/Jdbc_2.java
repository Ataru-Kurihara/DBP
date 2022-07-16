//SQL演習1で作成した 「メーカテーブル」 にデータを追加、更新、削除するプログラムを作成しなさい。 追加、更新、削除するデータは自由に決めてよい。
//実行例:
//
//データの表示
//M01	T文具
//M02	M製作所
//M03	S商事
//
//データの追加
//M01	T文具
//M02	M製作所
//M03	S商事
//M04	C販売
//
//データの更新
//M01	T文具
//M02	M製作所
//M03	S商事
//M04	C総合販売
//
//データの削除
//M01	T文具
//M02	M製作所
//M03	S商事
package JDBC2;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

public class Jdbc_2 {
     public static void main(String[] args) {
         String driverClassName, url, user, password;

         Properties props = getProperties("db.properties");
         driverClassName = props.getProperty("driverClassName");
         url = props.getProperty("url");
         user = props.getProperty("user");
         password = props.getProperty("password");

         showExecution_maker(driverClassName, url, user, password);


     }
    static void showExecution_maker(String driverClassName, String url, String user, String password) {
         String sql = "SELECT * FROM "+ "maker";
         try {
             Connection connection;
             Statement statement;
             Class.forName(driverClassName);
             connection = DriverManager.getConnection(url, user, password);
             statement = connection.createStatement();

             System.out.println("データの表示");
             showTable(statement.executeQuery(sql), "データの表示");

             System.out.println("データの追加");
             statement.executeUpdate("INSERT INTO maker VALUES('MO4', 'C販売')");
             showTable(statement.executeQuery(sql), "データの追加");

             System.out.println("データの更新");
             statement.executeUpdate("UPDATE maker SET makername ='C総合販売' WHERE makercode ='MO4'");
             showTable(statement.executeQuery(sql), "データの更新");

             System.out.println("データの削除");
             statement.executeUpdate("DELETE FROM maker WHERE makercode = 'MO4'");
             showTable(statement.executeQuery(sql), "データの削除");

             statement.close();
             connection.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    static void showTable(ResultSet resultSet, String text) throws Exception {
        FileWriter file = new FileWriter("./jdbc_2.txt");
        PrintWriter pw = new PrintWriter(new BufferedWriter(file));
        pw.println(text);
         while (resultSet.next()) {
                 String makerCode = resultSet.getString("makercode");
                 String makerName = resultSet.getString("makername");
                 System.out.println(makerCode + "\t" + makerName);
                 try {
                     pw.println(makerCode + "\t" + makerName);
                 }catch (Exception e) {
                     e.printStackTrace();
                 }
         }
         System.out.println();
         pw.println();
         pw.close();
         resultSet.close();
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
