package suts.desigenpattens.learnnote;

/**
 * Created by sutingshuai on 2019-08-21
 * Describe:
 */
public class Test {

    public static void main(String[] args){
        int s = 3;

        switch (s) {
            case 0:
            case 1:
                System.out.println("好的");
            case 2:
            case 3:
            default:
                System.out.println("好吗");
        }

    }

    public static void isNumber(String str){
        boolean isNumber =  str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
        System.out.println("isnumber = " + isNumber);
    }
}
