import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Employee {
    private String employeeName;
    private int employeeSalary;
    private int employeeDeptId;

    public void readData() {
        try (Scanner input = new Scanner(new File("src\\main\\java\\data.csv"))) {
            while (input.hasNextLine()) {
                employeeName = "";
                String line;

                line = input.nextLine();

                //process line of text for each data item
                try (Scanner data = new Scanner(line)) {
                    while (!data.hasNextInt()) {
                        employeeName += data.next() + " ";
                    }
                    employeeName = employeeName.trim();

                    //get salary
                    if (data.hasNextInt()) {
                        employeeSalary = data.nextInt();
                    }

                    //get department id
                    if (data.hasNextInt()) {
                        employeeDeptId = data.nextInt();
                    }
                }
                //check data
                //System.out.println(employeeName + "\t" + employeeSalary + "\t" + employeeDeptId);

                //call method to save data into database
                saveData();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    // save the data into database
    private void saveData() {
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO employees VALUES(?, ?, ?)")) {

            pstmt.setString(1, employeeName);
            pstmt.setInt(2, employeeSalary);
            pstmt.setInt(3, employeeDeptId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    //create a connection to the database
    private Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "Qwertyuiop1!");
        } catch (SQLException  e) {
            System.out.println(e);
            return null;

        }

    }
}
