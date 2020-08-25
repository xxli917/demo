package com.nucarf.base.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Amor on 2018/5/25.
 */

public class NumberUtils {

    /**
     * 四舍五入保留两位
     *
     * @param money
     * @return
     */
    public static String totalMoney(String money) {
        if (TextUtils.isEmpty(money)) {
            return " ";
        }
        Double mAmountF = Double.parseDouble(money) / 100;
        BigDecimal bigDec = new BigDecimal(mAmountF);
        double total = bigDec.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(total);
    }

    /**
     * 数字抹零
     * 抹零字段 结算开关（1关 2抹百位 3抹拾位 4抹个位 5抹小数位 6小数点后一位 7小数点后2位（不进位） 8小数点后2位（进位））
     *
     * @param position
     * @return
     */
    public static String getFormatNumber(String position, String number) {
        if (TextUtils.isEmpty(position)) {
            return number;
        }
        switch (Integer.parseInt(position)) {
            case 1:
                BigDecimal divide5 = new BigDecimal(number).multiply(new BigDecimal("100"));
                return ((int) divide5.doubleValue()) / 100f + "";
            case 2:
                BigDecimal divide = new BigDecimal(number).multiply(new BigDecimal("100")).divide(new BigDecimal("100000"));
                return ((int) divide.doubleValue()) * 1000 + "";
            case 3:
                BigDecimal divide1 = new BigDecimal(number).multiply(new BigDecimal("100")).divide(new BigDecimal("10000"));
                return ((int) divide1.doubleValue()) * 100 + "";
            case 4:
                BigDecimal divide2 = new BigDecimal(number).multiply(new BigDecimal("100")).divide(new BigDecimal("1000"));
                return ((int) divide2.doubleValue()) * 10 + "";
            case 5:
                return (int) Double.parseDouble(number) + "";
            case 6:
                BigDecimal divide3 = new BigDecimal(number).multiply(new BigDecimal("100")).divide(new BigDecimal("10"));
                return ((int) divide3.doubleValue()) / 10f + "";
            case 7:
                BigDecimal divide4 = new BigDecimal(number).multiply(new BigDecimal("100"));
                return ((int) divide4.doubleValue()) / 100f + "";
            case 8:
                return numberHalfUp(number);
            default:
                return number;
        }
    }

    /**
     * 两位小数向上取整
     *
     * @param money
     * @return
     */
    public static String numberHalfUp(String money) {
        Double mAmountF = Double.parseDouble(money);
        BigDecimal bigDec = new BigDecimal(mAmountF);
        double total = bigDec.setScale(2, BigDecimal.ROUND_CEILING)
                .doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(total);
    }

    /**
     * 两位小数向下取整
     *
     * @param money
     * @return
     */
    public static String numberHalfDown(String money) {
        Double mAmountF = Double.parseDouble(money);
        BigDecimal bigDec = new BigDecimal(mAmountF);
        double total = bigDec.setScale(2, BigDecimal.ROUND_FLOOR)
                .doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(total);
    }

    //获取截取后的金额字符串类型
    public static String getEndAmountString(String pAmount) {
        if (!TextUtils.isEmpty(pAmount)) {
            Double mAmountF = Double.parseDouble(pAmount);
            return amountToFolat(mAmountF);
        } else {
            return "0";
        }
    }

    //将金额转成 float 并且 只截取整数
    public static String amountToInt(Double f) {
        return Math.round(f) + "";
    }

    //将金额转成 float 并且 只截取整数
    public static String amountToFolat(Double f) {
        BigDecimal bigDec = new BigDecimal(f);
        double total = bigDec.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(total);
    }
    public static String amountToString(Double d,double divder){
        BigDecimal b = new BigDecimal(d);
        b=b.divide(new BigDecimal(divder),2,BigDecimal.ROUND_HALF_UP);
        return b+"";
    }
    public static String amountToStringTree(Double d,double divder){
        BigDecimal b = new BigDecimal(d);
        b=b.divide(new BigDecimal(divder),3,BigDecimal.ROUND_HALF_UP);
        return b+"";
    }

    //将金额转成 float 并且 只截取整数 一位小数
    public static String amountToFolatWithOne(Double f) {
        BigDecimal bigDec = new BigDecimal(f);
        double total = bigDec.setScale(1, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
        DecimalFormat df = new DecimalFormat("0.0");
        return df.format(total);
    }

    /**
     * 转换万
     *
     * @param number
     * @return
     */
    public static String formatWan(String number) {
        BigDecimal bigDecimal = new BigDecimal(number);
        if (bigDecimal.compareTo(BigDecimal.valueOf(10000)) == 1) {
            // 转换为万元（除以10000）
            BigDecimal decimal = bigDecimal.divide(new BigDecimal("10000"));
            // 保留两位小数
            DecimalFormat formater = new DecimalFormat("0.00");
            // 四舍五入
            formater.setRoundingMode(RoundingMode.HALF_UP);    // 5000008.89

            // 格式化完成之后得出结果
            String formatNum = formater.format(decimal) + "W";
            return formatNum;
        } else {
            return number;
        }

    }

    /**
     * BigDecimal 相加
     *
     * @param v1
     * @param v2
     * @return double
     */
    public static Double add(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.add(n2).doubleValue();
    }

    /**
     * BigDecimal 相减
     *
     * @param v1
     * @param v2
     * @return double
     */
    public static Double subtract(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.subtract(n2).doubleValue();
    }

    /**
     * BigDecimal 相乘
     *
     * @param v1
     * @param v2
     * @return double
     */
    public static Double multiply(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.multiply(n2).doubleValue();
    }

    /**
     * BigDecimal 相除
     *
     * @param v1
     * @param v2
     * @return double
     */
    public static Double divide(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.divide(n2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 比较大小 小于0：v1 < v2 大于0：v1 > v2 等于0：v1 = v2
     *
     * @param v1
     * @param v2
     * @return
     */
    public static int compare(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.compareTo(n2);
    }

    /**
     * 让 Map按key进行排序
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    //实现一个比较器类
    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareTo(s2);  //从小到大排序
        }

    }


}
