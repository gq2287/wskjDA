package com.wsda.project.util;

import com.aspose.cells.Worksheet;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.codec.TiffImage;

import javax.swing.*;
import java.io.*;

/**
 * 转换pdf
 *
 * @author milk
 */
public class Change2PDF {


    /**
     * 新text转pdf
     *
     * @param filePath
     * @param pdfPath
     */
    public static boolean txt2PDF(String filePath, String pdfPath) {
        boolean bool = true;
        File file = new File(filePath);
        FileInputStream in = null;
        //创建Document对象.
        Document document = new Document();
        try {
            in = new FileInputStream(file);
            //创建输出目标 字体
            BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font = new Font(baseFont);
            File pdffile = new File(pdfPath.substring(0, pdfPath.lastIndexOf("\\")));//防止路径不存在报错
            if (!pdffile.exists()) {
                pdffile.mkdirs();//创建存放路径
            }
            FileOutputStream pdfout = new FileOutputStream(pdfPath);
            PdfWriter.getInstance(document, pdfout);
            //打开Document.
            document.open();
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            document.add(new Paragraph(new String(bytes), font));
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            bool = false;
        } catch (DocumentException e) {
            e.printStackTrace();
            bool = false;
        } catch (IOException e) {
            e.printStackTrace();
            bool = false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    bool = false;
                }
            }
            return bool;
        }

    }

    /**
     * tif转PDF
     *
     * @param filePath
     * @param pdfPath
     */
    public static boolean tif2PDF(String filePath, String pdfPath) {
        File pdffile = new File(pdfPath.substring(0, pdfPath.lastIndexOf("\\")));//防止路径不存在报错
        if (!pdffile.exists()) {
            pdffile.mkdirs();//创建存放路径
        }
        boolean bool = true;
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
                System.out.println("Exception in " + filePath + " " + e.getMessage());
                bool = false;
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
                    System.out.println("tif to pdf success OK");
                } catch (Throwable e) {
                    System.out.println("Exception " + filePath + " page "
                            + (c + 1) + " " + e.getMessage());
                    bool = false;
                    return bool;
                }
            }
            ra.close();
            document.close();
        } catch (Throwable e) {
            bool = false;
            return bool;
        }
        return bool;
    }

    /**
     * 图片转换pdf(jpg,png等)
     *
     * @param filePath
     * @param pdfPath
     * @throws IOException
     */
    public static boolean Image2PDF(String filePath, String pdfPath) {
        File pdffile = new File(pdfPath.substring(0, pdfPath.lastIndexOf("\\")));//防止路径不存在报错
        if (!pdffile.exists()) {
            pdffile.mkdirs();//创建存放路径
        }
        boolean bool = true;
        File file = new File(filePath);
        Document document = null;
        FileOutputStream fos = null;
        if (file.exists()) {
            try {
                fos = new FileOutputStream(pdfPath);//pdf路径
                document = new Document();
                PdfWriter.getInstance(document, fos);
                document.setPageSize(PageSize.A4);
                document.open();
                Image image = Image.getInstance(filePath);//原图片
                float imageHeight = image.getScaledHeight();
                float imageWidth = image.getScaledWidth();
                image.setAlignment(Image.ALIGN_CENTER);
                int percent = getPercent(imageHeight, imageWidth);
                image.scalePercent(percent);
                document.add(image);
                fos.flush();
                System.out.println("Image to pdf success OK");
            } catch (DocumentException de) {
                System.out.println(de.getMessage());
                bool = false;
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                bool = false;
            } finally {
                if (document != null) {
                    document.close();
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        bool = false;
                    }
                }
                return bool;
            }
        } else {
            return false;
        }
    }


    /**
     * 去除Aspose水印
     *
     * @return
     */
    public static boolean getLicense() {
        //resource下的xml打包后找不到，直接写方法里
        String liceStr="<License>\n" +
                "<Data>\n" +
                "    <Products>\n" +
                "        <Product>Aspose.Total for Java</Product>\n" +
                "    </Products>\n" +
                "    <EditionType>Enterprise</EditionType>\n" +
                "    <SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                "    <LicenseExpiry>20991231</LicenseExpiry>\n" +
                "    <SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                "</Data>\n" +
                "<Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                "</License>";
        boolean result = true;
        try {
            InputStream license =  new ByteArrayInputStream(liceStr.getBytes("UTF-8"));
            License aposeLic = new License();
            aposeLic.setLicense(license);
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    /**
     * doc转换pdf(doc,docx)
     *
     * @param filePath
     * @param pdfPath
     */
    public static boolean doc2PDF(String filePath, String pdfPath) {
        boolean bool = true;
        FileOutputStream os = null;
        try {
            if (!getLicense()) {
                System.out.println("授权失效,请联系管理员");
                bool = false;
                return bool;
            }
            File pdffile = new File(pdfPath.substring(0, pdfPath.lastIndexOf("\\")));//防止路径不存在报错
            if (!pdffile.exists()) {
                pdffile.mkdirs();//创建存放路径
            }
            File file = new File(pdfPath);
            os = new FileOutputStream(file);
            com.aspose.words.Document doc = new com.aspose.words.Document(filePath);
            doc.save(os, SaveFormat.PDF);
            System.out.println("doc to pdf success OK");
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    bool = false;
                }
            }
            return bool;
        }
    }

    /**
     * excel转换pdf(xls,xlsx)
     *
     * @param excelPath
     * @param pdfPath
     * @return
     */
    public static boolean excel2PDF(String excelPath, String pdfPath) {
        boolean bool = true;
        OutputStream outputStream = null;
        if (!getLicense()) {
            System.out.println("授权失效,请联系管理员");
            bool = false;
            return bool;
        }
        File pdffile = new File(pdfPath.substring(0, pdfPath.lastIndexOf("\\")));//防止路径不存在报错
        if (!pdffile.exists()) {
            pdffile.mkdirs();//创建存放路径
        }
        try {
            InputStream inputStream = new FileInputStream(new File(excelPath));
            outputStream = new FileOutputStream(new File(pdfPath));
            com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook(inputStream);
            Worksheet ws = workbook.getWorksheets().get(0);
            com.aspose.cells.PdfSaveOptions pdfSaveOptions = new com.aspose.cells.PdfSaveOptions();
            pdfSaveOptions.setOnePagePerSheet(true);
            ws.getHorizontalPageBreaks().clear();
            ws.getVerticalPageBreaks().clear();
            workbook.save(outputStream, pdfSaveOptions);
            outputStream.flush();
            // TODO 当excel宽度太大时，在PDF中会拆断并分页。此处如何等比缩放。
            // 将不同的sheet单独保存为pdf
            //Get the count of the worksheets in the workbook
//            int sheetCount = workbook.getWorksheets().getCount();
            System.out.println("excel to pdf success OK");
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    bool = false;
                }
            }
            return bool;
        }
    }


    /**
     * 倍率
     *
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
     * pdf文件添加水印
     *
     * @param file      pdf文件
     * @param waterText 添加文字水印
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static String addtextWatermark(File file, String waterText) throws IOException, DocumentException {
        //获取pdfWatermark的存放路径
        String fileSavePath = file.getPath().substring(0, file.getPath().lastIndexOf("."));
        fileSavePath = fileSavePath + "Watermark" + ".pdf";//水印保存位置
        //待加水印的文件
        PdfReader reader = new PdfReader(file.getPath());
        // 加完水印的文件
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(fileSavePath));
        int total = reader.getNumberOfPages() + 1;
        //获取文档
        Document document = new Document(reader.getPageSize(1));
        // 获取页面宽度
        float widths = document.getPageSize().getWidth();
        // 获取页面高度
        float heights = document.getPageSize().getHeight();
//        document.close();
        PdfContentByte content;
        // 设置字体
        BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 循环对每页插入水印
        for (int i = 1; i < total; i++) {
            content = stamper.getOverContent(i);
            // 开始
            content.beginText();
            // 设置颜色
            content.setColorFill(BaseColor.GRAY);
            // 设置字体及字号
            content.setFontAndSize(baseFont, 55);
            //透明度
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.5f);// 设置透明度为0.8
            content.setGState(gs);
            // 开始写入水印 设置起始位置 旋转
            content.showTextAligned(Element.ALIGN_CENTER, waterText, widths / 2, heights / 2, 50);
            content.endText();
        }
        stamper.setFormFlattening(false);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
        stamper.close();//一定要在循环外关闭,不然只有第一页有水印
        reader.close();//关闭读取否则文件删除不了
        return fileSavePath;
    }

    /**
     * pdf文件添加水印
     *
     * @param file      pdf文件
     * @param waterImage 添加图片水印
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static String addimageWatermark(File file, String waterImage) throws IOException, DocumentException {
        ImageIcon imageIcon = new ImageIcon(waterImage);
        int iconWidth = imageIcon.getIconWidth();//图片宽
        int iconHeight = imageIcon.getIconHeight();//图片高

        //获取pdfWatermark的存放路径
        String fileSavePath = file.getPath().substring(0, file.getPath().lastIndexOf("."));
        fileSavePath = fileSavePath + "Image" + ".pdf";//水印保存位置
        PdfReader reader = new PdfReader(file.getPath());//待添加水印位置
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(fileSavePath));//水印文件保存位置

        //获取文档
        Document document = new Document(reader.getPageSize(1));
        // 获取页面宽度
        float widths = document.getPageSize().getWidth();
        // 获取页面高度
        float heights = document.getPageSize().getHeight();

        PdfContentByte content;
        Image img = Image.getInstance(waterImage);
        img.setAbsolutePosition(widths-iconWidth-20, heights-iconHeight-20);//减去图片宽高和10以上边框线确保图片完整显示 并保留有边距
        content = stamp.getOverContent(1);// 在第一页内容上方加水印
        content.addImage(img);
        stamp.close();
        reader.close();
        return fileSavePath;
    }

    public static void main(String[] args) throws Exception {
        getLicense();
    }
}
