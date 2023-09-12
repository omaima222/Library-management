package utils;

import java.util.Scanner;

public class Validation {

    public static Scanner scanner = new Scanner(System.in);

    public static Long validateISBN(){
        Long ISBN = -1L;
        do {
            System.out.println("| Enter ISBN :                                                               |");
            try {
                ISBN = Long.parseLong(scanner.nextLine());
                if (ISBN <= 0 || String.valueOf(ISBN).length() != 13) {
                    System.out.println("| !!! ISBN must be a positive 13 digits number !!!                           |");
                }
            } catch (NumberFormatException e) {
                System.out.println("| !!! ISBN must be a positive 13 digits number !!!                           |");
                ISBN = -1L; // Reset to an invalid value to continue the loop
            }
        } while (ISBN <= 0 || String.valueOf(ISBN).length() != 13);
        return ISBN;
    }

    public static int validateQuantity(){
        int quantity = -1;
        do{
            System.out.println("| Enter quantity :                                                           |");
            try{
                quantity = Integer.parseInt(scanner.nextLine());
                if(quantity<=0){
                    System.out.println("| !!! Quantity must be a positive number !!!                                 |");
                }
            }catch (NumberFormatException e){
                System.out.println("| !!! Quantity must be a positive number !!!                                 |");
                quantity = -1;
            }
        }while(quantity<=0);
        return quantity;
    }

    public static String validateStr(String x){
        String str= "";
        do{
            System.out.println("| Enter "+x+" :                                                             |");
            str = scanner.nextLine();
            if (str == null || str.isEmpty() || str.isBlank()){
                System.out.println("| !!! "+x+" is required !!!                                                 |");
            }
        }while (str == null || str.isEmpty() || str.isBlank());
        return str;
    }


}


