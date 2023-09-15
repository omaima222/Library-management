package utils;

import models.Book;
import models.User;
import models.BorrowingList;
import models.MissingBooksList;
import services.BookService;
import services.BorrowingListService;

import java.util.ArrayList;

public class Console {

    public static void printBook(ArrayList<Book> books, String title){
        System.out.println("___________________________________________ "+title+" ___________________________________________");
        if(books == null || books.isEmpty()){
            System.out.println("________________________________________ ! No books found ! _______________________________________");
            return;
        }
        System.out.println("+--------------------------------+----------------------+----------------------+----------------------+");
        System.out.printf("| %-30s | %-20s | %-20s | %-20s |%n", "Title", "Author","ISBN number", "Quantity");
        System.out.println("+--------------------------------+----------------------+----------------------+----------------------+");
        for (Book book : books) {
            System.out.printf("| %-30s | %-20s | %-20s | %-20s |%n",
                    book.getTitle(),book.getAuthorName(),book.getISBNNumber(),book.getQuantity());
            System.out.println("+--------------------------------+----------------------+----------------------+----------------------+");
        }
    }

    public static void printBorrowingList(ArrayList<BorrowingList> books,String title){
        System.out.println("___________________________________________________________ "+title+" ___________________________________________________________");
        if (books == null || books.isEmpty()) {
            System.out.println("___________________________________________________________ ! No borrowed books found ! _____________________________________________________");
            return;
        }

            System.out.println("+------------+----------------------+--------------------------------+-----------------+-----------------+-----------------+-----------------+");
        System.out.printf("| %-10s | %-20s | %-30s | %-15s | %-15s | %-15s |  %-15s |%n", "CIN", "User", "Book Title", "Book ISBN","Book quantity", "Borrowing Date", "Return Date");
        System.out.println("+------------+----------------------+--------------------------------+-----------------+-----------------+-----------------+-----------------+");

        for (BorrowingList book : books) {
            System.out.printf("| %-10s | %-20s | %-30s | %-15s | %-15s | %-15s |  %-15s |%n",
                    book.getUser().getCin(), book.getUser().getFirstName() + " " + book.getUser().getLastName(),
                    book.getBook().getTitle(), book.getBook().getISBNNumber(),book.getQuantity(),
                    book.getBorrowingDate(), book.getReturnDate());
            System.out.println("+------------+----------------------+--------------------------------+-----------------+-----------------+-----------------+-----------------+");
        }
    }

    public static void printMissingList(ArrayList<MissingBooksList> books, String title){
        System.out.println("______________________________ "+title+" ______________________________");
        if (books == null || books.isEmpty()) {
            System.out.println("__________________________ ! No missing books found ! ___________________________");
            return;
        }

        System.out.println("+------------+----------------------+--------------------------------+-----------------+-----------------+-----------------+");
        System.out.printf("| %-10s | %-20s | %-30s | %-15s | %-15s | %-15s |%n", "CIN", "User", "Book Title", "Book ISBN", "Book quantity", "Missing Date");
        System.out.println("+------------+----------------------+--------------------------------+-----------------+-----------------+-----------------+");

        for (MissingBooksList book : books) {
            System.out.printf("| %-10s | %-20s | %-30s | %-15s | %-15s | %-15s |%n",
                    book.getUser().getCin(), book.getUser().getFirstName() + " " + book.getUser().getLastName(),
                    book.getBook().getTitle(), book.getBook().getISBNNumber(),book.getQuantity(),
                    book.getMissingDate());
            System.out.println("+------------+----------------------+--------------------------------+-----------------+-----------------+-----------------+");
        }
    }


    public static void printUsers(ArrayList<User> users, String title){
            System.out.println("___________________________ "+title+" __________________________");
        if (users == null || users.isEmpty()) {
            System.out.println("_______________________ ! No users found ! ______________________");
            return;
        }

        System.out.println("+------------+----------------------+----------------------+");
        System.out.printf("| %-10s | %-20s | %-20s |%n", "CIN", "First name", "Last name");
        System.out.println("+------------+----------------------+----------------------+");

        for (User user : users) {
            System.out.printf("| %-10s | %-20s | %-20s |%n",
                    user.getCin(),user.getFirstName(),user.getLastName());
                    System.out.println("+------------+----------------------+----------------------+");
        }
    }


}
