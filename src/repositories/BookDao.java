package repositories;

import models.Book;
import utils.MyJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BookDao {
    public static Connection cnn = MyJDBC.cnn();

    public static ArrayList<Book> getBooks(String condition){
        ArrayList<Book> books = new ArrayList();
        try{
            Statement st = cnn.createStatement();
            ResultSet resultSet = st.executeQuery("select * from book where "+condition);
            while (resultSet.next()){
                Book book = new Book(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setQuantity(resultSet.getInt("quantity"));
                book.setISBNNumber(resultSet.getLong("ISBN"));
                books.add(book);
            }
            if(books.isEmpty()) return null;
            return books;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Book getBook(String condition){
        String getBookSql = "select * from book where "+condition+" LIMIT 1";
        try{
            Statement bookSt = cnn.createStatement();
            ResultSet resultSet = bookSt.executeQuery(getBookSql);
            if(resultSet==null) return null;
            else {
                Book book = new Book(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setISBNNumber(resultSet.getLong("ISBN"));
                book.setQuantity(resultSet.getInt("quantity"));
                return book;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addBook(String title,String author_name,int quantity,Long ISBN){
        String sql = "INSERT INTO book (title, author_name, quantity, ISBN) " +
                "VALUES (?,?,?,?)";

        try{
            PreparedStatement st = cnn.prepareStatement(sql);
            st.setString(1, title);
            st.setString(2, author_name);
            st.setInt(3, quantity);
            st.setLong(4, ISBN);

            int row = st.executeUpdate();
            if(row == 1) return true;
            else return false;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteBook(int id){
        String deleteSql = "DELETE FROM book where id="+id;
        try{
            PreparedStatement st = cnn.prepareStatement(deleteSql);
            st.setInt(1, id);
            int row = st.executeUpdate();
            if(row>0) return true;
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public static boolean updateBook(int id, String title, String author_name, int quantity, Long ISBNnumber){

        String sql = "UPDATE book SET title=?,author_name=?,quantity=?,ISBN=? where id = ? ";
        try{
            PreparedStatement st = cnn.prepareStatement(sql);
            st.setString(1, title);
            st.setString(2, author_name);
            st.setInt(3, quantity);
            st.setLong(4, ISBNnumber);
            st.setLong(5, id);

            int row = st.executeUpdate();
            if(row>0) return true;
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
