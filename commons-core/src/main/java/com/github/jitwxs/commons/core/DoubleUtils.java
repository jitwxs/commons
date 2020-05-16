package com.github.jitwxs.commons.core;

import java.math.BigDecimal;

/**
 * @author jitwxs
 * @date 2020年05月16日 23:00
 */
public class DoubleUtils {
    /**
     * 对double数据进行取精度. 返回最大的（最接近正无穷大）double 值，该值小于等于参数，并等于某个整数。
     *
     * @param value double数据.
     * @param scale 精度位数(保留的小数位数).
     * @return 精度计算后的数据.
     */
    public static double round(double value, int scale) {
        int n = (int)Math.pow(10, scale);
        return divide(Math.floor(multiply(value, n)), n, scale);
    }

    /**
     * 小数向上进位
     */
    public static double roundUp(double value, int scale) {
        int n = (int)Math.pow(10, scale);
        return divide(Math.ceil(multiply(value, n)), n, scale);
    }

    /**
     * double 相加
     */
    public static double add(double d1, double d2) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);
        return bd1.add(bd2).doubleValue();
    }

    /**
     * double 多数相加
     */
    public static double add(double d1, double d2, double... dn) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);

        BigDecimal result = bd1.add(bd2);
        if(dn != null && dn.length > 0) {
            for(double n : dn) {
                result = result.add(BigDecimal.valueOf(n));
            }
        }

        return result.doubleValue();
    }

    /**
     * double 相减
     */
    public static double subtract(double d1, double d2) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 多数相减
     */
    public static double subtract(double d1, double d2, double... dn) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);

        BigDecimal result = bd1.subtract(bd2);
        if(dn != null && dn.length > 0) {
            for(double n : dn) {
                result = result.subtract(BigDecimal.valueOf(n));
            }
        }

        return result.doubleValue();
    }

    /**
     * double 乘法
     */
    public static double multiply(double d1, double d2) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);

        return bd1.multiply(bd2).doubleValue();
    }

    /**
     * double 多数乘法
     */
    public static double multiply(double d1, double d2, double... dn) {
        BigDecimal bd1 = BigDecimal.valueOf(d1);
        BigDecimal bd2 = BigDecimal.valueOf(d2);

        BigDecimal result = bd1.multiply(bd2);
        if(dn != null && dn.length > 0) {
            for(double n : dn) {
                result = result.multiply(BigDecimal.valueOf(n));
            }
        }

        return result.doubleValue();
    }

    /**
     * double 除法
     *
     * @param dividend 被除数
     * @param divisor 除数
     * @param scale 保留小数点
     * @return 四舍五入
     */
    public static double divide(double dividend, double divisor, int scale) {
        if(divisor == 0L) {
            throw new ArithmeticException("divide operate divisor is zero");
        }

        BigDecimal bd1 = BigDecimal.valueOf(dividend);
        BigDecimal bd2 = BigDecimal.valueOf(divisor);
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * double 除法
     *
     * @param dividend 被除数
     * @param divisor 除数
     * @param scale 保留小数点
     * @return 向正无穷取值
     */
    public static double divideUp(double dividend, double divisor, int scale) {
        if(divisor == 0L) {
            throw new ArithmeticException("divide operate divisor is zero");
        }

        BigDecimal bd1 = BigDecimal.valueOf(dividend);
        BigDecimal bd2 = BigDecimal.valueOf(divisor);
        return bd1.divide(bd2, scale, BigDecimal.ROUND_CEILING).doubleValue();
    }

    /**
     * double 除法
     *
     * @param dividend 被除数
     * @param divisor 除数
     * @param scale 保留小数点
     * @return 向负无穷取值
     */
    public static double divideDown(double dividend, double divisor, int scale) {
        if(divisor == 0L) {
            throw new ArithmeticException("divide operate divisor is zero");
        }

        BigDecimal bd1 = BigDecimal.valueOf(dividend);
        BigDecimal bd2 = BigDecimal.valueOf(divisor);
        return bd1.divide(bd2, scale, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    public static boolean equals(double num1, double num2) {
        BigDecimal bd1 = BigDecimal.valueOf(num1);
        BigDecimal bd2 = BigDecimal.valueOf(num2);

        return bd1.equals(bd2);
    }

    public static String toString(double num) {
        return BigDecimal.valueOf(num).stripTrailingZeros().toPlainString();
    }
}
