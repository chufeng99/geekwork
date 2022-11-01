package com.k.week01;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 自定义ClassLoader
 */
public class SelfClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Class<?> aClass = new SelfClassLoader().findClass("Hello");
            for (Method declaredMethod : aClass.getDeclaredMethods()) {
                System.out.println("class methods：" + declaredMethod.getName());
            }
            Method method = aClass.getMethod("hello");
            method.invoke(aClass.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String rootPath = this.getClass().getClassLoader().getResource("").getPath();
        System.out.println(rootPath);

        URL url = SelfClassLoader.class.getResource("");
        System.out.println(url);

        String strPath  = SelfClassLoader.class.getResource("/week01/Hello.xlass").getPath();
        Path path = Paths.get(strPath);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}
