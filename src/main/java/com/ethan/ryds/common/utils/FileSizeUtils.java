package com.ethan.ryds.common.utils;

/**
 * @Description 文件大小工具类
 * @Author Ethan
 * @Date 2020/7/2 16:36
 */
public class FileSizeUtils {

    private static String KB = "kb";

    private static String MB = "M";

    private static long oneKB = 1024L;

    private static long oneMB = 1024 * 1024L;

    private static double size = 0;

    // 转换文件大小为 kb / M
    public static String transformFileSize(long value) {
        // 如果大于 1M
        if (value > oneMB) {
            size = (double) value / oneMB;

            return String.format("%.2f", size) + MB;
        } else {
            size = (double) value / oneKB;

            return String.format("%.2f", size) + KB;
        }
    }
}
