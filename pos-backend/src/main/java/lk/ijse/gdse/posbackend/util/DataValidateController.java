package lk.ijse.gdse.posbackend.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidateController {
    static String CUS_ID_REGEX = "^C\\d{3}$";
    static String CUS_NAME_REGEX = "^[A-Za-z ]{5,}$";
   static String CUS_ADDRESS_REGEX = "^[A-Za-z0-9 ]{8,}$";
    static String CUS_SALARY_REGEX = "^\\d+(\\.\\d{1,2})?$";

    String ITEM_ID_REGEX = "^I\\d{3}$";
    String ITEM_NAME_REGEX = "^[A-Za-z ]{5,}$";
    String ITEM_TYPE_REGEX = "^[A-Za-z ]{5,}$";
    String ITEM_UNIT_PRICE_REGEX = "^\\$?\\d+(?:\\.\\d{2})?$";
    String ITEM_QTY_REGEX = "^\\d+$";


    public static boolean customerIdValidate(String customerId){
        Pattern pattern = Pattern.compile(CUS_ID_REGEX);
        Matcher matcher = pattern.matcher(customerId);
        return matcher.matches();

    }

    public static boolean customerNameValidate(String customerName){
        Pattern pattern=Pattern.compile(CUS_NAME_REGEX);
        Matcher matcher=pattern.matcher(customerName);
        return matcher.matches();

    }

    public static boolean customerAddressValidate(String customerAddress){
        Pattern pattern=Pattern.compile(CUS_ADDRESS_REGEX);
        Matcher matcher=pattern.matcher(customerAddress);
        return matcher.matches();

    }

    public static boolean customerSalaryValidate(String customerSalary){
        Pattern pattern=Pattern.compile(CUS_SALARY_REGEX);
        Matcher matcher=pattern.matcher(customerSalary);
        return matcher.matches();

    }

    //////////////////

    public static boolean itemIdValidate(String itemId){
        Pattern pattern=Pattern.compile(CUS_SALARY_REGEX);
        Matcher matcher=pattern.matcher(itemId);
        return matcher.matches();

    }
    public static boolean itemNameValidate(String itemName){
        Pattern pattern=Pattern.compile(CUS_SALARY_REGEX);
        Matcher matcher=pattern.matcher(itemName);
        return matcher.matches();

    }
    public static boolean itemTypeValidate(String itemType){
        Pattern pattern=Pattern.compile(CUS_SALARY_REGEX);
        Matcher matcher=pattern.matcher(itemType);
        return matcher.matches();

    }
    public static boolean itemPriceValidate(String itemPrice){
        Pattern pattern=Pattern.compile(CUS_SALARY_REGEX);
        Matcher matcher=pattern.matcher(itemPrice);
        return matcher.matches();

    }
    public static boolean itemQtyValidate(String itemQty){
        Pattern pattern=Pattern.compile(CUS_SALARY_REGEX);
        Matcher matcher=pattern.matcher(itemQty);
        return matcher.matches();

    }



}
