package suts.desigenpattens.learnnote;

/**
 * Created by sutingshuai on 2019-08-21
 * Describe:
 */
public class Test {

    public static void main(String[] args){
        String numStr1 = "123456";
        String numStr2 = "123456 ";
        String numStr3 = " 123456";
        String numStr4 = "123 456";

        String numStr5 = "123456a";
        String numStr6 = "123{456";
        String numStr7 = "";
        String numStr8 = "1.1";
        String numStr9 = "1.1.7";
        String numStr10 = "1.17";

        isNumber(numStr1.trim());
        isNumber(numStr2.trim());
        isNumber(numStr3.trim());
        isNumber(numStr4.trim());
        isNumber(numStr5.trim());
        isNumber(numStr6.trim());
        isNumber(numStr7.trim());
        isNumber(numStr8.trim());
        isNumber(numStr9.trim());
        isNumber(numStr10.trim());

    }

    public static void isNumber(String str){
        boolean isNumber =  str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
        System.out.println("isnumber = " + isNumber);
    }
}
