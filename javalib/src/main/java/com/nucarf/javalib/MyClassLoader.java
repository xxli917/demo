package com.nucarf.javalib;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * author : lixiaoxue
 * date   : 2020/12/15/2:20 PM
 */
public class MyClassLoader extends ClassLoader {
    private String libPath;
    public MyClassLoader(String path) {
        libPath = path;
    }
        @Override protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        File file = new File(libPath,fileName);
        try {
            FileInputStream is = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len = 0;
            try {
                while ((len = is.read()) != -1) {
                    bos.write(len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data = bos.toByteArray();
            is.close(); bos.close();
            return defineClass(name,data,0,data.length);
        } catch (IOException e) { e.printStackTrace();
        }
        return super.findClass(name);
    }

    //获取要加载 的class文件名
     private String getFileName(String name) {
        int index = name.lastIndexOf('.');
        if(index == -1){ return name+".class"; }
        else{
            return name.substring(index+1)+".class"; }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return super.loadClass(name, resolve);
    }
}
