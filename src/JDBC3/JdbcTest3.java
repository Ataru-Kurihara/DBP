package JDBC3;

import java.sql.*;

class JdbcTest3 extends Object {
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

        String strPrepSQL_S = "SELECT * FROM address";
        String strPrepSQL_I = "INSERT INTO address VALUES(?, ?, ?, ?)";
        String strPrepSQL_U = "UPDATE address SET tel = ? WHERE address = ?";
        String strPrepSQL_D = "DELETE FROM address WHERE email = ?";

        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, user, password);

            prepStmt_S = connection.prepareStatement(strPrepSQL_S);
            prepStmt_I = connection.prepareStatement(strPrepSQL_I);
            prepStmt_U = connection.prepareStatement(strPrepSQL_U);
            prepStmt_D = connection.prepareStatement(strPrepSQL_D);

            showTable(prepStmt_S.executeQuery()); // 1.

            prepStmt_I.setString(1, "鈴木");
            prepStmt_I.setString(2, "Tokyo");
            prepStmt_I.setString(3, "0120-00-0000");
            prepStmt_I.setString(4, "suzuki@tokyo");
            prepStmt_I.executeUpdate(); // 2.
            showTable(prepStmt_S.executeQuery()); // 3.

            prepStmt_U.setString(1, "03-333-3333");
            prepStmt_U.setString(2, "Tokyo");
            prepStmt_U.executeUpdate(); // 4.
            showTable(prepStmt_S.executeQuery()); // 5.

            prepStmt_D.setString(1, "suzuki@tokyo");
            prepStmt_D.executeUpdate(); // 6.
            showTable(prepStmt_S.executeQuery()); // 7.

            prepStmt_S.close();
            prepStmt_I.close();
            prepStmt_U.close();
            prepStmt_D.close();
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
