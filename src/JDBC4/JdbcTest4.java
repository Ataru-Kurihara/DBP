package JDBC4;

import java.awt.*;       // AWTコンポーネントを使用するために必要
import java.awt.event.*; // イベントを取り扱うために必要
import java.sql.*;


/**
 JdbcTest4 クラスの定義
 */

public class JdbcTest4 extends Frame implements ActionListener, ItemListener {
    java.awt.List nameList;            // AWTのリストを入れる変数
    TextField nameField, addressField, telField, emailField; // テキストフィールドを入れる変数
    Button displayButton, updateButton, addButton, deleteButton; // ボタンを入れる変数
    Panel leftPanel, rightPanel;       // パネルを入れる変数
    String displayCommand = "Display", updateCommand = "Update",
            addCommand = "Add", deleteCommand = "Delete";  // ボタンのコマンド文字列

    String driverClassName = "org.postgresql.Driver";
    String url = "jdbc:postgresql://localhost/test";
    String user = "dbpuser";
    String password = "20fi035";
    Connection connection;
    ResultSet resultSet;

    PreparedStatement prepStmt; // SELECT name 用 (リスト表示)
    PreparedStatement prepStmt_S; // SELECT用
    PreparedStatement prepStmt_I; // INSERT用
    PreparedStatement prepStmt_U; // UPDATE用
    PreparedStatement prepStmt_D; // DELETE用

    String selectStr = "SELECT name FROM address";
    String strPrepSQL_S = "SELECT * FROM address WHERE name = ?";
    String strPrepSQL_I = "INSERT INTO address VALUES(?, ?, ?, ?)";
    String strPrepSQL_U =
            "UPDATE address SET address = ?, tel = ?, email = ? WHERE name = ?";
    String strPrepSQL_D = "DELETE FROM address WHERE name = ?";

    JdbcTest4() {            // コンストラクタ
        setSize(400, 200);                 // フレームのサイズ設定
        setTitle("Jdbc Test 4");   // フレームのタイトル設定
        setLayout(new GridLayout(1, 2));   // フレームのレイアウト設定

        leftPanel = new Panel();               // 左のパネルの生成
        leftPanel.setLayout(new GridLayout()); // 左のパネルのレイアウト設定
        add(leftPanel);                        // フレームに左のパネルを追加

        nameList = new java.awt.List(10);  // リストの生成
        nameList.addItemListener(this);    // フレームをリスナにする
        leftPanel.add(nameList);  // 左のパネルに追加

        rightPanel = new Panel();                   // 右のパネルの生成
        rightPanel.setLayout(new GridLayout(6, 2)); // 右のパネルのレイアウト設定
        add(rightPanel);                            // フレームに右のパネルを追加

        rightPanel.add(new Label("name"));        // 右のパネルにnameラベル追加
        nameField = new TextField(15);            // nameフィールドの生成
        rightPanel.add(nameField);                // 右のパネルにnameフィールド追加
        rightPanel.add(new Label("address"));     // 右のパネルにaddressラベル追加
        addressField = new TextField(15);         // addressフィールドの生成
        rightPanel.add(addressField);             // 右のパネルにaddressフィールド追加
        rightPanel.add(new Label("tel"));       // 右のパネルにtelラベル追加
        telField = new TextField(15);           // telフィールドの生成
        rightPanel.add(telField);               // 右のパネルにtelフィールド追加
        rightPanel.add(new Label("email"));     // 右のパネルにemailラベル追加
        emailField = new TextField(15);         // emailフィールドの生成
        rightPanel.add(emailField);             // 右のパネルにemailフィールド追加

        displayButton = new Button(displayCommand);   // displayボタンの生成
        displayButton.addActionListener(this);        // フレームをリスナにする
        rightPanel.add(displayButton);            // 右のパネルにdisplayボタン追加
        updateButton = new Button(updateCommand); // updateボタンの生成
        updateButton.addActionListener(this);     // フレームをリスナにする
        rightPanel.add(updateButton);             // 右のパネルにupdateボタン追加

        addButton = new Button(addCommand);       // addボタンの生成
        addButton.addActionListener(this);        // フレームをリスナにする
        rightPanel.add(addButton);                // 右のパネルにaddボタン追加
        deleteButton = new Button(deleteCommand); // deleteボタンの生成
        deleteButton.addActionListener(this);     // フレームをリスナにする
        rightPanel.add(deleteButton);             // 右のパネルにdeleteボタン追加
        addWindowListener ( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                try {
                    prepStmt.close();
                    prepStmt_S.close();
                    prepStmt_I.close();
                    prepStmt_U.close();
                    prepStmt_D.close();
                    connection.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        } ) ; // ウィンドウを閉じる処理

        try { // ドライバマネージャとコネクション
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, user, password);

            prepStmt = connection.prepareStatement(selectStr);
            prepStmt_S = connection.prepareStatement(strPrepSQL_S);
            prepStmt_I = connection.prepareStatement(strPrepSQL_I);
            prepStmt_U = connection.prepareStatement(strPrepSQL_U);
            prepStmt_D = connection.prepareStatement(strPrepSQL_D);
        } catch (Exception e) {
            e.printStackTrace();
        }
        displayList(); // 早速リストに名前表示
    }

    public void itemStateChanged(ItemEvent ie) { // 選択項目が変化した時の処理
        // とりあえず今は、なし
        displayData(); // 各フィールドにデータ表示
    }

    public void clearList() { // リストクリア
        nameList.removeAll(); // リストの項目をすべて削除
    }

    public void displayList() { // リスト項目表示
        try {
            resultSet = prepStmt.executeQuery(); // 名前の列だけ抜き出す
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                nameList.add(name); // リストに名前を追加
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayData() { // 選択項目データ再表示
        String name = nameList.getSelectedItem(), // 名前はリストの選択項目
                address = "", tel = "", email = "";
        try {
            prepStmt_S.setString(1, name);
            resultSet = prepStmt_S.executeQuery();
            while (resultSet.next()) { // 同じ名前の場合は最後が有効
                name = resultSet.getString("name");
                address = resultSet.getString("address");
                tel = resultSet.getString("tel");
                email = resultSet.getString("email");
            }
            nameField.setText(name); // 各フィールドに値をセット
            addressField.setText(address);
            telField.setText(tel);
            emailField.setText(email);

            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateData() { // 項目データ更新
        String name = nameList.getSelectedItem(), // 更新する行の名前
                address = "", tel = "", email = "";
        nameField.setText(name); // 念のため名前フィールドを元に
        address = addressField.getText(); // 残りの各データをもらう
        tel = telField.getText();
        email = emailField.getText();
        try { // 新データに更新
            prepStmt_U.setString(1, address);
            prepStmt_U.setString(2, tel);
            prepStmt_U.setString(3, email);
            prepStmt_U.setString(4, name);
            prepStmt_U.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addData() { // 項目データ追加
        String name = "", address = "", tel = "", email = "";
        name = nameField.getText(); // 各データをもらう
        address = addressField.getText();
        tel = telField.getText();
        email = emailField.getText();
        try { // 新データを追加
            prepStmt_I.setString(1, name);
            prepStmt_I.setString(2, address);
            prepStmt_I.setString(3, tel);
            prepStmt_I.setString(4, email);
            prepStmt_I.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearList(); // リストをクリア
        displayList(); // リストを表示
    }

    public void deleteData() { // 選択項目データ削除
        String name = nameList.getSelectedItem(); // 名前をもらう
        try { // 名前の行を削除
            prepStmt_D.setString(1, name);
            prepStmt_D.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        clearList(); // リストのクリア
        displayList(); // リストの表示
    }

    public void actionPerformed(ActionEvent ae) { // ボタンが押された時に行う処理
        String command = ae.getActionCommand();   // イベントからアクションコマンドを得る
        if (command.equals(displayCommand)) {     // displayコマンドなら
            displayData();// 表示処理
        } else if (command.equals(updateCommand)) { // updateコマンドなら
            updateData();// 更新処理
        } else if (command.equals(addCommand)) { // addコマンドなら
            addData();// 追加処理
        } else if (command.equals(deleteCommand)) { // deleteコマンドなら
            deleteData();// 削除処理
        }
    }

    public static void main(String[] argv) {
        JdbcTest4 myFrame = new JdbcTest4(); // フレームの生成
        myFrame.setVisible(true);          // フレームの可視化
    }
}