import controllers.BookController;
import models.Book;
import repositories.BookDao;
import repositories.BorrowingListDao;
import repositories.MissingBooksDao;
import repositories.UserDao;
import services.BookService;
import services.BorrowingListService;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args){
        //BookService.addBook();
        //System.out.println(BorrowingListDao.countBorrowedBooks());
        //System.out.println(MissingBooksDao.countMissingBooks());
        //System.out.println(BookDao.countAvailableBooks());
        //System.out.println(UserDao.countUsers());
        BookController.deleteBook();
    }
}
