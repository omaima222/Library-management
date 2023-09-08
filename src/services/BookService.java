package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import models.Book;
import models.BorrowingList;
import repositories.BorrowingListDao;
import utils.MyJDBC;
import repositories.BookDao;

public class BookService {
    public static Scanner scanner = new Scanner(System.in);
    public static Connection cnn = MyJDBC.cnn();

    public static ArrayList<Book> getBook(String searchBy){

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
            ArrayList<Book> books = new ArrayList<>();
            while (resultSet.next()){
                Book book = new Book(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setISBNNumber(resultSet.getLong("ISBN"));
                book.setQuantity(resultSet.getInt("quantity"));
                books.add(book);
            }
            if(books.isEmpty()) {System.out.println("!!! Book not found !!!"); return null;}

            return books;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Book> getAllAvailableBooks(){
        ArrayList<Book> books = BookDao.getBooks("quantity > 0");
        return books;
    };
    public static boolean addBook(){
        System.out.println("Enter Title : ");
        String title = scanner.nextLine();
        System.out.println("Enter Author : ");
        String author_name = scanner.nextLine();
        System.out.println("Enter ISBN : ");
        Long ISBN =  scanner.nextLong();
        System.out.println("Enter quantity : ");
        int quantity =  scanner.nextInt();
        return BookDao.addBook(title,author_name,quantity,ISBN);
    }
    public static void deleteBook(){
        System.out.println("Enter the book's ISBN :");
        Long ISBN = scanner.nextLong();
        // finding the book
        Book book = BookDao.getBook("ISBN = "+ISBN);
        try{
            //check if the book is borrowed
            BorrowingList is_borrowed = BorrowingListDao.getBorrowedBook("book_id = "+book.getId());
            Statement st = cnn.createStatement();
            //if it is then set the book's quantity to 0
            if(is_borrowed!=null){
                int borrowedQuantity = is_borrowed.getQuantity();
                int row = st.executeUpdate("UPDATE book set quantity=0 where id=" + book.getId());
                if (row > 0){
                    System.out.println("All available copies are deleted ! " + borrowedQuantity + " more copies are borrowed !");
                }
            }
            //if it isn't delete the book
            else{
                boolean row = BookDao.deleteBook(book.getId());
                if(row) System.out.println("The book is successfully deleted !");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void  editBook(){
        System.out.println("*********************** Edit a book ***********************");
        ArrayList<Book> books = getBook("ISBN");
        Book book = books.get(0);
        System.out.println(book);
        int book_id = book.getId();
        updateBook(book.id);
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
