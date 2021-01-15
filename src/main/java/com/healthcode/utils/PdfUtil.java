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
import com.itextpdf.text.pdf.PdfPTable;
import javafx.util.Pair;
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

public class PdfUtil {
    private static final String FILE_URL = "src/";

    private static PieDataset<String> pieDataSet(){
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Green", 10);
        dataset.setValue("Yellow", 15);
        dataset.setValue("Red", 20);
        return dataset;
    }

    public static byte[] GetPdf(String fileName, StudentDailyCardStatistic studentDailyCardStatistic){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();
            Paragraph paragraph_title = new Paragraph("StudentDailyCardStatistics");
            paragraph_title.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph pieParagraph = new Paragraph("Chart");
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
                    "Statistics of students", // chart title
                    dataset,// data
                    true,// include legend
                    true,
                    false
            );
            // 设置背景色为白色
            chart.setBackgroundPaint(Color.white);
            // 指定图片的透明度(0.0-1.0)
            // 设置图标题的字体
            java.awt.Font font = new java.awt.Font("黑体", Font.BOLD,20);
            TextTitle title = new TextTitle("Statistics");
            title.setFont(font);
            chart.setTitle(title);
            FileOutputStream fos_jpg;
            try {
                fos_jpg=new FileOutputStream("out.jpg");
                ChartUtils.writeChartAsJPEG(fos_jpg,0.7f,chart,800,1000,null);
                fos_jpg.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Image pieImage = Image.getInstance(FILE_URL+"out.jpg");
            pieImage.setAlignment(Image.ALIGN_CENTER);
            pieImage.scaleAbsolute(328, 370);
            document.add(pieImage);
            document.newPage();
            pieParagraph = new Paragraph("Detail");
            pieParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pieParagraph);

            //插入表格
            List<StudentDailyCardVo> studentDailyCardVos = studentDailyCardStatistic.getDailyCardList();
            PdfPTable table = new PdfPTable(5 * studentDailyCardVos.size() + 5);
            table.addCell("id");
            table.addCell("name");
            table.addCell("class");
            table.addCell("today");
            table.addCell("healthCodeType");
            for(StudentDailyCardVo cur :studentDailyCardVos){
                table.addCell(cur.getStudentId());
                table.addCell(cur.getName());
                table.addCell(cur.getClassName());
                table.addCell(cur.getHadSubmitDailyCard() ? "True" : "False");
                table.addCell(String.valueOf(cur.getType()));
            }

            System.out.println("end");
            document.close();

            return FileUtils.readFileToByteArray(new File(FILE_URL + "out.jpg"));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] GetPdf(String fileName, TeacherDailyCardStatistic teacherDailyCardStatistic){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName + ".pdf"));
            document.open();
            Paragraph paragraph_title = new Paragraph("TeacherDailyCardStatistics");
            paragraph_title.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph pieParagraph = new Paragraph("Chart");
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
                    "Statistics of teachers", // chart title
                    dataset,// data
                    true,// include legend
                    true,
                    false
            );
            // 设置背景色为白色
            chart.setBackgroundPaint(Color.white);
            // 指定图片的透明度(0.0-1.0)
            // 设置图标题的字体
            java.awt.Font font = new java.awt.Font("黑体", Font.BOLD,20);
            TextTitle title = new TextTitle("Statistics");
            title.setFont(font);
            chart.setTitle(title);
            FileOutputStream fos_jpg;
            try {
                fos_jpg=new FileOutputStream("out.jpg");
                ChartUtils.writeChartAsJPEG(fos_jpg,0.7f,chart,800,1000,null);
                fos_jpg.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Image pieImage = Image.getInstance(FILE_URL+"out.jpg");
            pieImage.setAlignment(Image.ALIGN_CENTER);
            pieImage.scaleAbsolute(328, 370);
            document.add(pieImage);
            document.newPage();
            pieParagraph = new Paragraph("Detail");
            pieParagraph.setAlignment(Paragraph.ALIGN_LEFT);
            document.add(pieParagraph);

            //插入表格
            List<TeacherDailyCardVo> teacherDailyCardVos = teacherDailyCardStatistic.getDailyCardList();
            PdfPTable table = new PdfPTable(5 * teacherDailyCardVos.size() + 5);
            table.addCell("id");
            table.addCell("name");
            table.addCell("class");
            table.addCell("today");
            table.addCell("healthCodeType");
            for(TeacherDailyCardVo cur :teacherDailyCardVos){
                table.addCell(cur.getTeacherId());
                table.addCell(cur.getName());
                table.addCell(cur.getCollegeName());
                table.addCell(cur.getHadSubmitDailyCard() ? "True" : "False");
                table.addCell(String.valueOf(cur.getType()));
            }

            System.out.println("end");
            document.close();

            return FileUtils.readFileToByteArray(new File(FILE_URL + "out.jpg"));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SafeVarargs
    private static PieDataset<String> pieHelper(Pair<String, Integer>... values){
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        for(Pair<String, Integer> value : values){
            dataset.setValue(value.getKey(), value.getValue());
        }
        return dataset;
    }
}