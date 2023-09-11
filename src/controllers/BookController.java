package controllers;

import repositories.BookDao;
import repositories.BorrowingListDao;
import repositories.MissingBooksDao;
import repositories.UserDao;
import services.BookService;

import java.util.Scanner;

public class BookController {

    public static Scanner scanner = new Scanner(System.in);
    public static void addBook(){
        System.out.println("|________________________________ ADD A BOOK ________________________________|");
        System.out.println("| Enter Title :                                                              |");
        String title = scanner.nextLine();
        System.out.println("| Enter Author :                                                             |");
        String author_name = scanner.nextLine();
        System.out.println("| Enter ISBN :                                                               |");
        Long ISBN =  scanner.nextLong();
        System.out.println("| Enter quantity :                                                           |");
        int quantity =  scanner.nextInt();
        if(BookService.addBook(title,author_name,quantity,ISBN)) {
              System.out.println("|_________________________ SUCCESSFULLY ADDDED BOOK _________________________|");
        }else System.out.println("|________________________ !!!! OPERATION FAILED !!!! ________________________|");
    }

    public static void deleteBook(){
        System.out.println("|________________________________ DELETE A BOOK ________________________________|");
        System.out.println("| Enter ISBN :                                                                  |");
        Long ISBN =  scanner.nextLong();
        if(BookService.deleteBook(ISBN)) {
              System.out.println("|___________________________ SUCCESSFULLY DELETED ____________________________|");
        }else System.out.println("|________________________ !!!! OPERATION FAILED !!!! _________________________|");
    }

    public static void BookStatics(){
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
