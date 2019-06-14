package com.wsda.project.util;

import org.apache.poi.hssf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成水印图片
 */
public class WatermarkImg {
    /**
     * 生成水印图片
     *
     * @param headers  水印内容 表头
     * @param allValue 水印内容
     * @param totalcol 需要生成的列
     * @return 路径
     * @throws Exception
     */
    public static String graphicsGeneration(List<String> headers, List<String> allValue, int totalcol) throws Exception {
        int width = 400;//图片宽
        int height = 300;//图片高
        // 创建BufferedImage对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //获取Graphics2D对象
        Graphics2D graphics = image.createGraphics();
        // ----------  增加下面的代码使得背景透明  -----------------
        image = graphics.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);//设置透明
        graphics.dispose();
        graphics = image.createGraphics();
        // ----------  背景透明代码结束  -----------------
        // 画图
        graphics.setColor(new Color(255, 0, 0));
        graphics.setStroke(new BasicStroke(1));
        //设置字体
        Font font = new Font("SimHei", Font.BOLD, 40);
        graphics.setFont(font);
        graphics.drawString("中国的黑体", 10, 60);
        //释放对象
        graphics.dispose();
        // 保存文件 D:\\watermark\\规档章.xls
        String path = "D:\\watermark\\1.jpg";
        ImageIO.write(image, "png", new File(path));
        return path;
    }


    /**
     * 传递图表数据test
     */
    public static void initChartData() throws Exception {
        List<String> headTitles = new ArrayList<>();//表头列
        headTitles.add("部门");
        headTitles.add("档号");
        headTitles.add("密级");
        headTitles.add("时间");
        List<String> allValue = new ArrayList<>();//内容集合
        allValue.add("交通部");
        allValue.add("9999");
        allValue.add("机密");
        allValue.add("2019年07月19号");
        graphicsGeneration(headTitles, allValue, 4);
    }


    /**
     * 导出Excel
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @param wb        HSSFWorkbook对象
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) throws IOException {
        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }
        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居左格式

        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for (int i = 0; i < values.length; i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        ToPNG(wb,sheetName,title,values);
        return wb;
    }


    public static void ToPNG(HSSFWorkbook wb,String sheetName,String[] title, String[][] allValue){
        HSSFSheet sheetDO = wb.getSheet(sheetName);
        int  rowCount= sheetDO.getPhysicalNumberOfRows(); // 获取整个sheet中行数
        for (int i = 0; i < rowCount; i++) {
            HSSFRow rowTemp = sheetDO.getRow(i);
            int columnNum=rowTemp.getPhysicalNumberOfCells();
            for (int j = 0; j <columnNum ; j++) {
                HSSFCell cellTemp = rowTemp.getCell(j);
                System.out.print(cellTemp.getStringCellValue()+"\t");
            }
            System.out.println();
        }
    }
    /**
     * 获取竖线和文字的水平位置
     * @param k
     * @param startWidth
     * @param colwidth
     * @param imageWidth
     * @return
     */
    private static int getRightMargin(int k, int startWidth, int colwidth, int imageWidth) {
        int rightLine = 0;
        if (k == 0) {
            rightLine = startWidth;
        } else if (k == 1) {
            rightLine = startWidth + colwidth / 2;
        } else if (k == 2) {
            rightLine = startWidth + 3 * colwidth / 2;
        } else if (k == 3) {
            rightLine = startWidth + 7 * colwidth / 2;
        } else if (k == 4) {
            rightLine = imageWidth - 5;
        }
        return rightLine;
    }


    public static void main(String[] args) throws Exception {
//        initChartData();
        String[] headTitles = new String[4];//表头列
        headTitles[0] = "部门";
        headTitles[1] = "档号";
        headTitles[2] = "密级";
        headTitles[3] = "时间";
        String[][] allValue = new String[1][4];//内容集合 1行4列
        allValue[0][0] = "交通部";
        allValue[0][1] = "9999";
        allValue[0][2] = "机密";
        allValue[0][3] = "2019年07月19号";
//        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
//        String sheetName = "归档章";//工作表名称
//        String excelName = "D:\\watermark\\规档章.xls";//Excel文件名
//        hssfWorkbook = getHSSFWorkbook(sheetName, headTitles, allValue, hssfWorkbook);
        //响应到客户端
//        OutputStream os = new FileOutputStream(new File(excelName));
//        hssfWorkbook.write(os);
//        os.flush();
//        os.close();


        int imageWidth = 400;//图片宽
        int imageheight = 300;//图片高
    }


}
