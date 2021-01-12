package com.healthcode.utils;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.util.StringUtils;

import java.io.*;
import java.util.*;

/**
 * @author zhenghong
 */
public class ExcelUtil {

    private static final Sheet initSheet;

    static {
        initSheet = new Sheet(1, 0);
        initSheet.setSheetName("sheet");
        //设置自适应宽度
        initSheet.setAutoWidth(Boolean.TRUE);
    }

    /**
     * 读取少于1000行数据
     * @param filePath 文件绝对路径
     */
    @SuppressWarnings("unused")
    public static List<Object> readLessThan1000Row(String filePath){
        return readLessThan1000RowBySheet(filePath,null);
    }

    /**
     * 读小于1000行数据, 带样式
     * filePath 文件绝对路径
     * initSheet ：
     *      sheetNo: sheet页码，默认为1
     *      headLineMun: 从第几行开始读取数据，默认为0, 表示从第一行开始读取
     *      clazz: 返回数据List<Object> 中Object的类名
     */
    public static List<Object> readLessThan1000RowBySheet(String filePath, Sheet sheet){
        if(!StringUtils.hasText(filePath)){
            return null;
        }

        sheet = sheet != null ? sheet : initSheet;

        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            return EasyExcelFactory.read(fileStream, sheet);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fileStream != null){
                    fileStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<Object> readLessThan1000RowBySheet(InputStream fileStream, Sheet sheet){
        if(fileStream == null){
            return null;
        }
        sheet = sheet != null ? sheet : initSheet;

        return EasyExcelFactory.read(fileStream, sheet);
    }

    /**
     * 读大于1000行数据
     * @param filePath 文件绝对路径
     */
    @SuppressWarnings("unused")
    public static List<Object> readMoreThan1000Row(String filePath){
        return readMoreThan1000RowBySheet(filePath,null);
    }

    /**
     * 读大于1000行数据, 带样式
     * @param filePath 文件绝对路径
     */
    public static List<Object> readMoreThan1000RowBySheet(String filePath, Sheet sheet){
        if(!StringUtils.hasText(filePath)){
            return null;
        }

        sheet = sheet != null ? sheet : initSheet;

        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(filePath);
            ExcelListener excelListener = new ExcelListener();
            EasyExcelFactory.readBySax(fileStream, sheet, excelListener);
            return excelListener.getData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fileStream != null){
                    fileStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 生成excel
     * @param filePath 绝对路径
     * @param data 数据源
     * @param head 表头
     */
    @SuppressWarnings("unused")
    public static void writeBySimple(String filePath, List<List<Object>> data, List<String> head){
        writeSimpleBySheet(filePath,data,head,null);
    }

    /**
     * 生成excel
     * @param filePath 绝对路径
     * @param data 数据源
     * @param sheet excel页面样式
     * @param head 表头
     */
    public static void writeSimpleBySheet(String filePath, List<List<Object>> data, List<String> head, Sheet sheet){
        sheet = (sheet != null) ? sheet : initSheet;

        if(head != null){
            List<List<String>> list = new ArrayList<>();
            head.forEach(h -> list.add(Collections.singletonList(h)));
            sheet.setHead(list);
        }

        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            writer.write1(data,sheet);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.finish();
                }

                if(outputStream != null){
                    outputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 生成excel
     * @param filePath 绝对路径
     * @param data 数据源
     */
    @SuppressWarnings("unused")
    public static void writeWithTemplate(String filePath, List<? extends BaseRowModel> data){
        writeWithTemplateAndSheet(filePath,data,null);
    }

    /**
     * 生成excel
     * @param filePath 绝对路径
     * @param data 数据源
     * @param sheet excel页面样式
     */
    public static void writeWithTemplateAndSheet(String filePath, List<? extends BaseRowModel> data, Sheet sheet){
        if(Objects.isNull(data)){
            return;
        }

        sheet = (sheet != null) ? sheet : initSheet;
        sheet.setClazz(data.get(0).getClass());

        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            writer.write(data,sheet);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.finish();
                }

                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 生成多Sheet的excel
     * @param filePath 绝对路径
     * @param multipleSheetProperty 多sheet的内容
     */
    @SuppressWarnings("unused")
    public static void writeWithMultipleSheets(String filePath,List<MultipleSheetProperty> multipleSheetProperty){
        if(Objects.isNull(multipleSheetProperty)){
            return;
        }

        OutputStream outputStream = null;
        ExcelWriter writer = null;
        try {
            outputStream = new FileOutputStream(filePath);
            writer = EasyExcelFactory.getWriter(outputStream);
            for (MultipleSheetProperty multipleSheelPropety : multipleSheetProperty) {
                Sheet sheet = multipleSheelPropety.getSheet() != null ? multipleSheelPropety.getSheet() : initSheet;
                if(!Objects.isNull(multipleSheelPropety.getData())){
                    sheet.setClazz(multipleSheelPropety.getData().get(0).getClass());
                }
                writer.write(multipleSheelPropety.getData(), sheet);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.finish();
                }

                if(outputStream != null){
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    //*********************匿名内部类开始，可以提取出去******************************/

    public static class MultipleSheetProperty{

        private List<? extends BaseRowModel> data;

        private Sheet sheet;

        public List<? extends  BaseRowModel> getData(){
            return data;
        }

        public Sheet getSheet(){
            return sheet;
        }

        public void setData(List<? extends BaseRowModel> data){
            this.data = data;
        }

        public void setSheet(Sheet sheet){
            this.sheet = sheet;
        }
    }

    /**
     * 解析监听器，
     * 每解析一行会回调invoke()方法。
     * 整个excel解析结束会执行doAfterAllAnalysed()方法
     */
    public static class ExcelListener extends AnalysisEventListener<Object> {

        private List<Object> data = new ArrayList<>();

        /**
         * 逐行解析
         * object : 当前行的数据
         */
        @Override
        public void invoke(Object object, AnalysisContext context) {
            //当前行
            // context.getCurrentRowNum()
            if (object != null) {
                data.add(object);
            }
        }


        /**
         * 解析完所有数据后会调用该方法
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            //解析结束销毁不用的资源
        }

        public List<Object> getData(){
            return data;
        }

        public void setData(List<Object> data){
            this.data = data;
        }
    }

    //************************匿名内部类结束，可以提取出去***************************/

//    public static void main(String[] args){
//        List<Object> objects = ExcelUtil.readLessThan1000RowBySheet("C:/Users/郑宏/Documents/学习/大三上/Internet编程技术/课程设计/test.xlsx", null);
//        System.out.println("Start to test");
//        assert objects != null;
//        objects.forEach(System.out::println);
//    }
}