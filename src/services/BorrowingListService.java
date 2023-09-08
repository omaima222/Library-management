package services;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import models.Book;
import utils.MyJDBC;
import models.BorrowingList;
import models.User;
import services.BookService;
import services.UserService;

public class BorrowingListService {
    public static Connection cnn = MyJDBC.cnn();
    public static Scanner scanner = new Scanner(System.in);

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void  getBorrowedBooksList(){

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

    public static void borrowBook(){
        System.out.println("*********************** Borrow a book ***********************");
        User user = UserService.getUser("cin");
        if(user==null) return;
        ArrayList<Book> books =  BookService.getBook("ISBN");
        Book book = books.get(0);
        if(book==null) return;
        int book_id = book.getId();
        int user_id = user.getId();
        System.out.println("Enter the book's quantity:");
        int quantity = scanner.nextInt();
        if(quantity>book.getQuantity()){System.out.println("!!! only "+book.getQuantity()+" books available !!!"); return;}
        System.out.println("Enter the return date in yyyy-mm-dd format :");
        String return_date = scanner.next();
        Date Borrowing_date = new Date();
        String borrowing_date = dateFormat.format(Borrowing_date);

        try{
            Statement st = cnn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * from borrowing_list where book_id ="+book_id+" and user_id ="+user_id);
            if(resultSet.next()){
                System.out.println("This user has already borrowed "+resultSet.getInt("quantity")+" of this book");
                System.out.println("Enter Y to allow this operation :");
                scanner.nextLine();
                String answer = scanner.next();
                if(!answer.equals("Y")) {
                    System.out.println("answer :"+answer);
                    System.out.println("!!! Operation denied !!!");
                    return;
                }
            }

            String addToBorrowingList = "INSERT INTO borrowing_list (quantity, borrowing_date, return_date, book_id, user_id)" +
                    "VALUES (?,?,?,?,?)";

            PreparedStatement borrowingListSt = cnn.prepareStatement(addToBorrowingList);
            borrowingListSt.setInt(1, quantity);
            borrowingListSt.setString(2, borrowing_date);
            borrowingListSt.setString(3, return_date);
            borrowingListSt.setInt(4, book_id);
            borrowingListSt.setInt(5, user_id);


            int row = borrowingListSt.executeUpdate();
            if (row > 0) {
                System.out.println("operation successeded");
                return;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void returnBook(){
        System.out.println("*********************** Return a book ***********************");
        User user = UserService.getUser("cin");
        if(user==null) return;
        ArrayList<Book> books =  BookService.getBook("ISBN");
        Book book = books.get(0);
        if(book==null) return;
        int book_id = book.getId();
        int user_id = user.getId();
        System.out.println("Enter the book's quantity:");
        int quantity = scanner.nextInt();

        try {
            Statement borrowedBookSt = cnn.createStatement();
            ResultSet resultSet = borrowedBookSt.executeQuery("select * from borrowing_list where book_id = "+book.getId()+" ORDER BY borrowing_date ASC LIMIT 1");
            if(!resultSet.next()){System.out.println("!!! This book is not in the borrowing list !!!");return;}
            if(resultSet.getInt("quantity") == quantity){
                int row = borrowedBookSt.executeUpdate("DELETE from borrowing_list where id ="+resultSet.getInt("id"));
            }else if(resultSet.getInt("quantity") > quantity){
                int newQuantity = resultSet.getInt("quantity") - quantity;
                int row = borrowedBookSt.executeUpdate("UPDATE borrowing_list set quantity = "+ newQuantity +" where id ="+resultSet.getInt("id"));
            }
            PreparedStatement bookSt = cnn.prepareStatement("update book set quantity = ? where id = ?");
            bookSt.setInt(1,book.getQuantity()+quantity);
            bookSt.setInt(2, book.getId());
            int row = bookSt.executeUpdate();
            if(row>0) {System.out.println("operation successeded"); return;}
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
