package controllers;

import repositories.BookDao;
import repositories.BorrowingListDao;
import repositories.MissingBooksDao;
import repositories.UserDao;
import services.BookService;
import models.Book;

import java.util.ArrayList;
import java.util.Scanner;

public class BookController {

    public static Scanner scanner = new Scanner(System.in);

    public static void getAvailableBooks(){
        System.out.println("|_____________________________ AVAILABLE BOOKS _____________________________|");
        ArrayList<Book> books = BookService.getAllAvailableBooks();
        if(books == null || books.isEmpty()){
            System.out.println("|_____________________________ NO BOOKS FOUND ______________________________|");
            return;
        }
        for (Book book : books) {
            System.out.println(book);
            System.out.println("|___________________________________________________________________________|");
        }
    }

    public static void searchByTitle(){
        System.out.println("|_____________________________ SEARCH BY TITLE _____________________________|");
        ArrayList<Book> books = BookService.searchByTitle();
        if(books == null || books.isEmpty()){
            System.out.println("|_____________________________ NO BOOKS FOUND ______________________________|");
            return;
        }
        for(Book book : books){
            System.out.println(book);
            System.out.println("|___________________________________________________________________________|");
        }
    }

    public static void searchByAuthor(){
        System.out.println("|_____________________________ SEARCH BY Author _____________________________|");
        ArrayList<Book> books = BookService.searchByAuthor();
        if(books == null || books.isEmpty()){
            System.out.println("|_____________________________ NO BOOKS FOUND ______________________________|");
            return;
        }
        for(Book book : books){
            System.out.println(book);
            System.out.println("|___________________________________________________________________________|");
        }
    }

    public static void addBook(){
        System.out.println("|________________________________ ADD A BOOK ________________________________|");
        if(BookService.addBook()) {
              System.out.println("|_________________________ SUCCESSFULLY ADDDED BOOK _________________________|");
        }else System.out.println("|________________________ !!!! OPERATION FAILED !!!! ________________________|");
    }

    public static void deleteBook(){
        System.out.println("|________________________________ DELETE A BOOK ________________________________|");
        if(BookService.deleteBook()) {
              System.out.println("|___________________________ SUCCESSFULLY DELETED ____________________________|");
        }else System.out.println("|________________________ !!!! OPERATION FAILED !!!! _________________________|");
    }

    public static void updateBook(){
        System.out.println("|_______________________________ UPDATE A BOOK _______________________________|");
        if(BookService.editBook()) {
            System.out.println("|___________________________ SUCCESSFULLY UPDATED ____________________________|");
        }else System.out.println("|________________________ !!!! OPERATION FAILED !!!! _________________________|");
    }

    public static void bookStatics(){
        int users = UserDao.countUsers();
        int availableBooks = BookDao.countAvailableBooks();
        int borrowedBooks = BorrowingListDao.countBorrowedBooks();
        int missingBooks = MissingBooksDao.countMissingBooks();
        int total = availableBooks + borrowedBooks + missingBooks;
        System.out.println("|________________________________ STATICS ________________________________");
        System.out.println("| TOTAL BOOKS      :                                                 "+total);
        System.out.println("|_________________________________________________________________________");
        System.out.println("| AVAILABLE BOOKS  :                                        "+availableBooks);
        System.out.println("|_________________________________________________________________________");
        System.out.println("| BORROWED BOOKS   :                                         "+borrowedBooks);
        System.out.println("|_________________________________________________________________________");
        System.out.println("| MISSING BOOKS    :                                         "+missingBooks);
        System.out.println("|_________________________________________________________________________");
        System.out.println("| USERS            :                                                "+users);
        System.out.println("|_________________________________________________________________________");
    }


}
