package com.wsda.project.util;

import com.aspose.cells.Worksheet;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.ParseException;
import java.util.Random;

/**
 * 转换pdf
 *
 * @author milk
 */
public class Change2PDF {


    /**
     * 新text转pdf
     * @param filePath
     * @param pdfPath
     */
    public static void txt2PDF(String filePath, String pdfPath) throws DocumentException, IOException  {
        Document document = new Document();
        OutputStream os = new FileOutputStream(pdfPath);
        PdfWriter.getInstance(document, os);
        document.open();
//        方法一：使用Windows系统字体(TrueType)
        BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\simhei.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(baseFont);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "GBK");
        BufferedReader bufferedReader = new BufferedReader(isr);
        String str = "";
        while ((str = bufferedReader.readLine()) != null) {
            document.add(new Paragraph(str, font));
        }
        document.close();
    }

    /**
     * tif转PDF
     *
     * @param filePath
     * @param pdfPath
     */
    public static boolean tif2PDF(String filePath, String pdfPath) {
        boolean bool=true;
        Document document = new Document(PageSize.A4);
        int pages = 0, comps = 0;
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            RandomAccessFileOrArray ra = null;
            try {
                ra = new RandomAccessFileOrArray(filePath);
                comps = TiffImage.getNumberOfPages(ra);
            } catch (Throwable e) {
                System.out.println("Exception in " + filePath + " "
                        + e.getMessage());
                bool=false;
                return bool;
            }
            for (int c = 0; c < comps; ++c) {
                try {
                    Image img = TiffImage.getTiffImage(ra, c + 1);
                    if (img != null) {
                        float heigth = img.getHeight();
                        float width = img.getWidth();
                        int percent = getPercent(heigth, width);
                        img.setAbsolutePosition(0, 0);
                        img.setAlignment(Image.MIDDLE);
                        img.scalePercent(percent);
                        cb.addImage(img);
                        document.newPage();
                        ++pages;
                    }
                } catch (Throwable e) {
                    System.out.println("Exception " + filePath + " page "
                            + (c + 1) + " " + e.getMessage());
                    bool=false;
                    return bool;
                }
            }
            ra.close();
            document.close();
        } catch (Throwable e) {
            bool=false;
            return bool;
        }
        return bool;
    }

    /**
     * 图片转换pdf(jpg,png等)
     * @param filePath
     * @param pdfPath
     * @throws IOException
     */
    public static boolean Image2PDF(String filePath, String pdfPath) throws IOException {
        boolean bool=true;
        File file = new File(filePath);
        if (file.exists()) {
            Document document = new Document();

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(pdfPath);
                PdfWriter.getInstance(document, fos);
                document.setPageSize(PageSize.A4);
                document.open();
                Image image = Image.getInstance(filePath);
                float imageHeight = image.getScaledHeight();
                float imageWidth = image.getScaledWidth();
                image.setAlignment(Image.ALIGN_CENTER);
                int percent = getPercent(imageHeight, imageWidth);
                image.scalePercent(percent);
                document.add(image);
            } catch (DocumentException de) {
                System.out.println(de.getMessage());
                bool=false;
                return bool;
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                bool=false;
                return bool;
            }
            document.close();
            fos.flush();
            fos.close();
        }
        bool=false;
        return bool;
    }


    /**
     * 去除Aspose水印
     * @return
     */
    public static boolean getLicense() {
        boolean result = true;
        try {
            InputStream license = Change2PDF.class.getClassLoader().getResourceAsStream("license.xml"); // license路径
            License aposeLic = new License();
            aposeLic.setLicense(license);
        } catch (Exception e) {
            result=false;
        }
        return result;
    }

    /**
     * doc转换pdf(doc,docx)
     * @param filePath
     * @param pdfPath
     */
    public static boolean doc2PDF(String filePath, String pdfPath) {
        boolean bool=true;
        FileOutputStream os = null;
        try {
            if(!getLicense()){
                System.out.println("授权失效,请联系管理员");
                bool=false;
                return bool;
            }
            File file = new File(pdfPath);
            os = new FileOutputStream(file);
            com.aspose.words.Document doc = new com.aspose.words.Document(filePath);
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
            bool=false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    bool=false;
                }
            }
            return bool;
        }
    }



    /**
     * excel转换pdf(xls,xlsx)
     * @param excelPath
     * @param pdfPath
     * @return
     * @throws Exception
     */
    public static boolean excel2PDF(String excelPath,String pdfPath) throws Exception {
        boolean bool=true;
        if (getLicense()) {
            InputStream inputStream = new FileInputStream(new File(excelPath));
            OutputStream outputStream = new FileOutputStream(new File(pdfPath));
            com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook(inputStream);
            Worksheet ws = workbook.getWorksheets().get(0);
            com.aspose.cells.PdfSaveOptions pdfSaveOptions = new com.aspose.cells.PdfSaveOptions();
            pdfSaveOptions.setOnePagePerSheet(true);
            ws.getHorizontalPageBreaks().clear();
            ws.getVerticalPageBreaks().clear();
            workbook.save(outputStream, pdfSaveOptions);
            outputStream.flush();
            outputStream.close();
            // TODO 当excel宽度太大时，在PDF中会拆断并分页。此处如何等比缩放。
            // 将不同的sheet单独保存为pdf
            //Get the count of the worksheets in the workbook
//            int sheetCount = workbook.getWorksheets().getCount();
            System.out.println("excel to pdf success");
            return bool;
        } else {
            System.out.println("excel to pdf error");
            bool=false;
            return bool;
        }
    }


    /**
     * 倍率
     * @param h
     * @param w
     * @return
     */
    public static int getPercent(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 297 / h * 274;
        } else {
            p2 = 210 / w * 268;
        }
        p = Math.round(p2);
        return p;
    }



    /**
     * 水印图
     * @param originalImagePath 	需打水印的原图片路径
     * @param watermarkImagePath 	水印后的图片路径
     * @param alpha 				透明度
     * @param x 					距x轴的距离
     * @param y 					距y轴的距离
     * @return
     */
    public static byte[] imageWatermarkProcess(String originalImagePath,String watermarkImagePath, float alpha, int x, int y) {
        try {
            // 原图
            java.awt.Image original = Toolkit.getDefaultToolkit().getImage(originalImagePath);
            original = new ImageIcon(original).getImage();
            int width = original.getWidth(null);
            int height = original.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2d = bufferedImage.createGraphics();
            graphics2d.drawImage(original, 0, 0, width, height, null);
            // 水印图
            java.awt.Image watermark = Toolkit.getDefaultToolkit().getImage(watermarkImagePath);
            watermark = new ImageIcon(watermark).getImage();
            int watermarkWidth = watermark.getWidth(null);
            int watermarkHeight = watermark.getHeight(null);
            graphics2d.setComposite(AlphaComposite.getInstance(10, alpha));
            graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            int widthDiff = width - watermarkWidth;
            int heightDiff = height - watermarkHeight;
            // 若水印图尺寸大于原图，等比例缩小1/4
            if (widthDiff <= 0 || heightDiff <= 0) {
                watermarkWidth /= 4;
                watermarkHeight /= 4;
                widthDiff = width - watermarkWidth;
                heightDiff = height - watermarkHeight;
            }
            // 保证水印图全部出现在原图中
            if (x < 0)
                x = widthDiff / 2;
            else if (x > widthDiff) {
                x = widthDiff;
            }
            if (y < 0)
                y = heightDiff / 2;
            else if (y > heightDiff) {
                y = heightDiff;
            }
            graphics2d.drawImage(watermark, x, y, watermarkWidth,watermarkHeight, null);
            graphics2d.dispose();
            String fileType = originalImagePath.substring(originalImagePath.lastIndexOf(".") + 1);
            ByteArrayOutputStream baops = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, fileType, baops);
            return baops.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用添加水印的方法
     * @param pdfPath 添加水印的文件路径
     * @param logoPath 水印logo
     * @return
     */
    public static String mergeWaterMark(String pdfPath,String logoPath) {
        if (null == pdfPath || -1 != pdfPath.indexOf("-merge-") || null == logoPath) {
            return "ERROR";
        }
        String watermarkPath = pdfPath.substring(0,pdfPath.lastIndexOf('/')+1);
        // 添加随机4位的数字目的是为了避免切换其他logo合成水印后，页面图片依然显示第一次logo水印合成图片（缓存的原因）
        String watermarkImagePath = pdfPath.substring(0,pdfPath.lastIndexOf("."))+"-merge-"+ new Random().nextInt(9999) +".jpg";
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        File imgDir = new File(watermarkPath);
        if(!imgDir.exists()){
            imgDir.mkdirs();
        }
        File fileImg = new File(watermarkImagePath);
        try {
            fos = new FileOutputStream(fileImg);
            bos = new BufferedOutputStream(fos);
            bos.write(imageWatermarkProcess(pdfPath, logoPath, 1.0F,10,10));
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        } finally {
            try {
                if(null != bos){
                    bos.close();
                }
                if(null != fos){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return watermarkImagePath;
    }




    public static void main(String[] args) throws ParseException {
        String fileText="E:\\Desktop\\wapgame\\省厅堡垒机访问策略调查表.xls";
        String filePdf="E:\\Desktop\\wapgame\\ACIV_ZXTSZTDA_LY.pdf";
        try {
            Change2PDF.tif2PDF(fileText,filePdf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
