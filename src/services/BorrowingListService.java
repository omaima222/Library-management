package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import models.Book;
import utils.MyJDBC;
import models.BorrowingList;
import models.User;

public class BorrowingListService {
    public static Connection cnn = MyJDBC.cnn();
    public static Scanner scanner = new Scanner(System.in);

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void getBorrowedBooksList(){

        System.out.println("________________________________________________________________________________________________________________________________");
        System.out.println("CIN                User                Book title                Book ISBN                Borrowing date                Return date                ");
        System.out.println("________________________________________________________________________________________________________________________________");

        try{
            Statement st = cnn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * from borrowing_list " +
                    "INNER JOIN user on borrowing_list.user_id = user.id " +
                    "INNER JOIN book on borrowing_list.book_id = book.id");
            while (resultSet.next()){
                BorrowingList borrowed = new BorrowingList(resultSet.getInt("id"));
                borrowed.setBorrowingDate(resultSet.getDate("borrowing_date"));
                borrowed.setReturnDate(resultSet.getDate("return_date"));
                borrowed.setQuantity(resultSet.getInt("quantity"));

                User user = new User(resultSet.getInt("user.id"));
                user.setFirstName(resultSet.getString("user.first_name"));
                user.setLastName(resultSet.getString("user.last_name"));
                user.setCin(resultSet.getString("user.cin"));
                borrowed.setUser(user);

                Book book = new Book(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setISBNNumber(resultSet.getLong("ISBN"));
                borrowed.setBook(book);

                System.out.println(borrowed);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void borrow(){
        System.out.println("*********************** Borrow a book ***********************");
        System.out.println("Enter the client's cin :");
        String cin = scanner.next();
        System.out.println("Enter the book's ISBN number :");
        Long ISBNnumber = scanner.nextLong();
        System.out.println("Enter the book's quantity:");
        String quantity = scanner.next();
        System.out.println("Enter the return date in yyyy-mm-dd format :");
        String return_date = scanner.next();
        Date Borrowing_date = new Date();
        String borrowing_date = dateFormat.format(Borrowing_date);

        String getBookSql = "select * from book where ISBN = "+ISBNnumber;
        String getUserSql = "select * from user where cin = "+cin;

        try{
            Statement st = cnn.createStatement();
            ResultSet book = st.executeQuery(getBookSql);
            if(book == null) {System.out.println("Book not found"); return;}

            ResultSet user = st.executeQuery(getUserSql);
            if(user == null) {System.out.println("Client not found"); return;}

            String addToBorrowingList = "INSERT INTO borrowing_list (quantity, borrowing_date, return_date, book_id, user_id)"+
                    "VALUES ("+quantity+","+borrowing_date+","+return_date+","+book.getInt("id")+","+user.getInt(("id"));

            int row = st.executeUpdate(addToBorrowingList);
            if(row>0) {System.out.println("operation successeded"); return;}

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
