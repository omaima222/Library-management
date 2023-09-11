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


    public static ArrayList<Book> getAllAvailableBooks(){
        ArrayList<Book> books = BookDao.getBooks("quantity > 0");
        return books;
    };
    public static boolean addBook(String title,String author_name,int quantity,Long ISBN){
        while (true){

            // Check if if title is null
            if (title == null || title.isEmpty()) {
                System.out.println("Title is required. Please enter a title:");
                title = scanner.nextLine();
                continue;
            }

            // Check if if author name is null
            if (author_name == null || author_name.isEmpty()) {
                System.out.println("Author is required. Please enter the author' name:");
                author_name = scanner.nextLine();
                continue;
            }

            // Check if quantity is positive
            if ( quantity <= 0) {
                System.out.println("Quantity must be a positive number. Please enter a valid quantity:");
                try {
                    quantity = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    continue;
                }
            }

            // Check if ISBN is valid
            if (ISBN == null || ISBN <= 0 ) {
                System.out.println("ISBN must be a positive number. Please enter a valid ISBN:");
                try {
                    ISBN = Long.parseLong(scanner.nextLine());
                } catch (NumberFormatException e) {
                    continue;
                }
            }

            return BookDao.addBook(title,author_name,quantity,ISBN);
        }
    }
    public static boolean deleteBook(Long ISBN){
        while (true){
            // Check if ISBN is valid
            if (ISBN == null || ISBN <= 0 ) {
                System.out.println("ISBN must be a positive number. Please enter a valid ISBN:");
                try {
                    ISBN = Long.parseLong(scanner.nextLine());
                } catch (NumberFormatException e) {
                    continue;
                }
            }


            // finding the book
            Book book = BookDao.getBook("ISBN = "+ISBN);
            if(book == null) return false;
            //check if the book is borrowed
            BorrowingList is_borrowed = BorrowingListDao.getBorrowedBook("book_id = "+book.getId());
            //if it is then set the book's quantity to 0
            if(is_borrowed!=null){
                int borrowedQuantity = is_borrowed.getQuantity();
                if ( BookDao.updateBook(book.getId(),book.getTitle(),book.getAuthorName(),0,book.getISBNNumber())){
                    System.out.println("All available copies are deleted ! " + borrowedQuantity + " more copies are borrowed !");
                    return true;
                }else return false;
            }
            //if it isn't delete the book
            else{
                boolean row = BookDao.deleteBook(book.getId());
                if(row) {
                    System.out.println("The book is successfully deleted !");
                    return true;
                }else return false;
            }
        }
    }
    public static void  editBook(Long ISBN){
        System.out.println("*********************** Edit a book ***********************");
        Book book = BookDao.getBook("ISBN="+ISBN);
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
