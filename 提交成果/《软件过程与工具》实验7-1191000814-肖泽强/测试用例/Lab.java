package pojo;

/*
软件过程与工具实验7
 */
public class Lab{

    /**
     * 字符串转整数
     * @param num 字符串
     * @return 整数
     */
    public static int strToInt(String num){
        int num1 = 0;
        try{
            num1 = Integer.parseInt(num);
        }catch(Exception e){
            System.out.println("非法的输入: " + num);
        }
        return num1;
    }

    /**
     * @param year 年份
     * @return 是否是闰年
     */
    public static boolean isLeapYear(int year){
        if(year % 4 != 0)
            return false;
        return year % 100 != 0 || year % 400 == 0;
    }

    /**
     * @param num 一个数字
     * @return 是否是素数
     */
    public static boolean isPrime(int num){
        if(num <= 1)
            return false;
        if(num == 2)
            return true;
        for(int i = 2; i < num; i ++){
            if(num % i == 0){
                return false;
            }
        }
        return true;
    }

}
