package com.wsda.project.util;

import com.aspose.words.SaveFormat;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.*;
import java.util.Date;

/**
 * 转换pdf
 *
 * @author milk
 */
public class Change2PDF {
    private static final Integer WORD_TO_PDF_OPERAND = 17;
    private static final Integer EXCEL_TO_PDF_OPERAND = 0;

    /**
     * txt转PDF
     *
     * @param filePath
     * @param pdfPath
     * @throws IOException
     * @throws DocumentException
     * @throws Exception
     */
    public void txt2pdf(String filePath, String pdfPath)
            throws DocumentException, IOException {
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);

        FileOutputStream out = new FileOutputStream(pdfPath);

        Rectangle rect = new Rectangle(PageSize.A4.rotate());

        Document doc = new Document(rect);

        PdfWriter writer = PdfWriter.getInstance(doc, out);

        doc.open();
        Paragraph p = new Paragraph();
        p.setFont(FontChinese);

        BufferedReader read = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), "UTF-8"));

        String line = read.readLine();
        while (line != null) {
            p.add(line + "\n");
            line = read.readLine();
        }
        read.close();

        doc.add(p);

        doc.close();
    }

    /**
     * tif转PDF
     *
     * @param filePath
     * @param pdfPath
     */
    public void tif2PDF(String filePath, String pdfPath) {
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
                }
            }
            ra.close();
            document.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void doc2pdfA(String filePath, String pdfPath) {
        FileOutputStream os = null;
        try {
            File file = new File(pdfPath);
            os = new FileOutputStream(file);
            com.aspose.words.Document doc = new com.aspose.words.Document(
                    filePath);
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void Image2PdfA(String filePath, String pdfPath) throws IOException {
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
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            document.close();
            fos.flush();
            fos.close();
        }
    }

    /**
     * 郭旗
     * excle转PDF
     *
     * @param inputFile excel
     * @param pdfFile   pdf
     * @author ygl
     */
    public static int excel2PdfA(String inputFile, String pdfFile) {
        try {
            ComThread.InitSTA(true);
            ActiveXComponent ax = new ActiveXComponent("KET.Application");
            long date = new Date().getTime();
            ax.setProperty("Visible", false);
            ax.setProperty("AutomationSecurity", new Variant(3)); // 禁用宏
            Dispatch excels = ax.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch
                    .invoke(excels, "Open", Dispatch.Method,
                            new Object[]{inputFile, new Variant(false), new Variant(false)}, new int[9])
                    .toDispatch();
            // 转换格式
            Dispatch.invoke(excel, "ExportAsFixedFormat", Dispatch.Method, new Object[]{new Variant(0), // PDF格式=0
                    pdfFile, new Variant(0) // 0=标准 (生成的PDF图片不会变模糊) 1=最小文件
                    // (生成的PDF图片糊的一塌糊涂)
            }, new int[1]);

            // 这里放弃使用SaveAs
            long date2 = new Date().getTime();
            int time = (int) ((date2 - date) / 1000);
            Dispatch.call(excel, "Close", new Variant(false));

            if (ax != null) {
                ax.invoke("Quit", new Variant[]{});
                ax = null;
            }
            ComThread.Release();
            return time;
        } catch (Exception e) {
            // TODO: handle exception
            return -1;
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
}
