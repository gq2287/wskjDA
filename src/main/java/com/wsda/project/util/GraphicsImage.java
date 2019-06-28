package com.wsda.project.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 生成归档图片
 */
public class GraphicsImage {
    public static String graphicsGeneration(List<List<List<String>>> allValue,List<String[]> headers ,String receiver,int totalcol) throws Exception {
        int rows = 0;
        for (List<List<String>> typeV : allValue) {
            if (typeV != null && typeV.size() > 0) {
                rows += (2+typeV.size());
            }
        }
        // 实际数据行数+标题+备注
        int totalrow = 1+rows;//
        int imageWidth = 800;//生成图片的宽
        int imageHeight = totalrow * 30 + 20;//
        int rowheight = 30;//
        int startHeight = 10;//
        int startWidth = 10;//
        int colwidth = ((imageWidth - 20) / totalcol);//

        // 定义图像buffer
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        // 增加下面代码使得背景透明
        image = graphics.getDeviceConfiguration().createCompatibleImage(imageWidth, imageHeight, Transparency.TRANSLUCENT);
//        graphics.dispose();
        graphics = image.createGraphics();
        // 背景透明代码结束

        graphics.setColor(Color.RED);
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        //画背景
        int startH = 1;
        for (List<List<String>> typeV : allValue) {
            if (typeV != null && typeV.size() > 0) {
                graphics.fillRect(startWidth+1, startHeight+startH*rowheight+1, imageWidth - startWidth-5-1,rowheight-1);
                startH+=2+typeV.size();
            }
        }
        // 画横线
        for (int j = 0; j < totalrow - 1; j++) {
            graphics.drawLine(startWidth, startHeight + (j + 1) * rowheight, imageWidth - 5,
                    startHeight + (j + 1) * rowheight);
        }

        // 画竖线
        startH = 1;
        int rightLine = 0 ;
        for (List<List<String>> typeV : allValue) {
            if (typeV != null && typeV.size() > 0) {
                for (int k = 0; k < totalcol+1; k++) {
                    rightLine = getRightMargin(k,startWidth, colwidth,imageWidth);
                    graphics.drawLine(rightLine, startHeight + startH*rowheight, rightLine,
                            startHeight + (typeV.size()+1+startH)*rowheight);
                }
                startH+=2+typeV.size();
            }
        }

        // 写入表头
        Font font = new Font("Serif", Font.BOLD, 20);
        graphics.setFont(font);
        startH = 2;
        int i = 0;
        for (List<List<String>> typeV : allValue) {
            if (typeV != null && typeV.size() > 0) {

                String[] headCells = headers.get(i);
                for (int m = 0; m < headCells.length; m++) {
                    rightLine = getRightMargin(m,startWidth, colwidth,imageWidth)+5;
                    graphics.drawString(headCells[m].toString(), rightLine,startHeight + rowheight * startH - 10);
                }
                startH+=2+typeV.size();
            }
            i++;
        }


        // 写入内容
        startH = 3;
        i = 0;
        for (List<List<String>> typeV : allValue) {
            graphics.setColor(Color.RED);
            if (typeV != null && typeV.size() > 0) {
                for (int n = 0; n < typeV.size(); n++) {
                    List<String> arr = typeV.get(n);
                    for (int l = 0; l < arr.size(); l++) {
                        rightLine = getRightMargin(l,startWidth, colwidth,imageWidth)+5;
                        graphics.drawString(arr.get(l).toString(), rightLine,startHeight + rowheight * (n + startH) - 10);
                    }
                }
                startH+=2+typeV.size();
            }
            i++;
        }

        String path = "E:\\Desktop\\GQ_GuoQ\\1.jpg";
        ImageIO.write(image, "jpg", new File(path));
        return path;
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

    public static void initChartData() throws Exception {
        List<List<List<String>>> allValue = new ArrayList<>();
        List<String> content1 = Arrays.asList(new String[]{"刘丹丹","25","163cm","未婚"});//第一层 内容
        List<List<String>> contentArray1 = new ArrayList<>();//第二层负责生成位置
        contentArray1.add(content1);
        allValue.add(contentArray1);

        List<String[]> headTitles = new ArrayList<>();
        String[] h1 = new String[]{"名字","年龄","身高","婚姻"};
        headTitles.add(h1);
        List<String> titles = new ArrayList<>();
        graphicsGeneration(allValue,headTitles ,"",4);
    }
    public static void main(String[] args) throws Exception{
        initChartData();
    }

}
