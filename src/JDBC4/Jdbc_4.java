package JDBC4;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Jdbc_4 extends Frame implements ActionListener, ItemListener {
    java.awt.List makerCodeList;
    TextField makerCodeField, makerNameField;
    Button displayButton, updateButton, addButton, deleteButton;
    Panel leftPanel, rightPanel;
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

    String selectStr = "SELECT makercode FROM maker";
    String strPrepSQL_S = "SELECT * FROM maker WHERE makercode = ?";
    String strPreSQL_I = "INSERT INTO maker VALUES(?, ?)";
    String strPrepSQL_U = "UPDATE maker SET makername = ? where makercode = ?";
    String strPrepSQL_D = "DELETE FROM maker where makercode = ?";

    Jdbc_4() {
        setSize(400, 200);
        setTitle("Jbdc_4");
        setLayout(new GridLayout(1,2));
        leftPanel = new Panel();
        leftPanel.setLayout(new GridLayout());
        add(leftPanel);

        makerCodeList = new java.awt.List(10);
        makerCodeList.addItemListener(this);
        leftPanel.add(makerCodeList);

        rightPanel = new Panel();
        rightPanel.setLayout(new GridLayout(6, 2));
        add(rightPanel);


        rightPanel.add(new Label("makercode"));
        makerCodeField = new TextField(15);
        rightPanel.add(makerCodeField);
        rightPanel.add(new Label("makername"));
        makerNameField = new TextField(15);
        rightPanel.add(makerNameField);

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
            public void windowClosing(WindowEvent we) {
                try {
                    preparedStatement.close();
                    preparedStatement_S.close();
                    preparedStatement_I.close();
                    preparedStatement_U.close();
                    preparedStatement_D.close();
                    connection.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });

        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, user, password);

            preparedStatement = connection.prepareStatement(selectStr);
            preparedStatement_S = connection.prepareStatement(strPrepSQL_S);
            preparedStatement_I = connection.prepareStatement(strPreSQL_I);
            preparedStatement_U = connection.prepareStatement(strPrepSQL_U);
            preparedStatement_D = connection.prepareStatement(strPrepSQL_D);
        }catch (Exception e) {
            e.printStackTrace();
        }
        displayList();
    }

    public void itemStateChanged(ItemEvent itemEvent) {
        displayDate();
    }

    public void clearList() {
        makerCodeList.removeAll();
    }

    public void displayList() {
        try {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("makercode");
                makerCodeList.add(name);
            }
            resultSet.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayDate() {
        String name = makerCodeList.getSelectedItem(),makercode = "", makername = "";
        try {
            preparedStatement_S.setString(1, name);
            resultSet = preparedStatement_S.executeQuery();
            while (resultSet.next()) {
                makercode = resultSet.getString("makercode");
                makername = resultSet.getString("makername");
            }
            makerCodeField.setText(makercode);
            makerNameField.setText(makername);
            resultSet.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDate() {
        String makercode = makerCodeList.getSelectedItem(),makername = "";
        makerCodeField.setText(makercode);
        makername = makerNameField.getText();
        try {
            preparedStatement_U.setString(1,makername);
            preparedStatement_U.setString(2, makercode);
            preparedStatement_U.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDate() {
        String makercode = "", makername = "";
        makercode = makerCodeField.getText();
        makername = makerNameField.getText();
        try {
            preparedStatement_I.setString(1, makercode);
            preparedStatement_I.setString(2, makername);
            preparedStatement_I.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
        clearList();
        displayList();
    }

    public void deleteDate() {
        String makercode = makerCodeList.getSelectedItem();
        try {
             preparedStatement_D.setString(1, makercode);
             preparedStatement_D.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
        clearList();
        displayList();
    }
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equals(displayCommand)) {
            displayDate();
        } else if (command.equals(updateCommand)) {
            updateDate();
        } else if (command.equals(addCommand)) {
            addDate();
        } else if (command.equals(deleteCommand)) {
            deleteDate();
        }
    }

    public static void main(String[] args) {
        Jdbc_4 frame = new Jdbc_4();
        frame.setVisible(true);
    }
}

