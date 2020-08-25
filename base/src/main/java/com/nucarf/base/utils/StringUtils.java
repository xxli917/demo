package com.nucarf.base.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nucarf.base.widget.LabelsView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/3/16.
 */

public class StringUtils {

    /**
     * 获取两位小数的金钱
     *
     * @param price
     * @return
     */
    public static String getPrice(double price) {
        DecimalFormat df = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
        return df.format(price) + "元";// format 返回的是字符串
    }

    /**
     * String is not empty
     */
    public static boolean isNoEmpty(@Nullable CharSequence str) {
        return !(str == null || str.length() == 0);
    }

    /**
     * 显示带有*号的手机号码
     *
     * @param strPhone
     * @return
     */
    public static String phoneFormat(String strPhone) {
        if (TextUtils.isEmpty(strPhone)) {
            return "";
        }
        return strPhone.substring(0, 3) + "****"
                + strPhone.substring(7, strPhone.length());
    }

    /**
     * 获取两位小数的金钱
     *
     * @param price
     * @return
     */
    public static String getPriceDouble(double price) {
        DecimalFormat df = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return df.format(price);//format 返回的是字符串
    }

    /**
     * 获取没有小数的金钱
     *
     * @param price
     * @return
     */
    public static String getIntPrice(double price) {
        DecimalFormat df = new DecimalFormat("0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return df.format(price) + "元";//format 返回的是字符串
    }

    /**
     * 获取不带包名的类名称
     *
     * @return
     */
    public static String getClassName(Class cls) {
        return cls.getCanonicalName().replace(cls.getPackage().getName() + ".", "");
    }

    /**
     * 中间加*的身份证号
     */
    public static String IDIntercept(String peopleId) {
        String s1 = peopleId.substring(0, 4);
        String s2 = peopleId.substring(peopleId.length() - 2, peopleId.length());
        String formatID = String.format("%s****%s", s1, s2);
        return formatID;
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param data 要转换的字节数组
     * @return 转换后的结果
     */
    public static final String byteArrayToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] d = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return d;
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 检测有几个中文汉字
     */
    public static int checkChineseCount(String str) {
        String regex = "[\\u4e00-\\u9fa5]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        int chineseCount = 0;
        while (matcher.find()) {
            chineseCount++;
        }
        return chineseCount;
    }

    /**
     * 检查两次String是否相同
     * str1:在传入是要保证不为null
     */
    public static boolean isStringWhetherSame(String str1, String str2) {
        return str1.equals(str2);
    }

    /**
     * 拼接两个字符串
     */
    public static String connectString(String str1, String str2) {
        return str1.concat(str2);
    }

    /**
     * 图片URI转为绝对路径
     *
     * @param
     * @return
     */
    public static String getPath(Activity activity, Intent intent) {
        Uri uri = getPictureUri(activity, intent);
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * 解决小米手机上获取图片路径为null的情况
     */
    public static Uri getPictureUri(Activity activity, Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = activity.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    /**
     * 截取第一组数字
     */
    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }


    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1]\\d{10}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number.trim())) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    /**
     * 判断身份证号是否合法
     *
     * @param str
     * @return
     */
    public static boolean isIdCard(String str) {
        //\\d{14}|\\d{17})(\\d|[xX])$
//        Pattern p = Pattern.compile("(\\d{18})|(\\d{17}[xX])");
        Pattern p = Pattern.compile("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 文字描色
     *
     * @param context
     * @param wholeStr
     * @param highlightStr
     * @param color
     * @return
     */
    public static SpannableStringBuilder fillColor(Context context, String wholeStr, String highlightStr, int color) {

        int start = 0, end = 0;
        if (!TextUtils.isEmpty(wholeStr) && !TextUtils.isEmpty(highlightStr)) {
            if (wholeStr.contains(highlightStr)) {
                /*
                 *  返回highlightStr字符串wholeStr字符串中第一次出现处的索引。
                 */
                start = wholeStr.indexOf(highlightStr);
                end = start + highlightStr.length();
            } else {
                return null;
            }
        } else {
            return null;
        }
        SpannableStringBuilder spBuilder = new SpannableStringBuilder(wholeStr);
        color = context.getResources().getColor(color);
        CharacterStyle charaStyle = new ForegroundColorSpan(color);
        spBuilder.setSpan(charaStyle, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spBuilder;
    }

    /**
     * 格式化日期
     *
     * @param pattern
     * @param time
     * @return
     */
    public static String formatData(String pattern, long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return format.format(date);
    }

    public static long getStringToDate(String pattern) {
        String dateString = formatData(pattern, System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    //判断字符串是否为JSON格式
    public static boolean isJson(String jsonStr) {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(jsonStr);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }

    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;

    public static String bytesToHuman(final long value) {
        final long[] dividers = new long[]{T, G, M, K, 1};
        final String[] units = new String[]{"TB", "GB", "MB", "KB", "B"};
        if (value < 1)
            return 0 + " " + units[units.length - 1];
        String result = null;
        for (int i = 0; i < dividers.length; i++) {
            final long divider = dividers[i];
            if (value >= divider) {
                result = format(value, divider, units[i]);
                break;
            }
        }
        return result;
    }

    private static String format(final long value,
                                 final long divider,
                                 final String unit) {
        final double result =
                divider > 1 ? (double) value / (double) divider : (double) value;
        return new DecimalFormat("#.##").format(result) + " " + unit;
    }

    public static long calculateLength(CharSequence c, boolean mpdistinguishChineseOrEnglish) {
        double len = 0;
        if (mpdistinguishChineseOrEnglish) {
            for (int i = 0; i < c.length(); i++) {
                int tmp = (int) c.charAt(i);
                if (tmp > 0 && tmp < 127) {
                    len += 0.5;
                } else {
                    len++;
                }
            }
        } else {
            for (int i = 0; i < c.length(); i++) {
                len++;

            }
        }
        return Math.round(len);
    }

    /**
     * 设置labview 默认两种颜色
     *
     * @param labelsView
     * @param labelstr
     */
    public static void setLabs(LabelsView labelsView, List<String> labelstr) {
        List<int[]> colors = new ArrayList<>();
        for (int i = 0; i < labelstr.size(); i++) {
            if (i == 0) {
                String colStr = GlobalVar.label_colors[0];
                int[] col = new int[]{Color.parseColor(colStr), 0};
                colors.add(col);
            } else {
                String colStr = GlobalVar.label_colors[1];
                int[] col = new int[]{Color.parseColor(colStr), 0};
                colors.add(col);
            }
        }
        labelsView.setLabels(labelstr, colors);
    }
    /**
     * 设置labview 默认一种颜色
     *
     * @param labelsView
     * @param labelstr
     */
    public static void setLabs(LabelsView labelsView, List<String> labelstr, String label_color) {
        List<int[]> colors = new ArrayList<>();
        for (int i = 0; i < labelstr.size(); i++) {
            if (i == 0) {
                String colStr = label_color;
                int[] col = new int[]{Color.parseColor(colStr), 0};
                colors.add(col);
            } else {
                String colStr = label_color;
                int[] col = new int[]{Color.parseColor(colStr), 0};
                colors.add(col);
            }
        }
        labelsView.setLabels(labelstr, colors);
    }

    //最长显示5个字符,中间...
    public static String limitLength(String str){

        if(TextUtils.isEmpty(str)){
            return null;
        }
        if(str.length()<=5){
            return str;
        }
        str = str.substring(0,2)+"..."+str.substring(str.length()-2);
        return str;


    }
}
