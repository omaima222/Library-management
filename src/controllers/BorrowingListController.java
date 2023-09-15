package controllers;

import models.BorrowingList;
import services.BookService;
import services.BorrowingListService;
import utils.Console;

import java.util.ArrayList;

public class BorrowingListController {

    public static void getBorrowedBooks(){
        Console.printBorrowingList(BorrowingListService.getBorrowedBooksList(),"BORROWED BOOKS");
    }

    public static void getBorrowedBooksByUser(){
        Console.printBorrowingList(BorrowingListService.getBorrowedBooksListByUser(),"BORROWED BOOKS BY USER");
    }

    public static void borrowBook(){
        System.out.println("|________________________________ BORROW A BOOK ________________________________|");
        if(BorrowingListService.borrowBook()){
               System.out.println("|__________________________ SUCCESSFULLY BORROWED BOOK _________________________|");
        }else  System.out.println("|_________________________ !!!! OPERATION FAILED !!!! __________________________|");
    }

    public static void returnBook(){
        System.out.println("|________________________________ RETURN A BOOK ________________________________|");
        if(BorrowingListService.returnBook()){
               System.out.println("|__________________________ SUCCESSFULLY RETURNED BOOK _________________________|");
        }else  System.out.println("|_________________________ !!!! OPERATION FAILED !!!! __________________________|");
    }

}
