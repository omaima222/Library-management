package services;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import models.Book;
import repositories.BookDao;
import repositories.BorrowingListDao;
import repositories.UserDao;
import utils.MyJDBC;
import models.BorrowingList;
import models.User;
import services.BookService;
import services.UserService;
import utils.Validation;

public class BorrowingListService {
    public static Connection cnn = MyJDBC.cnn();
    public static Scanner scanner = new Scanner(System.in);
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static ArrayList<BorrowingList>  getBorrowedBooksList() {
        return BorrowingListDao.getBorrowedBooks("1");
    }

    public static ArrayList<BorrowingList>  getBorrowedBooksListByUser() {
        // input cin
        String cin = Validation.validateStr("user's cin");
        return BorrowingListDao.getBorrowedBooks("user.cin='"+cin+"'");
    }

    public static boolean borrowBook(){

        // cin input
        String cin = Validation.validateStr("the user's cin");
        // find the user
        User user = UserDao.getUser("cin='"+cin+"'");
        if(user==null) {
            System.out.println("!!! User not found !!!");
            return false;
        }
        int user_id = user.getId();

        // ISBN input
        Long ISBN = Validation.validateISBN();
        // find the book
        Book book = BookDao.getBook("ISBN="+ISBN);
        if(book==null) {
            System.out.println("!!! Book not found !!!");
            return false;
        }
        // check if the book is available
        else if(book.getQuantity()==0){
            System.out.println("!!! Book not available !!!");
            return false;
        }
        int book_id = book.getId();

        // quantity input
        int quantity;
        // check if the quantity is more than the book's quantity
        do{
            quantity = Validation.validateQuantity();
            if(quantity>book.getQuantity()){
                System.out.println("!!! only "+book.getQuantity()+" books available !!!");
            }
        }while (quantity>book.getQuantity());

        // borrowing date input
        LocalDate borrowing_date = Validation.validateBorrowingDate();

        // return date input
        String return_date = Validation.validateReturnDate(borrowing_date);

        // check if this book is previously borrowed
        ArrayList<BorrowingList> borrowedBooks = BorrowingListDao.getBorrowedBooks("book_id ="+book_id+" and user_id ="+user_id);

        // if it is then ask for confirmation
        if(borrowedBooks!=null){
            int borrowedQuantity = borrowedBooks.stream().mapToInt(x -> x.getQuantity()).sum();
            System.out.println("!!! This user has already borrowed "+borrowedQuantity+" of this book !!!");
            System.out.println("Enter Y to allow this operation :");
            scanner.nextLine();
            String answer = scanner.next();
            // if it's not confirmed stop here
            if(!answer.equals("Y")) {
                System.out.println("!!! Operation denied !!!");
                return true;
            }
        }

        // else add a borrowed book
        return BorrowingListDao.addToBorrowedBooks(book_id,user_id,borrowing_date.toString(),return_date,quantity);
    }

    public static boolean returnBook(){

        // cin input
        String cin = Validation.validateStr("the user's cin");
        // find the user
        User user = UserDao.getUser("cin='"+cin+"'");
        if(user==null) {
            System.out.println("!!! User not found !!!");
            return false;
        }
        int user_id = user.getId();

        // ISBN input
        Long ISBN = Validation.validateISBN();
        // find the book
        Book book = BookDao.getBook("ISBN="+ISBN);
        if(book==null) {
            System.out.println("!!! Book not found !!!");
            return false;
        }
        int book_id = book.getId();

        // find the borrowed book
        String condition = "book_id ="+book.getId()+" ORDER BY borrowing_date ASC";
        BorrowingList borrowedBook = BorrowingListDao.getBorrowedBook(condition);
        if(borrowedBook==null){
            System.out.println("!!! This book is not in the borrowing list !!!");
            return false;
        }


        // Quantity input
        int quantity = Validation.validateQuantity();


        // if the quantities are equal delete the book from the list
        if(borrowedBook.getQuantity() == quantity){
            if (BorrowingListDao.deleteBookFromBorrowedBooks(borrowedBook.getId())){
                return false;
            }
        }

        // if the quantity is less update the borrowed book's quantity
        else if(borrowedBook.getQuantity() > quantity){
            int newQuantity = borrowedBook.getQuantity() - quantity;
            if (BorrowingListDao.updateBookQuantityFromBorrowedBooks(borrowedBook.getId(), newQuantity)){
                return false;
            }
        }

        //if the quantity is more
        else if(borrowedBook.getQuantity() < quantity){
            int leftQuantity = quantity - borrowedBook.getQuantity();
            System.out.println("!!! "+leftQuantity+" books are left !!!");
            System.out.println("!!! Restart the operation to check for borrowed copies from different dates !!!");
            if(!BorrowingListDao.deleteBookFromBorrowedBooks(borrowedBook.getId())){
               return false;
            }
            quantity = borrowedBook.getQuantity();
        }

        return BookDao.updateBook(book.getId(),book.getTitle(),book.getAuthorName(),book.getQuantity()+quantity,book.getISBNNumber());
    }
}
