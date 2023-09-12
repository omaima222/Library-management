import controllers.BookController;
import controllers.BorrowingListController;
import models.Book;
import repositories.BookDao;
import repositories.BorrowingListDao;
import repositories.MissingBooksDao;
import repositories.UserDao;
import services.BookService;
import services.BorrowingListService;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args){
        BorrowingListController.getBorrowedBooks();

        Scanner scanner = new Scanner(System.in);
        System.out.println("*______________________________________ MENU ______________________________________*");
        System.out.println("| 1  =>  Available books                                                           |");
        System.out.println("| 2  =>  Search for a book by title                                                |");
        System.out.println("| 3  =>  Search for a book by author                                               |");
        System.out.println("| 4  =>  Add a book                                                                |");
        System.out.println("| 5  =>  Delete a book                                                             |");
        System.out.println("| 6  =>  Edit a book                                                               |");
        System.out.println("| 7  =>  Borrow  a book                                                            |");
        System.out.println("| 8  =>  Return  a book                                                            |");
        System.out.println("| 9  =>  Borrowing  list                                                           |");
        System.out.println("| 10  =>  Missing books list                                                        |");
        System.out.println("| 11 =>  Statistics                                                                |");
        System.out.println("|__________________________________________________________________________________|");
        System.out.println("|                      * Enter a number to pursue an action *                      |");
        System.out.println("|__________________________________________________________________________________|");

        int action = scanner.nextInt();

        switch (action){
            case 1:
                BookController.getAvailableBooks();
            case 2:
                BookController.searchByTitle();
            case 3:
                BookController.searchByAuthor();
            case 4:
                BookController.addBook();
            case 5:
                BookController.deleteBook();
            case 6:
                BookController.updateBook();
            case 9:
                BorrowingListController.getBorrowedBooks();
        }
    }
}
