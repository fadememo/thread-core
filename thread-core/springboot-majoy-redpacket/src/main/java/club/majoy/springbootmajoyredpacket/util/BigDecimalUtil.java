package club.majoy.springbootmajoyredpacket.util;

import java.math.BigDecimal;
import java.math.MathContext;

public class BigDecimalUtil {
    //加 MathContext 精度
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b, MathContext.DECIMAL128);
    }
    //减
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {

        return a.subtract(b, MathContext.DECIMAL128);
    }
    //乘
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {

        return a.multiply(b, MathContext.DECIMAL128);
    }
    //除
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {

        return a.divide(b, MathContext.DECIMAL128);
    }

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(1000.0001);
        BigDecimal b = new BigDecimal(9.999);
        System.out.println(a);
        System.out.println(add(a, b));
        System.out.println(multiply(a,b ));
        System.out.println(subtract(a,b ));
        System.out.println(divide(a,b));
    }




}
