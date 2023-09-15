package utils;

import models.Book;
import repositories.BookDao;

import java.text.*;
import java.time.*;
import java.util.Date;
import java.util.Scanner;
import java.time.format.*;
public class Validation {

    public static Scanner scanner = new Scanner(System.in);

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static Long validateISBN(){
        Long ISBN = -1L;
        do {
            System.out.println("| Enter ISBN : ");
            try {
                ISBN = Long.parseLong(scanner.nextLine());
                if (ISBN <= 0 || String.valueOf(ISBN).length() != 13) {
                    System.out.println("| !!! ISBN must be a positive 13 digits number !!! ");
                }
            } catch (NumberFormatException e) {
                System.out.println("| !!! ISBN must be a positive 13 digits number !!!   ");
                ISBN = -1L; // Reset to an invalid value to continue the loop
            }
        } while (ISBN <= 0 || String.valueOf(ISBN).length() != 13);
        return ISBN;
    }

    public static int validateQuantity(){
        int quantity = -1;
        do{
            System.out.println("| Enter quantity :      ");
            try{
                quantity = Integer.parseInt(scanner.nextLine());
                if(quantity<=0){
                    System.out.println("| !!! Quantity must be a positive number !!! ");
                }
            }catch (NumberFormatException e){
                System.out.println("| !!! Quantity must be a positive number !!! ");
                quantity = -1;
            }
        }while(quantity<=0);
        return quantity;
    }

    public static String validateStr(String x){
        String str= "";
        do{
            System.out.println("| Enter "+x+" : ");
            str = scanner.nextLine();
            if (str == null || str.isEmpty() || str.isBlank()){
                System.out.println("!!! "+x+" is required !!!");
            }
        }while (str == null || str.isEmpty() || str.isBlank());
        return str;
    }

    public static LocalDate validateBorrowingDate(){
        LocalDate Borrowing_date = LocalDate.now();
        String bDate = Borrowing_date.toString();
        LocalDate borrowing_date = LocalDate.parse(bDate, formatter);
        return borrowing_date;
    }

    public static String validateReturnDate(LocalDate borrowingDate){
        String return_date = "";
        do{
            System.out.println("| Enter the return date in yyyy-mm-dd format :");
            return_date = scanner.next();
            try {
                LocalDate date = LocalDate.parse(return_date, formatter);
                if(date.isBefore(borrowingDate) || date.isEqual(borrowingDate) ){
                    System.out.println("!!! return date should be at least 1 day after !!! :");
                    return_date = "";
                }
            } catch (DateTimeParseException e) {
                System.out.println("!!! return date should be a valid date !!! :");
                return_date = "";
            }
        }while(return_date == null || return_date.isEmpty() || return_date.isBlank());
        return return_date;
    }
}


