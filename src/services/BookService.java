package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import models.Book;
import utils.MyJDBC;

public class BookService {
    public static Scanner scanner = new Scanner(System.in);
    public static Connection cnn = MyJDBC.cnn();

    public static Book getBook(String searchBy){

        System.out.println("Enter the book's "+searchBy+" :");
        String searchValue = scanner.nextLine();
        String getBookSql = "select * from book where "+searchBy+" = ?";

        try{
            PreparedStatement bookSt = cnn.prepareStatement(getBookSql);
            if(searchBy == "ISBN"){
                Long ISBNnumber = Long.parseLong(searchValue);
                bookSt.setLong(1, ISBNnumber);
            }else bookSt.setString(1, searchValue);
            ResultSet resultSet = bookSt.executeQuery();
            if(!resultSet.next()) {System.out.println("!!! Book not found !!!"); return null;}
            Book book = new Book(resultSet.getInt("id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthorName(resultSet.getString("author_name"));
            book.setISBNNumber(resultSet.getLong("ISBN"));
            book.setQuantity(resultSet.getInt("quantity"));
            return book;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
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
        System.out.println("*********************** Add a book ***********************");
        System.out.println("Enter Title : ");
        String title = scanner.nextLine();
        System.out.println("Enter Author : ");
        String author_name = scanner.nextLine();
        System.out.println("Enter ISBN : ");
        int ISBN =  scanner.nextInt();
        System.out.println("Enter quantity : ");
        int quantity =  scanner.nextInt();
        String sql = "INSERT INTO book (title, author_name, quantity, ISBN) " +
                "VALUES (?,?,?,?)";

        try{
            PreparedStatement st = cnn.prepareStatement(sql);
            st.setString(1, title);
            st.setString(2, author_name);
            st.setInt(3, quantity);
            st.setLong(4, ISBN);

            int row = st.executeUpdate();
            if(row == 1)
                System.out.println("Book inserted successfully");
            else System.out.println("Insertion failed");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void deleteBook(){
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
    public static void  editBook(){
        System.out.println("*********************** Edit a book ***********************");
        System.out.println("Enter the book's ISBN number :");
        long ISBNnumber = scanner.nextLong();
        String findSql = "select * from book where ISBN ="+ISBNnumber;
        try{
            Statement st = cnn.createStatement();
            ResultSet resultSet = st.executeQuery(findSql);
            if(!resultSet.next()){ System.out.println("!!! Book not found !!!"); return;}
            Book book = new Book(resultSet.getInt("id"));
            book.setAuthorName(resultSet.getString("author_name"));
            book.setTitle(resultSet.getString("title"));
            book.setQuantity(resultSet.getInt("quantity"));
            book.setISBNNumber(resultSet.getLong("ISBN"));
            System.out.println(book);
            scanner.nextLine();
            updateBook(book.id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void updateBook(int id){
        System.out.println("Enter new title :");
        String title = scanner.nextLine();
        System.out.println("Enter new author :");
        String author = scanner.nextLine();
        System.out.println("Enter new ISBN :");
        long ISBNnumber = scanner.nextInt();
        System.out.println("Enter new quantity :");
        int quantity = scanner.nextInt();

        String sql = "UPDATE book SET title=?,author_name=?,quantity=?,ISBN=? where id = ? ";
        try{
            PreparedStatement st = cnn.prepareStatement(sql);
            st.setString(1, title);
            st.setString(2, author);
            st.setInt(3, quantity);
            st.setLong(4, ISBNnumber);
            st.setLong(5, id);

            int row = st.executeUpdate();
            if(row>0) System.out.println("Book successfully updated");

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
