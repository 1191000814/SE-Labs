package test;

import org.junit.Test;
import pojo.Lab;

/*
顺序测试
 */
public class LabTest{

    // 顺序测试
    @Test
    public void testOrder(){
        int num1 = Lab.strToInt("1");
        System.out.println(num1);
        int num2 = Lab.strToInt("0123");
        System.out.println(num2);
        int num3 = Lab.strToInt("a");
        System.out.println(num3);
        int num4 = Lab.strToInt("2147483648");
        System.out.println(num4);
        int num5 = Lab.strToInt("-1");
        System.out.println(num5);
    }

    // 分支测试
    @Test
    public void testBranch(){
        System.out.println("2000 " + Lab.isLeapYear(2000));
        System.out.println("2003 " + Lab.isLeapYear(2003));
        System.out.println("2004 " + Lab.isLeapYear(2004));
        System.out.println("1900 " + Lab.isLeapYear(1900));
    }

    @Test
    public void testCircle(){
        System.out.println("-1 " + Lab.isPrime(-1));
        System.out.println("0 " + Lab.isPrime(0));
        System.out.println("1 " + Lab.isPrime(1));
        System.out.println("2 " + Lab.isPrime(2));
        System.out.println("12 " + Lab.isPrime(12));
        System.out.println("13 " + Lab.isPrime(13));
    }
}
