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
    private static final int WIDTH = 600;//图片宽度
//    private static final int HEIGHT = 100;//图片高度
    /**
     * 生成归档章
     * @param headers 表头
     * @param allValue 表头对应内容
     * @param totalcol 总列数
     * @return
     * @throws Exception
     */
    public static String graphicsGeneration(List<String[]> headers,List<List<List<String>>> allValue ,int totalcol) throws Exception {
        int row = 0;// 总打印行数
        for (List<List<String>> typeV : allValue) {
            if (typeV != null && typeV.size() > 0&&headers!=null&&headers.size()>0) {
                row += (headers.size()+typeV.size());//确定打印几行
            }
        }
        // 实际数据行数+标题+备注
        int totalrow=row;
        int imageHeight =totalcol*30;//高度 30字体
        int rowheight = 30;//行高
        int startHeight = 0;//起始高
        int startWidth = 0;//起始宽
        int colwidth = ((WIDTH - 30) / totalcol);//列宽

        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(WIDTH, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = buffImg.createGraphics();
        // 增加下面代码使得背景透明
        buffImg = graphics.getDeviceConfiguration().createCompatibleImage(WIDTH, imageHeight, Transparency.TRANSLUCENT);
        graphics.dispose();
        graphics = buffImg.createGraphics();
        // 背景透明代码结束

        //生成图片的大小
        graphics.fillRect(0, 0, WIDTH, imageHeight);
        //画背景
        int startH = 1;
        for (List<List<String>> typeV : allValue) {
            if (typeV != null && typeV.size() > 0) {
                graphics.fillRect(startWidth+1, startHeight+startH*rowheight+1, WIDTH - startWidth-5-1,rowheight-1);
                startH+=2+typeV.size();
            }
        }

        // 画横线
        graphics.setColor(Color.RED);
        for (int j = 0; j < totalrow - 1; j++) {
            graphics.drawLine(startWidth, startHeight + (j + 1) * rowheight, WIDTH - 5,
                    startHeight + (j + 1) * rowheight);
        }

        // 画竖线
        graphics.setColor(Color.RED);
        startH = 1;
        int rightLine = 0 ;
        for (List<List<String>> typeV : allValue) {
            if (typeV != null && typeV.size() > 0) {
                for (int k = 0; k < totalcol+1; k++) {
                    rightLine = getRightMargin(k,startWidth, colwidth,WIDTH);
                    graphics.drawLine(rightLine, startHeight + startH*rowheight, rightLine,
                            startHeight + (typeV.size()+1+startH)*rowheight);
                }
                startH+=2+typeV.size();
            }
        }

        // 写入表头
        graphics.setFont(new Font("宋体", Font.BOLD, 30));
        startH = 2;
        for (String[] strings : headers) {
            if (strings != null && strings.length> 0) {
                for (int m = 0; m < strings.length; m++) {
                    rightLine = getRightMargin(m,startWidth, colwidth,WIDTH)+5;
                    graphics.drawString(strings[m].toString(), rightLine,startHeight + rowheight * startH - 10);
                }
                startH+=2+headers.size();
            }
        }


        // 写入内容
        graphics.setFont(new Font("宋体", Font.BOLD, 30));
        startH = 3;
        for (List<List<String>> typeV : allValue) {
            if (typeV != null && typeV.size() > 0) {
                for (int i = 0; i < typeV.size(); i++) {
                    List<String> arr = typeV.get(i);
                    for (int l = 0; l < arr.size(); l++) {
                        rightLine = getRightMargin(l,startWidth, colwidth,WIDTH)+5;
                        graphics.drawString(arr.get(l).toString(), rightLine,startHeight + rowheight * (i + startH) - 10);
                    }
                }
                startH+=2+typeV.size();
            }
        }

        String path = "E:\\Desktop\\GQ_GuoQ\\1.png";
        ImageIO.write(buffImg, "png", new File(path));
        return path;
    }

    /**
     * 获取竖线和文字的水平位置
     * @param k 第几条线
     * @param startWidth 起始位置
     * @param colwidth 列宽度
     * @param imageWidth 图片宽度
     * @return
     */
    private static int getRightMargin(int k, int startWidth, int colwidth, int imageWidth) {
        int rightLine = 0;//直线
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

    /** 测试
     * 初始化数据
     * @throws Exception
     */
    public static void initChartData() throws Exception {
        List<String[]> headTitles = new ArrayList<>();
        String[] h1 = new String[]{"部门","年度","归档日期","页数"};
        headTitles.add(h1);

        List<List<List<String>>> allValue = new ArrayList<>();
        List<String> content = Arrays.asList(new String[]{"投资","2019","永久","888"});//第一层 内容
        List<List<String>> contentArray = new ArrayList<>();//第二层负责生成位置
        contentArray.add(content);
        allValue.add(contentArray);

        graphicsGeneration(headTitles ,allValue,4);
    }

    public static void main(String[] args) throws Exception{
        initChartData();
    }

}
