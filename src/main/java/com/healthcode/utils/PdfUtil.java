package com.healthcode.utils;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.healthcode.vo.StudentDailyCardStatistic;
import com.healthcode.vo.StudentDailyCardVo;
import com.healthcode.vo.TeacherDailyCardStatistic;
import com.healthcode.vo.TeacherDailyCardVo;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;

public class PdfUtil {
    private static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    private static final String FILE_URL = "src/";


    public static byte[] GetPdf(String fileName, StudentDailyCardStatistic studentDailyCardStatistic) {
        Document document = new Document();
        try {
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font topfont = new Font(bfChinese, 14, Font.BOLD);
            Font textfont = new Font(bfChinese, 10);
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            Paragraph paragraph_title = new Paragraph("学生打卡统计", topfont);
            paragraph_title.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph pieParagraph = new Paragraph("图表", textfont);
            pieParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(paragraph_title);
            document.add(pieParagraph);
            /* *
             * 生成统计图
             * */
            //获取数据
            Pair<String, Integer> redValue = new Pair<>("Red", studentDailyCardStatistic.getRedCodeStudentCount());
            Pair<String, Integer> greenValue = new Pair<>("Green", studentDailyCardStatistic.getGreenCodeStudentCount());
            Pair<String, Integer> yellowValue = new Pair<>("Yellow", studentDailyCardStatistic.getYellowCodeStudentCount());

            PieDataset<String> dataset = pieHelper(redValue, greenValue, yellowValue);

            JFreeChart chart = ChartFactory.createPieChart(
                    "学生打卡统计", // chart title
                    dataset,// data
                    true,// include legend
                    true,
                    false
            );
            // 设置背景色为白色
            chart.setBackgroundPaint(Color.white);
            // 指定图片的透明度(0.0-1.0)
            // 设置图标题的字体
            java.awt.Font font = new java.awt.Font("黑体", java.awt.Font.BOLD, 20);
            TextTitle title = new TextTitle("统计");
            title.setFont(font);
            chart.setTitle(title);
            FileOutputStream fos_jpg;
            new File(FILE_URL).mkdirs();
            fos_jpg = new FileOutputStream(FILE_URL + "out.jpg");
            ChartUtils.writeChartAsJPEG(fos_jpg, 0.7f, chart, 800, 1000, null);
            fos_jpg.close();
            Image pieImage = Image.getInstance(FILE_URL + "out.jpg");
            pieImage.setAlignment(Image.ALIGN_CENTER);
            pieImage.scaleAbsolute(328, 370);
            document.add(pieImage);
            document.newPage();
            pieParagraph = new Paragraph("详情");
            pieParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pieParagraph);

            //插入表格
            List<StudentDailyCardVo> studentDailyCardVos = studentDailyCardStatistic.getDailyCardList();
            PdfPTable table = new PdfPTable(5);
            table.addCell(new PdfPCell(new Paragraph("学号", textfont)));
            table.addCell(new PdfPCell(new Paragraph("姓名", textfont)));
            table.addCell(new PdfPCell(new Paragraph("所在班级", textfont)));
            table.addCell(new PdfPCell(new Paragraph("今日打卡情况", textfont)));
            table.addCell(new PdfPCell(new Paragraph("健康码类型", textfont)));
            for (StudentDailyCardVo cur : studentDailyCardVos) {
                table.addCell(new PdfPCell(new Paragraph(cur.getStudentId(), textfont)));
                table.addCell(new PdfPCell(new Paragraph(cur.getName(), textfont)));
                table.addCell(new PdfPCell(new Paragraph(cur.getClassName(), textfont)));
                table.addCell(new PdfPCell(new Paragraph(cur.getHadSubmitDailyCard() ? "已打卡" : "未打卡", textfont)));
                table.addCell(new PdfPCell(new Paragraph(cur.getType().getType(), textfont)));
            }
            document.add(table);

            System.out.println("end");
            document.close();

            return FileUtils.readFileToByteArray(new File(fileName));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] GetPdf(String fileName, TeacherDailyCardStatistic teacherDailyCardStatistic) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font topfont = new Font(bfChinese, 14, Font.BOLD);
            Font textfont = new Font(bfChinese, 10);
            document.open();
            Paragraph paragraph_title = new Paragraph("教师每日一报统计", topfont);
            paragraph_title.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph pieParagraph = new Paragraph("图表", textfont);
            pieParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(paragraph_title);
            document.add(pieParagraph);
            /* *
             * 生成统计图
             * */
            //获取数据
            Pair<String, Integer> redValue = new Pair<>("Red", teacherDailyCardStatistic.getRedCodeTeacherCount());
            Pair<String, Integer> greenValue = new Pair<>("Green", teacherDailyCardStatistic.getGreenCodeTeacherCount());
            Pair<String, Integer> yellowValue = new Pair<>("Yellow", teacherDailyCardStatistic.getYellowCodeTeacherCount());

            PieDataset<String> dataset = pieHelper(redValue, greenValue, yellowValue);

            JFreeChart chart = ChartFactory.createPieChart(
                    "教师统计", // chart title
                    dataset,// data
                    true,// include legend
                    true,
                    false
            );
            // 设置背景色为白色
            chart.setBackgroundPaint(Color.white);
            // 指定图片的透明度(0.0-1.0)
            // 设置图标题的字体
            java.awt.Font font = new java.awt.Font("黑体", Font.BOLD, 20);
            TextTitle title = new TextTitle("统计");
            title.setFont(font);
            chart.setTitle(title);
            FileOutputStream fos_jpg;
            new File(FILE_URL).mkdirs();
            fos_jpg = new FileOutputStream(FILE_URL + "out.jpg");
            ChartUtils.writeChartAsJPEG(fos_jpg, 0.7f, chart, 800, 1000, null);
            fos_jpg.close();
            Image pieImage = Image.getInstance(FILE_URL + "out.jpg");
            pieImage.setAlignment(Image.ALIGN_CENTER);
            pieImage.scaleAbsolute(328, 370);
            document.add(pieImage);
            document.newPage();
            pieParagraph = new Paragraph("详情", textfont);
            pieParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pieParagraph);

            //插入表格
            List<TeacherDailyCardVo> teacherDailyCardVos = teacherDailyCardStatistic.getDailyCardList();
            PdfPTable table = new PdfPTable(5);
            table.addCell(new PdfPCell(new Paragraph("工号", textfont)));
            table.addCell(new PdfPCell(new Paragraph("姓名", textfont)));
            table.addCell(new PdfPCell(new Paragraph("班级", textfont)));
            table.addCell(new PdfPCell(new Paragraph("今日打卡情况", textfont)));
            table.addCell(new PdfPCell(new Paragraph("健康码", textfont)));
            for (TeacherDailyCardVo cur : teacherDailyCardVos) {
                table.addCell(new PdfPCell(new Paragraph(cur.getTeacherId(), textfont)));
                table.addCell(new PdfPCell(new Paragraph(cur.getName(), textfont)));
                table.addCell(new PdfPCell(new Paragraph(cur.getCollegeName(), textfont)));
                table.addCell(new PdfPCell(new Paragraph(cur.getHadSubmitDailyCard() ? "已打卡" : "未打卡", textfont)));
                table.addCell(new PdfPCell(new Paragraph(cur.getType().getType(), textfont)));
            }
            document.add(table);

            System.out.println("end");
            document.close();

            return FileUtils.readFileToByteArray(new File(fileName));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SafeVarargs
    private static PieDataset<String> pieHelper(Pair<String, Integer>... values) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        for (Pair<String, Integer> value : values) {
            dataset.setValue(value.getKey(), value.getValue());
        }
        return dataset;
    }
}