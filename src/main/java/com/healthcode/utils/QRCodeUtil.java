package com.healthcode.utils;

import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.healthcode.domain.HealthCodeType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * @author zhenghong
 */
public class QRCodeUtil {
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;

    // LOGO宽度
    private static final int WIDTH = 100;

    // LOGO高度
    private static final int HEIGHT = 100;


    /**
     * 将前端传入的信息，编码成二维码
     */
    public static BufferedImage encode(HealthCodeType healthCodeType, String content, String imgPath, boolean needCompress) throws Exception {
        return QRCodeUtil.createImage(healthCodeType, content, imgPath, needCompress);
    }

    /**
     * 生成二维码核心代码
     */
    private static BufferedImage createImage(HealthCodeType healthCodeType, String content, String imgPath, boolean needCompress) throws Exception {
        int rgbPint = 0xFF000000;
        switch (healthCodeType){
            case RED:
                rgbPint = 0xFFFF0000;
                break;
            case GREEN:
                rgbPint = 0xFF008000;
                break;
            case YELLOW:
                rgbPint = 0xFFFFFF00;
        }
        HashMap<EncodeHintType, Object> hints = new HashMap<>(16);
        // 指定要使用的纠错程度，如果加水印则使用H高纠错，否则L低纠错
        if(imgPath == null || "".equals(imgPath)){
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        }else {
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        }
        // 指定字符编码
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        // 指定生成条形码时要使用的边距（以像素为单位）。
        hints.put(EncodeHintType.MARGIN, 1);

        // 生成一个二维位矩阵
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // true则有颜色，false则是白色
                image.setRGB(x, y, bitMatrix.get(x, y) ? rgbPint : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入LOGO图片
        QRCodeUtil.insertImage(image, imgPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO图片
     */
    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println(imgPath + ":该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);

        // 压缩LOGO
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }

            // 创建此图像的缩放版本。
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = tag.getGraphics();

            // 绘制缩小后的图
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 解码，将二维码里的信息解码出来
     */
    @SuppressWarnings({"unused"})
    public static String decode(String path) throws Exception {
        File file = new File(path);
        BufferedImage image;
        image = ImageIO.read(file);
        return decodeHelper(image).getText();
    }
    @SuppressWarnings({"unused"})
    public static String decode(BufferedImage image) throws Exception {
        return decodeHelper(image).getText();
    }

    private static Result decodeHelper(BufferedImage image) throws NotFoundException {
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Hashtable<DecodeHintType, Charset> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);

        return new MultiFormatReader().decode(bitmap, hints);
    }
}
