package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import models.Book;
import utils.MyJDBC;

public class BookService {

    public static Scanner scanner = new Scanner(System.in);
    public static void getAvailableBooks(){
        Connection cnn = MyJDBC.cnn();
        ArrayList<Book> result = new ArrayList();
        try{
            Statement st = cnn.createStatement();
            ResultSet resultSet = st.executeQuery("select * from book where quantity>0");
            while (resultSet.next()){
                Book book = new Book(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setQuantity(resultSet.getInt("quantity"));
                book.setISBNNumber(resultSet.getLong("ISBN"));
                result.add(book);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("_____________________________________________");
        System.out.println("                Available books              ");
        for( Book book : result){
            System.out.println("_____________________________________________");
            System.out.println(book);
            System.out.println("_____________________________________________");
        }
    }

    public static void addBook(){
        Connection cnn = MyJDBC.cnn();
        System.out.println("*********************** Add a book ***********************");
        System.out.println("Enter Title : ");
        String title = scanner.next();
        System.out.println("Enter Author : ");
        String author_name = scanner.next();
        System.out.println("Enter ISBN : ");
        int ISBN =  scanner.nextInt();
        System.out.println("Enter quantity : ");
        int quantity =  scanner.nextInt();
        String sql = "INSERT INTO book (title, author_name, quantity, ISBN) " +
                "VALUES ('" + title + "', '" + author_name + "', " + quantity + ", " + ISBN + ")";

        try{
            Statement st = cnn.createStatement();
            int row = st.executeUpdate(sql);
            if(row == 1)
                System.out.println("Book inserted successfully");
            else System.out.println("Insertion failed");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteBook(){
        Connection cnn = MyJDBC.cnn();
        System.out.println("*********************** Delete a book ***********************");
        System.out.println("Enter the book's ISBN number :");
        long ISBNnumber = scanner.nextLong();
        String sql = "Delete FROM book where ISBN="+ISBNnumber;
        try{
            Statement st = cnn.createStatement();
            int m = st.executeUpdate(sql);
            if(m>0) System.out.println("The book is successfully deleted !");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
