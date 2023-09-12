package controllers;

import models.BorrowingList;
import services.BorrowingListService;

import java.util.ArrayList;

public class BorrowingListController {

    public static void getBorrowedBooks(){
        System.out.println("________________________________________________________________________________________________________________________________");
        System.out.println("CIN                User                Book title                Book ISBN                Borrowing date                Return date                ");
        System.out.println("________________________________________________________________________________________________________________________________");
        ArrayList<BorrowingList> books = BorrowingListService.getBorrowedBooksList();
        if(books == null || books.isEmpty()){
            System.out.println("_________________________________ THIS LIST IS EMPTY _________________________________");
            return;
        }
        for(BorrowingList book : books){
            System.out.println(book);
        }
    }
}
