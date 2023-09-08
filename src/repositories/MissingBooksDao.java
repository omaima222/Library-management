package repositories;

import models.Book;
import models.BorrowingList;
import models.MissingBooksList;
import models.User;
import utils.MyJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MissingBooksDao {
    public static Connection cnn = MyJDBC.cnn();

    public static ArrayList<MissingBooksList> getMissingBooks(String condition){
        ArrayList<MissingBooksList> missingBooks = new ArrayList<>();
        try{
            Statement st = cnn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM missing_books_list "+
                                                "INNER JOIN user on missing_books_list.user_id = user.id " +
                                                "INNER JOIN book on missing_books_list.book_id = book.id"+
                                                " WHERE "+condition);
            while (resultSet.next()){
                MissingBooksList missingBook = new MissingBooksList(resultSet.getInt("id"));

                missingBook.setQuantity(resultSet.getInt("quantity"));

                User user = new User(resultSet.getInt("user.id"));
                user.setFirstName(resultSet.getString("user.first_name"));
                user.setLastName(resultSet.getString("user.last_name"));
                user.setCin(resultSet.getString("user.cin"));
                missingBook.setUser(user);

                Book book = new Book(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setISBNNumber(resultSet.getLong("ISBN"));
                missingBook.setBook(book);

                missingBooks.add(missingBook);
            }
            if(missingBooks.isEmpty()) return null;
            else return missingBooks;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public static boolean addToMissingBooks(int book_id, int user_id, int quantity){
        String addToMissingBooks = "INSERT INTO missing_books_list (quantity, book_id, user_id)" +
                "VALUES (?,?,?)";
        try{
            PreparedStatement st = cnn.prepareStatement(addToMissingBooks);
            st.setInt(1, quantity);
            st.setInt(2, book_id);
            st.setInt(3, user_id);

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

    public static boolean deleteBookFromMissingBooks(int id){
        String deleteSql = "DELETE from missing_books_list where id = ?";
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

    public static boolean updateBookQuantityFromMissingBooks(int id, int quantity){
        String updateSql = "UPDATE missing_books_list set quantity = ? where id = ?";
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

}
