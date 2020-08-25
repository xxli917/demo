package com.nucarf.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.nucarf.base.R;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片工具类
 *
 * @author dada
 */
public class ImageUtil {
    public static ImageUtil instance;

    public static ImageUtil getInstance() {
        if (null == instance) {
            instance = new ImageUtil();
        }

        return instance;
    }
    /**
     * 图片最终裁剪宽度
     */
    public static final int INSERT_CROP_IMAGE_WIDTH = 1280;
    /**
     * 图片最终裁剪高度
     */
    public static final int INSERT_CROP_IMAGE_HEIGHT = 1280;
    /**
     * 图片最大尺寸
     */
    public static final int MAX_INSERT_FILE_SIZE = 500 * 1024;
    public static final String URI_PREFIX_FILE = "file://";

    private ImageUtil() {
    }

    /**
     * 计算图片sample
     *
     * @param options   :图片options
     * @param reqWidth  :显示宽度
     * @param reqHeight :显示高度
     * @return 图片sample
     */
    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while (((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth)
                    || ((halfHeight / inSampleSize) > 2048) || ((halfWidth / inSampleSize) > 2048)) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static final Bitmap scaleBitmap(Bitmap bmp, int reqWidth, int reqHeight) {
        Bitmap temp = bmp;
        try {
            System.gc();
            Runtime.getRuntime().gc();
            if (null != temp) {
                if ((temp.getWidth() > INSERT_CROP_IMAGE_WIDTH) || (temp.getHeight() > INSERT_CROP_IMAGE_HEIGHT)) {
                    float scal = 1.0F;
                    float scalW = (float) INSERT_CROP_IMAGE_WIDTH / (float) temp.getWidth();
                    float scalH = (float) INSERT_CROP_IMAGE_HEIGHT / (float) temp.getHeight();
                    if (scalW < scalH) {
                        scal = scalW;
                    } else {
                        scal = scalH;
                    }
                    Matrix matrix = new Matrix();
                    matrix.setScale(scal, scal);
                    temp = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), matrix, true);
                    if (null == temp) {
                        temp = bmp;
                    }
                    System.gc();
                    Runtime.getRuntime().gc();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 获取文件的Bitmap
     *
     * @param fileName  :文件名
     * @param reqWidth  :显示宽度
     * @param reqHeight :显示高度
     * @param degrees   :旋转角度
     * @return Bitmap
     */
    public static Bitmap getBitmap(String fileName, int reqWidth, int reqHeight, int degrees, boolean scale) {
        Bitmap bmp = null;
        try {
            Options options = new Options();
            if ((reqWidth > 0) || (reqHeight > 0)) {
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(fileName, options);
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            }
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeFile(fileName, options);
            if (null != bmp) {
                if (scale) {
                    bmp = scaleBitmap(bmp, reqWidth, reqHeight);
                }
                int rotate = 0;
                ExifInterface exifInterface = new ExifInterface(fileName);
                int result = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                switch (result) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                    default:
                        break;
                }
                rotate += degrees;
                if (0 != rotate) {
                    Matrix matrix = new Matrix();
                    matrix.setRotate(rotate);
                    Bitmap rotateBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                    if (null != rotateBitmap) {
                        bmp = rotateBitmap;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public static String compressImage(Context context, String path, int reqWidth, int reqHeight, int degrees) {
        Bitmap temp = getBitmap(path, reqWidth, reqHeight, degrees, true);
        return compressImage(context, temp, reqWidth, reqHeight);
    }

    public static String compressImage(Context context, Bitmap bmp, int reqWidth, int reqHeight) {
        String result = null;
        Bitmap temp = bmp;
        ByteArrayOutputStream baos = null;
        try {
            if (null != temp) {
                temp = scaleBitmap(temp, reqWidth, reqHeight);
                // LogUtil.i(TAG, "compressImage temp[" + temp + "] bmp[" + bmp
                // + "]");
                baos = new ByteArrayOutputStream();
                temp.compress(CompressFormat.JPEG, 100, baos);
                int options = 100;
                int length = baos.toByteArray().length;
                while (length > MAX_INSERT_FILE_SIZE) {
                    baos.reset();
                    if (length > MAX_INSERT_FILE_SIZE * 5) {
                        options -= 50;
                    } else if (length > MAX_INSERT_FILE_SIZE * 3) {
                        options -= 30;
                    } else if (length > MAX_INSERT_FILE_SIZE * 2) {
                        options -= 10;
                    } else {
                        options -= 5;
                    }
                    temp.compress(CompressFormat.JPEG, options, baos);
                    length = baos.toByteArray().length;
                }
                result = saveImageFile(context, baos.toByteArray(), CompressFormat.JPEG);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            result = null;
        }

        return result;
    }

    /**
     * 存储Bitmap
     *
     * @param context
     * @param bmp
     * @param format
     * @return
     */
    public static String saveImageFile(Context context, Bitmap bmp, CompressFormat format) {
        Bitmap temp = bmp;
        ByteArrayOutputStream baos = null;
        try {
            if (null != temp) {
                baos = new ByteArrayOutputStream();
                temp.compress(CompressFormat.JPEG, 100, baos);
                int options = 100;
                int length = baos.toByteArray().length;
                while (length > MAX_INSERT_FILE_SIZE) {
                    baos.reset();
                    if (length > MAX_INSERT_FILE_SIZE * 5) {
                        options -= 50;
                    } else if (length > MAX_INSERT_FILE_SIZE * 3) {
                        options -= 30;
                    } else if (length > MAX_INSERT_FILE_SIZE * 2) {
                        options -= 10;
                    } else {
                        options -= 5;
                    }
                    temp.compress(CompressFormat.JPEG, options, baos);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return saveImageFile(context, baos.toByteArray(), format);
    }

    public static String saveImageFile(Context context, byte[] data, CompressFormat format) {
        String path = "";
        try {
            String suffix = "";
            switch (format) {
                case JPEG:
                    suffix = ".jpg";
                    break;
                case PNG:
                    suffix = ".png";
                    break;
                case WEBP:
                    suffix = ".webp";
                    break;
            }

            String folder = getCacheDirectory(context);
            String fileName = FileUtil.getTempFileName() + suffix;
            path = folder + fileName;
            File file = new File(path);
            if (file.exists()) {
                if (file.isFile()) {
                    if (false == file.delete()) {
                        LogUtils.e("imageutils", "saveImageFile delete fail");
                        return path;
                    }
                } else {
                    LogUtils.e("imageutils", "saveImageFile folder err");
                    return path;
                }
            }
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file);
            outStream.write(data);
            outStream.close();
        } catch (Throwable e) {
            e.printStackTrace();
            path = "";
        }
        return path;
    }

    /**
     * 获取缓存路径
     *
     * @param context ：上下文
     * @return：返回缓存路径，可能为null
     */
    public static String getCacheDirectory(Context context) {
        return getDiskCacheDir(context);
    }

    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        //Environment.getExtemalStorageState() 获取SDcard的状态
        //Environment.MEDIA_MOUNTED 手机装有SDCard,并且可以进行读写

        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return cachePath;
    }

    public static byte[] bitmapToByteArray(final Bitmap bmp, int quality, final boolean needRecycle) {
        byte[] result = null;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            bmp.compress(CompressFormat.JPEG, quality, output);

            result = output.toByteArray();
            output.close();

            if (needRecycle) {
                bmp.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取缩略图()
     *
     * @param bmp
     * @param needRecycle
     * @return
     * @author wangshuaibo
     */
    public static byte[] bitmapToThumb(final Bitmap bmp, int thumbWidth, int thumbHeight, final boolean needRecycle) {
        byte[] result = null;
        try {
            if (null != bmp) {
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, thumbWidth, thumbHeight, true);

                if (null != thumbBmp) {
                    result = bitmapToByteArray(thumbBmp, 50, false);
                }
                if (needRecycle) {
                    bmp.recycle();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 旋转一张图片
     *
     * @param srcBitmap
     * @param degrees
     * @return
     */
    public final static Bitmap rotationBitmap(Bitmap srcBitmap, float degrees) {
        Bitmap result = null;
        if (degrees != 0 && srcBitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) srcBitmap.getWidth() / 2, (float) srcBitmap.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), m, true);
                if (srcBitmap != b2) {
                    srcBitmap.recycle(); // Android开发网再次提示Bitmap操作完应该显示的释放
                    srcBitmap = b2;
                }
                result = b2;
            } catch (OutOfMemoryError ex) {
                // Android123建议大家如何出现了内存不足异常，最好return 原始的bitmap对象。.
                ex.printStackTrace();
            }
        }
        return result;
    }

    public final static Bitmap rotationBitmap2(Bitmap srcBitmap, float degrees) {
        Bitmap result = null;
        if (degrees != 0 && srcBitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) srcBitmap.getWidth() / 2, (float) srcBitmap.getHeight() / 2);
            try {
                result = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), m, true);
            } catch (OutOfMemoryError ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static int computeSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength),
                Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static Bitmap readFromDrawable(Context context, int id) {
        Resources res = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, id);
        return bitmap;
    }

    public static Drawable getDrawable(Context context, int source) {
        // 根据id从资源文件中获取图片对象
        Drawable d = context.getResources().getDrawable(source);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        return d;
    }

    /**
     * 保存图片<br>
     * 图片保存到默认路径，名字用时间戳。
     *
     * @param saveBitmap
     */

    @SuppressLint("NewApi")
    public static String saveBitmapToAlbum(Context context, Bitmap saveBitmap, boolean showToast) {
        String root = "";
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            root = context.getFilesDir().getAbsolutePath();
        } else {
            // 获取系统相册路径，若sdk版本低于2.2
            root = Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        String path = root + File.separator + Environment.DIRECTORY_DCIM + File.separator + "starshow";// 保存路径
        String fileName = path + File.separator + System.currentTimeMillis() + ".jpg";

        try {
            File directory = new File(path);
            // 若保存路径不存在，先创建保存目录文件夹
            if (!directory.exists())
                directory.mkdirs();
            File file = new File(fileName);

            FileOutputStream fos = new FileOutputStream(file);
            saveBitmap.compress(CompressFormat.JPEG, 100, fos);

            fos.flush();
            fos.close();
            // 发送扫描广播
            Uri data = Uri.parse("file://" + fileName);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
            if (showToast) {
                ToastUtils.show_middle(context, context.getString(R.string.str_yi_bao_cun_zhi_lu_jing) + fileName, ToastUtils.LENGTH_SHORT);
            }
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @SuppressLint("NewApi")
    public static String savePic(String pFromClass, Context context, File file, boolean showToast, String url) {
        String root = "";
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            root = context.getFilesDir().getAbsolutePath();
        } else {
            // 获取系统相册路径，若sdk版本低于2.2
            root = Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        String path = root + File.separator + Environment.DIRECTORY_DCIM + File.separator + "CollectionAuction";// 保存路径
        String fileName = path + File.separator + System.currentTimeMillis() + ".jpg";

        try {
            int bytesum = 0;
            int byteread = 0;
            File directory = new File(path);
            // 若保存路径不存在，先创建保存目录文件夹
            if (!directory.exists())
                directory.mkdirs();
            File imgfile = new File(fileName); //Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+"yida"+url.substring(url.lastIndexOf("/"));我用的保存文件的地址
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = new FileOutputStream(imgfile);
            byte[] buffer = new byte[1000];
            int length;
            while ((byteread = inputStream.read(buffer)) != -1) {
                bytesum += byteread; //字节数 文件大小
                outputStream.write(buffer, 0, byteread);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            // 发送扫描广播
            Uri data = Uri.parse("file://" + fileName);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
            if (showToast) {
                LogUtils.e("执行toast", "执行toast");
                EventBus.getDefault().post(new SaveImageEvent(pFromClass));
            }
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * Get bitmap from image file, Try to rotate Bitmap according Exif info;
     *
     * @param filename file path
     * @return original bitmap or rotated bitmap
     */
    public static Bitmap geAutoRotatedBitmap(String filename, int height, int weight) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, opts);
        LogUtils.e("imageutils", "height|weight:" + height + "|" + weight);
        opts.inSampleSize = ImageUtil.computeSampleSize(opts, -1, height * weight);
        opts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filename, opts);
        int orientation = ExifInterface.ORIENTATION_NORMAL;
        try {
            ExifInterface exifInterface = new ExifInterface(filename);
            orientation = exifInterface
                    .getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            LogUtils.e("imageutils", "orientation:" + orientation);
            int degree = 0;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
            if (degree > 0) {
                bitmap = rotationBitmap(bitmap, degree);
                LogUtils.e("imageutils", " try to rotate, orientation:" + orientation);
            }
        } catch (IOException e) {
            orientation = ExifInterface.ORIENTATION_NORMAL;
        }
        return bitmap;
    }

    public static Bitmap fastblur(Bitmap sentBitmap, int radius) {

        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filter works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }


    /**
     * 柔化效果(高斯模糊)
     *
     * @param bmp
     * @return
     */
    private Bitmap blurImageAmeliorate(Bitmap bmp) {
        long start = System.currentTimeMillis();
        // 高斯矩阵
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};

        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int delta = 16; // 值越小图片会越亮，越大则越暗

        int idx = 0;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * gauss[idx]);
                        newG = newG + (int) (pixG * gauss[idx]);
                        newB = newB + (int) (pixB * gauss[idx]);
                        idx++;
                    }
                }

                newR /= delta;
                newG /= delta;
                newB /= delta;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        long end = System.currentTimeMillis();
        LogUtils.e("may", "used time=" + (end - start));
        return bitmap;
    }

    public static enum LoadingFailType {
        /**
         * 读写失败
         */
        IO_ERROR,
        /**
         * 解码失败
         */
        DECODING_ERROR,
        /**
         * 网络下载失败
         */
        NETWORK_DENIED,
        /**
         * 没有足够的空间存储
         */
        OUT_OF_MEMORY,
        /**
         * 未知错误
         */
        UNKNOWN
    }

    public interface ImageLoadingListener {
        /**
         * 图片开始加载
         *
         * @param imageUri ：图片uri
         * @param view     ：显示图片的视图
         */
        void onLoadingStarted(String imageUri, View view);

        /**
         * 图片加载失败
         *
         * @param imageUri ：图片uri
         * @param view     ：显示图片的视图
         * @param failType ：图片加载失败类型
         */
        void onLoadingFailed(String imageUri, View view, LoadingFailType failType);

        /**
         * 图片加载完成
         *
         * @param imageUri    ：图片uri
         * @param view        ：显示图片的视图
         * @param loadedImage ：加载后Bitmap
         */
        void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);

        /**
         * 图片加载取消
         *
         * @param imageUri ：图片uri
         * @param view     ：显示图片的视图
         */
        void onLoadingCancelled(String imageUri, View view);

        /**
         * 图片加载中更新
         *
         * @param imageUri ：图片uri
         * @param view     ：显示图片的视图
         * @param current  ：当前大小
         * @param total    ：总大小
         */
        void onProgressUpdate(String imageUri, View view, int current, int total);
    }


    /**
     * @param x              图像的宽度
     * @param y              图像的高度
     * @param image          源图片
     * @param outerRadiusRat 圆角的大小
     * @return 圆角图片
     */
    public static Bitmap createFramedPhoto(int x, int y, Bitmap image, float outerRadiusRat) {
        //根据源文件新建一个darwable对象
        Drawable imageDrawable = new BitmapDrawable(image);

        // 新建一个新的输出图片
        Bitmap output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        // 新建一个矩形
        RectF outerRect = new RectF(0, 0, x, y);

        // 产生一个红色的圆角矩形
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

        // 将源图片绘制到这个圆角矩形上
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        imageDrawable.setBounds(0, 0, x, y);
        canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
        imageDrawable.draw(canvas);
        canvas.restore();

        return output;
    }

    //将图片转成bitmap

    public Bitmap decodeResource(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        Options opts = new Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }
}
