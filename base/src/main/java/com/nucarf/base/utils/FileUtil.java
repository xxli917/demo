package com.nucarf.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 文件操作
 *
 * @author wlj
 */
public class FileUtil {
    private static FileUtil instance;

    public static synchronized FileUtil getInstance() {
        if (null == instance) {
            instance = new FileUtil();
        }
        return instance;
    }

    /**
     * 获取文件大小
     *
     * @param filePath
     * @return
     */
    public static long getFileSize(String filePath) {
        long size = 0;

        File file = new File(filePath);
        if (file != null && file.exists()) {
            size = file.length();
        }
        return size;
    }


    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    /**
     * 获取SD卡的根目录，末尾带\
     *
     * @return
     */
    public static String getSDRoot() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 判断手机是否存在sd卡,并且有读写权限
     *
     * @return
     */
    public static boolean isExistSdcard(Context context) {
        boolean flag = false;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            flag = true;
        } else {

        }
        return flag;
    }


    public static boolean deleteFile(String fileName) {
        boolean result = true;
        try {
            if (null != fileName) {
                File file = new File(fileName);
                deleteFile(file);
            }
        } catch (Throwable e) {
            result = false;
        }
        return result;
    }

    public static boolean deleteFile(File file) {
        boolean result = true;
        try {
            if (file.exists()) {
                if (file.isFile()) {
                    final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
                    file.renameTo(to);
                    if (false == to.delete()) {
                        result = false;
                        return result;
                    }
                } else if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFile(files[i]);
                    }
                }
                final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
                file.renameTo(to);
                to.delete();
            }
        } catch (Throwable e) {
            LogUtils.e("fileutis", "deleteFile e[" + e + "]");
            result = false;
        }

        return result;
    }


    /**
     * 使用当前时间戳拼接一个唯一的文件名
     *
     * @param
     * @return
     */
    public static String getTempFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_SS", Locale.getDefault());
        String fileName = format.format(new Timestamp(System.currentTimeMillis()));
        return fileName;
    }


    /**
     * 创建一个文件，创建成功返回true
     *
     * @param filePath
     * @return
     */
    public static boolean createFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }

                return file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 写文件
     *
     * @param file
     * @param content
     * @return
     */
    public static boolean writeFileFileWriter(File file, String content) throws IOException {

        if (!file.exists()) {
            try {
                if (!file.createNewFile())
                    return false;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        // do something
        // writing file

        // -----------
        java.io.BufferedWriter writer = null;
        try {
            FileOutputStream writerStream = new FileOutputStream(file);
            writer = new java.io.BufferedWriter(new java.io.OutputStreamWriter(writerStream, "UTF-8"));
            // fw.write(content);
            writer.write(content);
        } catch (IOException e) {
            throw new IOException("数据同步--写缓存文件失败，文件为" + file.getAbsolutePath());
        } finally {
            if (writer != null)
                try {
                    // fw.close();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return true;
    }

    /**
     * 读文件内容
     *
     * @param file
     * @return
     */
    public static String readFileFileReader(File file) {
        FileReader fr = null;

        StringBuilder jsonString = new StringBuilder("");

        char[] content = null;
        if (!file.exists())
            return null;
        else {
            try {
                // --------------------------------

                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String inLine = null;
                while ((inLine = bufferedReader.readLine()) != null) {
                    jsonString.append(inLine);
                }
                bufferedReader.close();

                // --------------------------------

                // fr = new FileReader(file);
                // content = new char[(int) file.length()];
                // fr.read(content);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fr != null)
                    try {
                        fr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            // return String.valueOf(content);
            return jsonString.toString();
        }
    }

    public enum PathStatus {
        SUCCESS, EXITS, ERROR
    }

    public static void writeImage(Bitmap bitmap, String destPath, int quality) {
        try {
            FileUtil.deleteFile(destPath);
            if (FileUtil.createFile(destPath)) {
                FileOutputStream out = new FileOutputStream(destPath);
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                    out.flush();
                    out.close();
                    out = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeImagePNG(final Bitmap bitmap, final String destPath, final int quality) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileUtil.deleteFile(destPath);
                    if (FileUtil.createFile(destPath)) {
                        FileOutputStream out = new FileOutputStream(destPath);
                        if (bitmap.compress(Bitmap.CompressFormat.PNG, quality, out)) {
                            out.flush();
                            out.close();
                            out = null;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 文件重命名
     * @param filePath 文件路径
     * @param fileName 文件新名字
     */
    public static boolean renameFile(String filePath, String fileName) {
        File file = new File(filePath);
        if(file.isFile()){
           return file.renameTo(new File(file.getParent() + File.separator + fileName));
        }else {
            return false;
        }
//        return file.isFile() && file.renameTo(new File(file.getPath() + File.separator + fileName));

    }

    public static void moveFile(String oldFilePath, String newFolderPath) {
        File oldFile = new File(oldFilePath);
        //文件新（目标）地址
        String newPath = newFolderPath;
        //new一个新文件夹
        File fnewpath = new File(newPath);
        //判断文件夹是否存在
        if (!fnewpath.exists())
            fnewpath.mkdirs();
        //将文件移到新文件里
        File fnew = new File(newPath + oldFile.getName());
        oldFile.renameTo(fnew);
    }

    public Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}