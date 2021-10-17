package com.k.week04.utils;

/**
 * 平方计算工具类
 */
public class SquareUtils {
    private SquareUtils(){};

    public static SquareUtils getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static SquareUtils instance = new SquareUtils();
    }

    public int getSquare(int n) {
        return n * n;
    }
}
