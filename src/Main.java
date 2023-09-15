import controllers.BookController;
import controllers.BorrowingListController;
import controllers.MissingBooksController;
import controllers.UserController;
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
    public static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("*______________________________________ MENU ______________________________________*");
        System.out.println("| 1  =>  Available books                                                           |");
        System.out.println("| 2  =>  Search for a book by title                                                |");
        System.out.println("| 3  =>  Search for a book by author                                               |");
        System.out.println("| 4  =>  Add a book                                                                |");
        System.out.println("| 5  =>  Delete a book                                                             |");
        System.out.println("| 6  =>  Edit a book                                                               |");
        System.out.println("| 7  =>  Borrow a book                                                             |");
        System.out.println("| 8  =>  Return a book                                                             |");
        System.out.println("| 9  =>  Borrowing list                                                            |");
        System.out.println("| 10 =>  Missing books list                                                        |");
        System.out.println("| 11 =>  Report missing books of the day                                           |");
        System.out.println("| 12 =>  Users list                                                                |");
        System.out.println("| 13 =>  Add user                                                                  |");
        System.out.println("| 14 =>  Get a user's borrowing list                                               |");
        System.out.println("| 15 =>  Statistics                                                                |");
        System.out.println("|__________________________________________________________________________________|");
        System.out.println("|                      * Enter a number to pursue an action *                      |");
        System.out.println("|__________________________________________________________________________________|");

        int action = scanner.nextInt();

        switch (action){
            case 1:
                BookController.getAvailableBooks();
                break;
            case 2:
                BookController.searchByTitle();
                break;
            case 3:
                BookController.searchByAuthor();
                break;
            case 4:
                BookController.addBook();
                break;
            case 5:
                BookController.deleteBook();
                break;
            case 6:
                BookController.updateBook();
                break;
            case 7:
                BorrowingListController.borrowBook();
                break;
            case 8:
                BorrowingListController.returnBook();
                break;
            case 9:
                BorrowingListController.getBorrowedBooks();
                break;
            case 10:
                MissingBooksController.getMissingList();
                break;
            case 11:
                MissingBooksController.reportMissingBooksOfTheDay();
                break;
            case 12:
                UserController.getAllUsers();
                break;
            case 13:
                UserController.addUser();
                break;
            case 14:
                BorrowingListController.getBorrowedBooksByUser();
                break;
            case 15:
                BookController.bookStatics();
                break;
            default:
                System.out.println("_________________________! This number isn't in the menu !_________________________");
        }
    }

}
