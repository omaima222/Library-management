package repositories;

import models.Book;
import models.BorrowingList;
import models.User;
import services.BookService;
import services.UserService;
import utils.MyJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class BorrowingListDao {

    public static Connection cnn = MyJDBC.cnn();

    public static ArrayList<BorrowingList> getBorrowedBooks(String condition){
        ArrayList<BorrowingList> borrowedBooks = new ArrayList<>();
        try{
            Statement st = cnn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * from borrowing_list " +
                    "INNER JOIN user on borrowing_list.user_id = user.id " +
                    "INNER JOIN book on borrowing_list.book_id = book.id"+
                    " WHERE "+condition);
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
                borrowedBooks.add(borrowed);
            }
            if(borrowedBooks.isEmpty()) return null;
            else return borrowedBooks;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static BorrowingList getBorrowedBook(String condition){
        try{
            Statement st = cnn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * from borrowing_list " +
                    "INNER JOIN user on borrowing_list.user_id = user.id " +
                    "INNER JOIN book on borrowing_list.book_id = book.id"+
                    " WHERE "+condition+" LIMIT 1");
            if(resultSet.next()){
                BorrowingList borrowedBook = new BorrowingList(resultSet.getInt("id"));
                borrowedBook.setBorrowingDate(resultSet.getDate("borrowing_date"));
                borrowedBook.setReturnDate(resultSet.getDate("return_date"));
                borrowedBook.setQuantity(resultSet.getInt("quantity"));

                User user = new User(resultSet.getInt("user.id"));
                user.setFirstName(resultSet.getString("user.first_name"));
                user.setLastName(resultSet.getString("user.last_name"));
                user.setCin(resultSet.getString("user.cin"));
                borrowedBook.setUser(user);

                Book book = new Book(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setISBNNumber(resultSet.getLong("ISBN"));
                borrowedBook.setBook(book);

                return borrowedBook;
            }else return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addToBorrowedBooks(int book_id, int user_id, String borrowing_date, String return_date, int quantity){
        String addToBorrowingList = "INSERT INTO borrowing_list (quantity, borrowing_date, return_date, book_id, user_id)" +
                "VALUES (?,?,?,?,?)";
        try{
            PreparedStatement st = cnn.prepareStatement(addToBorrowingList);
            st.setInt(1, quantity);
            st.setString(2, borrowing_date);
            st.setString(3, return_date);
            st.setInt(4, book_id);
            st.setInt(5, user_id);

            int row = st.executeUpdate();
            if (row > 0) {
                return true;
            }
            return false;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteBookFromBorrowedBooks(int id){
        String deleteSql = "DELETE from borrowing_list where id = ?";
        try{
            PreparedStatement st = cnn.prepareStatement(deleteSql);
            st.setInt(1, id);
            int row = st.executeUpdate();
            if(row>0){
                return true;
            }
            return  false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateBookQuantityFromBorrowedBooks(int id, int quantity){
        String updateSql = "UPDATE borrowing_list set quantity = ? where id = ?";
        try{
            PreparedStatement st = cnn.prepareStatement(updateSql);
            st.setInt(1, quantity);
            st.setInt(2, id);
            int row = st.executeUpdate();
            if(row>0) {
                return  true;
            }
            return false;
        }catch (Exception e){
           e.printStackTrace();
           return false;
        }
    }

    public static int countBorrowedBooks(){
        try{
            Statement st = cnn.createStatement();
            ResultSet resultSet = st.executeQuery("select * from borrowing_list");
            int count = 0;
            while(resultSet.next()){
                count+=resultSet.getInt("quantity");
            }
            return  count;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
