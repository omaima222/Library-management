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
import utils.Validation;

import javax.sound.midi.Soundbank;

public class BookService {

    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Book> getAllAvailableBooks(){
        ArrayList<Book> books = BookDao.getBooks("quantity > 0");
        return books;
    };
    public static ArrayList<Book> searchByTitle(){
        // Title input
        String title = Validation.validateStr("Title")+"%";
        // Find books
        ArrayList<Book> books = BookDao.getBooks("title LIKE '"+title+"'");
        return books;
    }
    public static ArrayList<Book> searchByAuthor(){
        // Author input
        String author_name = Validation.validateStr("Author")+"%";
        // Find books
        ArrayList<Book> books = BookDao.getBooks("author_name LIKE '"+author_name+"'");
        return books;
    }
    public static boolean addBook(){
        // ISBN input
        Long ISBN = Validation.validateISBN();

        // check if this ISBN is unique 4
        Book book =  BookDao.getBook("ISBN="+ISBN);
        if(book!=null){
            System.out.println("!!! The book '"+book.getTitle()+"' already has this ISBN !!!");
            return false;
        }

        // Title input
        String title = Validation.validateStr("Title");

        // Author name input
        String author_name = Validation.validateStr("Author");

        // Quantity input
        int quantity = Validation.validateQuantity();


        return BookDao.addBook(title,author_name,quantity,ISBN);
    }
    public static boolean deleteBook(){
        // ISBN input
        Long ISBN = Validation.validateISBN();
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
                return true;
            }else return false;
        }
    }
    public static boolean  editBook(){
        // ISBN input
        Long ISBN = Validation.validateISBN();
        // Find the book
        Book book = BookDao.getBook("ISBN="+ISBN);
        if(book == null) {
            System.out.println("| !!! Book not found !!!                                                 |");
            return false;
        }
        System.out.println(book);
        System.out.println("|_____________________________________________________________________________|");
        // Title input
        String title = Validation.validateStr("Title");
        // Author input
        String author_name = Validation.validateStr("Author");
        // Quantity input
        int quantity = Validation.validateQuantity();
        // ISBN input
        Long newISBN = Validation.validateISBN();
        // updating the book
        return BookDao.updateBook(book.getId(),title,author_name,quantity,newISBN);
    }
}
