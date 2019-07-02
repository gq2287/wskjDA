package com.wsda.project.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成归档图片
 */
public class GraphicsImage {
    /**
     * 生成归档章
     *
     * @param headers  表头
     * @param allValue 表头对应内容
//     * @param totalrow 总列行数
     * @param totalcol 总列数
     * @return
     * @throws Exception
     */
    public static String graphicsGeneration(List<String> headers, List<String> allValue,int totalcol) throws Exception {
        // 实际数据行数+标题+备注
        int imageWidth = 240;//图片宽度
        int imageHeight = 2 * 30;//高度 30字体
        int startHeight = 0;//起始高 y
        int startWidth = 0;//起始宽 x

        // 定义图像buffer
        BufferedImage buffImg = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = buffImg.createGraphics();
        //生成图片的大小
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        //start  增加下面代码使得背景透明
        buffImg = graphics.getDeviceConfiguration().createCompatibleImage(imageWidth, imageHeight, Transparency.TRANSLUCENT);
        graphics = buffImg.createGraphics();
        //end    背景透明代码结束

        // 画横线
        graphics.setColor(Color.RED);
        graphics.drawRect(startWidth,startHeight,imageWidth-1,imageHeight-1);
        graphics.drawLine(startWidth,imageHeight/2,imageWidth,imageHeight/2);//绘制中间的分割线


        //获取当前标题每列文字长度
        int fontSum=0;
        int[] fontLength = new int[headers.size()];//获取标题字段长度
        for (int k = 0; k < headers.size(); k++) {
            fontLength[k]=headers.get(k).length();
            fontSum+=fontLength[k];
        }
        int startW=imageWidth/fontSum;//每个字占总宽度多少
        // 画竖线
        int verticalLine=0;//竖线宽度
        for (int j = 0; j <totalcol ; j++) {
            graphics.drawLine(verticalLine,0,verticalLine,imageHeight);
            verticalLine=verticalLine+fontLength[j]*startW;//追加宽度
        }

        // 写入表头
        verticalLine=0;
        graphics.setFont(new Font("宋体", Font.BOLD, 15));
        for (int i = 0; i < headers.size(); i++) {//imageHeight
            graphics.drawString(headers.get(i), verticalLine,imageHeight/2-10);//x y 都是字体左下角的位置
            verticalLine=verticalLine+fontLength[i]*startW+1;//追加宽度
        }


        // 写入内容
        verticalLine=0;
        graphics.setFont(new Font("宋体", Font.PLAIN, 12));
        for (int i = 0; i < allValue.size(); i++) {//imageHeight
            if(verticalLine==0){
                graphics.drawString(allValue.get(i), 0,imageHeight-10);
            }else{
                graphics.drawString(allValue.get(i), verticalLine,imageHeight-10);
            }
            verticalLine=verticalLine+fontLength[i]*startW+1;//追加宽度
        }

        String path = "E:\\Desktop\\GQ_GuoQ\\1.png";
        ImageIO.write(buffImg, "png", new File(path));
        return path;
    }

    /**
     * 测试
     * 初始化数据
     *
     * @throws Exception
     */
    public static String initChartData() throws Exception {
        List<String> headTitles = new ArrayList<>();
        headTitles.add("部门");
        headTitles.add("年度");
        headTitles.add("保管期限");
        headTitles.add("页数");

        List<String> allValue = new ArrayList<>();
        allValue.add("办公室");
        allValue.add("2019");
        allValue.add("永久");
        allValue.add("888");

        return graphicsGeneration(headTitles, allValue,4);
    }

    public static void main(String[] args) throws Exception {
        String imagepath=initChartData();//生成图片水印

    }


    /**public static void main(String[] args) throws Exception{
     //服务器信息
     Map<String,Object> ethernetInfo=CPUUtil.serverInfo();
     for (String str:ethernetInfo.keySet()) {
     System.out.println(str+"--"+ethernetInfo.get(str));
     }
     //系统信息
     ethernetInfo=CPUUtil.systemInfo();
     for (String str:ethernetInfo.keySet()) {
     System.out.println(str+"--"+ethernetInfo.get(str));
     }
     //CPU信息
     ethernetInfo=CPUUtil.cpuInfo();
     for (String str:ethernetInfo.keySet()) {
     System.out.println(str+"--"+ethernetInfo.get(str));
     }
     //JVM信息
     ethernetInfo=CPUUtil.jvmInfo();
     for (String str:ethernetInfo.keySet()) {
     System.out.println(str+"--"+ethernetInfo.get(str));
     }
     //内存信息
     ethernetInfo=CPUUtil.memoryInfo();
     for (String str:ethernetInfo.keySet()) {
     System.out.println(str+"--"+ethernetInfo.get(str));
     }
     //磁盘文件信息
     ethernetInfo=CPUUtil.fileSystemInfo();
     for (String str:ethernetInfo.keySet()) {
     System.out.println(str+"--"+ethernetInfo.get(str));
     }
     //网络信息
     ethernetInfo=CPUUtil.netInfo();
     for (String str:ethernetInfo.keySet()) {
     System.out.println(str+"--"+ethernetInfo.get(str));
     }
     //以太网信息
     ethernetInfo=CPUUtil.ethernetInfo();
     for (String str:ethernetInfo.keySet()) {
     System.out.println(str+"--"+ethernetInfo.get(str));
     }
     }*/

}
