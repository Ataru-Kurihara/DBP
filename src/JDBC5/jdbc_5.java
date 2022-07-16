package JDBC5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class jdbc_5 extends JFrame implements ActionListener, ItemListener{
    java.awt.List PostList;
    java.util.List<String> postList = new ArrayList<String>();
    JList PostField;
    JTextField CityField, AreaField;
    JScrollPane jScrollPane;
    JComboBox PrefecturesBox;
    Button displayButton, updateButton, addButton, deleteButton;
    JPanel leftPanel, rightPanel;
    String displayCommand = "Display", updateCommand = "Update", addCommand = "add", deleteCommand = "Delete";

    String driverClassName = "org.postgresql.Driver";
    String url = "jdbc:postgresql://localhost/test";
    String user = "dbpuser";
    String password = "20fi035";
    Connection connection;
    ResultSet resultSet;

    PreparedStatement preparedStatement;
    PreparedStatement preparedStatement_S;
    PreparedStatement preparedStatement_I;
    PreparedStatement preparedStatement_U;
    PreparedStatement preparedStatement_D;

    String selectPost = "SELECT code FROM postalcode where prefecture = ?";
    String selectCity = "SELECT city FROM postalcode where code = ?";
    String selectArea = "SELECT area from postalcode where code = ?";
    String strPrepSQL_S = "SELECT * FROM postalcode WHERE prefecture = ?";
    String strPreSQL_I = "INSERT INTO maker VALUES(?, ?)";
    String strPrepSQL_U = "UPDATE maker SET makername = ? where makercode = ?";
    String strPrepSQL_D = "DELETE FROM maker where makercode = ?";



    public static void main(String[] args) {
        jdbc_5 frame = new jdbc_5();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    jdbc_5() {
        String[] Prefecture_list = {"都道府県を1つ選択してください","北海道","青森県","岩手県","宮城県","秋田県","山形県","福島県",
                "茨城県","栃木県","群馬県","埼玉県","千葉県","東京都","神奈川県",
                "新潟県","富山県","石川県","福井県","山梨県","長野県","岐阜県",
                "静岡県","愛知県","三重県","滋賀県","京都府","大阪府","兵庫県",
                "奈良県","和歌山県","鳥取県","島根県","岡山県","広島県","山口県",
                "徳島県","香川県","愛媛県","高知県","福岡県","佐賀県","長崎県",
                "熊本県","大分県","宮崎県","鹿児島県","沖縄県"
        };

        setSize(1000, 200);
        setTitle("jdbc_5");
        setLayout(new GridLayout(1,2));
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout());
        add(leftPanel);

        //都道府県の選択
        PrefecturesBox = new JComboBox(Prefecture_list);
        PrefecturesBox.addActionListener(this);
        leftPanel.add(PrefecturesBox);

        //郵便番号の表示
        PostField = new JList();
        jScrollPane = new JScrollPane(PostField);
        add(jScrollPane);
//        PostList = new java.awt.List(100);
//        PostList.addItemListener(this);
//        leftPanel.add(PostList);

        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(6, 2));
        add(rightPanel);

        rightPanel.add(new Label("city"));
        CityField = new JTextField(15);
        rightPanel.add(CityField);
        rightPanel.add(new Label("area"));
        AreaField = new JTextField( 15);
        rightPanel.add(AreaField);

        displayButton = new Button(displayCommand);
        displayButton.addActionListener(this);
        rightPanel.add(displayButton);

        updateButton = new Button(updateCommand);
        updateButton.addActionListener(this);
        rightPanel.add(updateButton);

        addButton = new Button(addCommand);
        addButton.addActionListener(this);
        rightPanel.add(addButton);

        deleteButton = new Button(deleteCommand);
        deleteButton.addActionListener(this);
        rightPanel.add(deleteButton);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    preparedStatement.close();
                }catch (Exception err) {
                    err.printStackTrace();
                }
                System.exit(0);
            }
        });
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, user, password);

            preparedStatement = connection.prepareStatement(selectPost);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void action_list(ActionEvent e) {
        String selectPrefectures;
        if (PrefecturesBox.getSelectedIndex() == -1) {
            selectPrefectures = "(not select)";
        } else {
            selectPrefectures = (String) PrefecturesBox.getSelectedItem();
        }
        System.out.println(selectPrefectures);
    }
    public void displayPost(ActionEvent ev) {
        String selectPrefectures;
        String code = "";
        try {
            if (PrefecturesBox.getSelectedIndex() == -1) {
                selectPrefectures = "(not select)";
            } else {
                selectPrefectures  = (String)PrefecturesBox.getSelectedItem();
            }
            preparedStatement.setString(1, selectPrefectures);
            resultSet = preparedStatement.executeQuery();
            StringBuilder sb = new StringBuilder();
            while (resultSet.next()) {
                code = resultSet.getString("code");
                sb.append(code).append("\n");
            }



            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void displayDate() {


    }
    public void actionPerformed(ActionEvent e) {

        displayPost(e);
    }


    public void itemStateChanged(ItemEvent e) {

    }



}
