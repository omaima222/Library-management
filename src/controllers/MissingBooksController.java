package controllers;

import models.MissingBooksList;
import services.MissingBooksService;
import utils.Console;

public class MissingBooksController {
    public static void getMissingList(){
        Console.printMissingList(MissingBooksService.getAllMissingBooks(),"MISSING BOOKS LIST");
    }

    public static void reportMissingBooksOfTheDay(){
        Console.printMissingList(MissingBooksService.reportMissingBooksOfTheDay(),"MISSING BOOKS OF TODAY");
    }
}
