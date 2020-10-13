package me.fanchaoo.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 随机产生类似Github的马赛克风格头像
 *
 * @author LLH
 */
public class ImageGenerateUtils {

    private static final List<String> imageNameList = Arrays.asList(
            "15921064553848e4aba0127964935be9cfdb647670667.jpeg");

    /**
     * 背景颜色
     */
    private final static Color BACK_GROUND_COLOR = new Color(238, 238, 238);
    /**
     * 图片宽
     */
    private final static int IMG_WIDTH = 360;
    /**
     * 图片高
     */
    private final static int IMG_HEIGHT = 360;
    /**
     * 图片边缘内边距
     */
    private final static int PADDING = 30;
    /**
     * 填充比率，越接近1，有色色块出现几率越高
     */
    private final static double RADIO = 0.45;
    /**
     * 每边矩形数量（建议>=5）
     */
    private final static int BLOCK_NUM = 9;
    /**
     * 颜色差值评价值（越大颜色越鲜艳）
     */
    private final static int COLOR_DIFF_EVALUATION = 100;
    /**
     * 基色阶数极限
     */
    private final static int COLOR_LIMIT = 256;
    /**
     * 保存路径
     */
    private final static String DIR = "/Users/fanchao/";

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 20; i++) {
            generate();
        }
    }

    public static String getImageName() {
        Random random = new Random();
        int index = random.nextInt(imageNameList.size());
        return imageNameList.get(index);
    }

    private static void generate() throws IOException {
        //得到图片缓冲区
        BufferedImage bi = new BufferedImage
                (IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);

        //得到它的绘制环境(这张图片的笔)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();

        //设置背景颜色
        g2.setColor(BACK_GROUND_COLOR);
        g2.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);

        // 随机颜色
        Color mainColor = getRandomColor();

        // 随机生成有效块坐标集合
        List<Point> pointList = getRandomPointList(RADIO);

        // 填充图形
        fillGraph(g2, pointList, mainColor);

        // 输出
        ImageIO.write(bi, "JPG",
                new FileOutputStream(DIR + System.currentTimeMillis() + ".jpg"));//保存图片 JPEG表示保存格式
    }

    /**
     * 填充图形
     *
     * @param g2        画笔
     * @param pointList 填充块坐标
     * @param mainColor 填充颜色
     */
    private static void fillGraph(Graphics2D g2, List<Point> pointList, Color mainColor) {
        int rowBlockLength = (IMG_HEIGHT - 2 * PADDING) / BLOCK_NUM;
        int colBlockLength = (IMG_WIDTH - 2 * PADDING) / BLOCK_NUM;
        // 填充
        g2.setColor(mainColor);
        // 遍历points
        for (Point point : pointList) {
            g2.fillRect(PADDING + point.x * rowBlockLength,
                    PADDING + point.y * colBlockLength,
                    rowBlockLength, colBlockLength);
        }
    }

    /**
     * 获取随机颜色位置列表
     *
     * @param radio 填充色块几率
     * @return List<Point> 列表
     */
    private static List<Point> getRandomPointList(double radio) {
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < BLOCK_NUM / 2; i++) {
            for (int j = 0; j < BLOCK_NUM; j++) {
                if (Math.random() < radio) {
                    points.add(new Point(i, j));
                }
            }
        }
        addReversePoints(points);
        if (BLOCK_NUM % 2 == 1) {
            for (int i = 0; i < BLOCK_NUM; i++) {
                if (Math.random() < radio) {
                    points.add(new Point(BLOCK_NUM / 2, i));
                }
            }
        }
        return points;
    }

    /**
     * 获取随机颜色
     *
     * @return Color对象
     */
    private static Color getRandomColor() {
        int r, g, b;
        do {
            r = new Random().nextInt(COLOR_LIMIT);
            g = new Random().nextInt(COLOR_LIMIT);
            b = new Random().nextInt(COLOR_LIMIT);
        } while (evaluateColor(r, g, b));
        return new Color(r, g, b);
    }

    /**
     * 评价颜色品质，只需任意两种颜色差值大于某个规定值即可
     *
     * @return boolean
     */
    private static boolean evaluateColor(int r, int g, int b) {
        int rg = Math.abs(r - g);
        int rb = Math.abs(r - b);
        int gb = Math.abs(g - b);
        int max = rg > rb ? (rg > gb ? rg : gb) : (rb > gb ? rb : gb);
        return max < COLOR_DIFF_EVALUATION;
    }

    /**
     * 添加对称坐标
     *
     * @param points point的列表
     */
    private static void addReversePoints(List<Point> points) {
        ArrayList<Point> pointListCopy = new ArrayList<>(points);
        for (Point point : pointListCopy) {
            points.add(new Point((BLOCK_NUM - 1 - point.x), point.y));
        }
    }

    /**
     * 封装了坐标的内部类
     */
    private static class Point {
        private int x;
        private int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
