import java.sql.*;
import java.math.*;
import java.util.Scanner;

import oracle.jdbc.*;

import javax.xml.transform.Result;

public class Main {
    public static void main(String[] args) {
        //Initialyze SQL instances
        Connection myConnection = null;
        Statement theStatement = null;
        boolean exit = false;
        Scanner sc = new Scanner(System.in);
        int i = 0;

        //We try to load the JDBC driver
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (Exception e)
        {
            System.err.println("No puedo encontrar el driver JDBC.");
        }
        System.out.println("Lo cargue!");

        //We log ourselves into the Oracle SQL server
        try {
            myConnection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@hendrix-oracle.cps.unizar.es:1521:vicious",
                    "a601397","guGe6fai3g9nSv");
            theStatement = myConnection.createStatement();
        }
        catch (Exception e){
            System.err.println("No puedo autenticarme.");
        }

        while (!exit){
            System.out.println("Select an option:");
            System.out.println("1) Print all entries");
            System.out.println("2) Add new entry");
            System.out.println("3) Delete entry");
            System.out.println("4) Edit entry");
            System.out.println("5) Exit");
            i = sc.nextInt();
            switch (i){
                case 1: showAll(theStatement);
                    break;
                case 2: entryWizard(theStatement);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5: exit = true;
                    break;
            }
        }
        //Actual SQL query





    }
    public static void showAll(Statement theStatement){
        ResultSet theSet = null;
        try{
            theSet = theStatement.executeQuery("SELECT * FROM books");
            while (theSet.next()) {
                String theId = theSet.getString("Identificador");
                String theTitle = theSet.getString("Titulo");
                String theAuthor = theSet.getString("Autor");
                String thePrice = theSet.getString("Precio");
                System.out.println(theId + " " + theTitle + " " + theAuthor + " " + thePrice);
            }
        }
        catch (Exception e){
            System.err.println("Consulta incorrecta.");
        }
    }
    public static void entryWizard(Statement theStatement){
        ResultSet theSet = null;
        Scanner sc = new Scanner(System.in);
        String query = "";
        System.out.println("Enter id:");
        query = "'" + sc.nextLine() + "'" +", ";
        System.out.println("Enter title:");
        query = query + "'" + sc.nextLine() + "'" +", ";
        System.out.println("Enter author:");
        query = query + "'" + sc.nextLine() + "'" +", ";
        System.out.println("Enter price:");
        query = query + "'" + sc.nextLine() + "'";



        try{
            System.out.println("INSERT INTO books VALUES (" + query + ")");
            boolean theResult = theStatement.execute("INSERT INTO books VALUES (" + query + ")");
//            theSet = theStatement.executeQuery("select column_name from all_tab_columns where table_name = 'BOOKS'");
//            while (theSet.next()) {
//                String theColumn = theSet.getString("COLUMN_NAME");
//                System.out.println(theColumn);
//                String name = sc.nextLine();
//            }
        }
        catch (Exception e){
            System.err.println("Consulta incorrecta.");
        }
    }
}
