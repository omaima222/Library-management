package services;

import models.BorrowingList;
import models.MissingBooksList;
import repositories.BorrowingListDao;
import repositories.MissingBooksDao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MissingBooksService {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ArrayList<MissingBooksList> getAllMissingBooks(){
        ArrayList<MissingBooksList> missingBooksOfToday = MissingBooksDao.getMissingBooks("1");
        if(missingBooksOfToday==null) return null;
        return missingBooksOfToday;
    }

    public static ArrayList<MissingBooksList> reportMissingBooksOfTheDay(){
        ArrayList<BorrowingList> borrowedBooks = BorrowingListDao.getBorrowedBooks("1");
        LocalDate today = LocalDate.now();
        today = LocalDate.parse(today.toString(), formatter);
        if(borrowedBooks!=null){
            for (BorrowingList book : borrowedBooks) {
                LocalDate returnDate = LocalDate.parse(book.getReturnDate().toString(), formatter);
                if (returnDate.isBefore(today)) {
                    BorrowingListDao.deleteBookFromBorrowedBooks(book.getId());
                    MissingBooksDao.addToMissingBooks(book.getBook().getId(), book.getUser().getId(), book.getQuantity(), today.toString());
                }
            }
        }
        ArrayList<MissingBooksList> missingBooksOfToday = MissingBooksDao.getMissingBooks("missing_date='"+today.toString()+"'");
        if(missingBooksOfToday==null) return null;
        return missingBooksOfToday;
    }


}
