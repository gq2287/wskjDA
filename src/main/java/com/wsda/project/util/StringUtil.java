package com.wsda.project.util;


import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * .
 * 字符串工具类
 *
 * @author seven
 */
public class StringUtil {

    /**
     * .
     * 判断字符串是否为空
     *
     * @param param 字符串参数
     * @return true为空，false为非空
     */
    public static boolean isBlank(String param) {
        if (null == param) {
            return true;
        }
        int len = param.length();
        if (len == 0) {
            return true;
        }
        for (int i = 0; i < len; i++) {
            switch (param.charAt(i)) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    /**
     * .
     * 判断字符串是否不为空
     *
     * @param param 字符串变量
     * @return true为不为空，false为空
     */
    public static boolean notBlank(String param) {
        return !isBlank(param);
    }

    /**
     * .
     * 判断多个字符串是否不为空
     *
     * @param params 多个字符串变量
     * @return true为不为空，false为空
     */
    public static boolean notBlank(String... params) {
        if (params == null || params.length == 0) {
            return false;
        }
        for (String param : params) {
            if (isBlank(param)) {
                return false;
            }
        }
        return true;
    }

    /**
     * .
     * 获取UUID
     *
     * @return 返回UUID字符串
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    /**
     * .
     * 获取时间
     * @param type 时间格式 1日期  2字符串
     * @return 返回当前时间字符串
     */
    public static String getDate(int type){
        SimpleDateFormat df=null;
        if(type==1){
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        }else if(type==2){
            df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        }
        return df.format(new Date());
    }
    /**
     * .
     * 获取min到max之间的随机数
     *
     * @return 返回随机数字符串
     */
    public static String getRandom(int min, int max){
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return String.valueOf(s);
    }
    /**
     * .
     * 验证手机号是否合法
     *
     * @param phone 手机号码
     * @return 合法 返回true，反之false
     */
    public static boolean validPhoneNo(String phone) {
        String regMobile = "^((19[0-9])|(17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
        return Pattern.matches(regMobile, phone);
    }

    /**
     * .
     * 获取指定长度的随机字符串
     *
     * @param len 长度
     * @return 返回随机字符串
     */
    public static String getRandomStr(int len) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * .
     * 获取随机数字验证码
     *
     * @param len 长度
     * @return 返回指定长度的随机数
     */
    public static String getRandomNum(int len) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * .
     * 根据Request获取客户端的IP地址
     *
     * @param request 请求对象
     * @return 返回IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        return ip;
    }

    /**
     * .
     * 验证中国公民身份证号码是否合法
     *
     * @param idCardNo 中国公民身份证号码
     * @return 验证结果，true为合法，false为不合法
     */
    public static boolean validIdCardNo(String idCardNo) {
        // 对身份证号进行长度等简单判断
        if (idCardNo == null || idCardNo.length() != 18
                || !idCardNo.matches("\\d{17}[0-9X]")) {
            return false;
        }
        // 1-17位相乘因子数组
        int[] factor = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        // 18位随机码数组
        char[] random = "10X98765432".toCharArray();
        // 计算1-17位与相应因子乘积之和
        int total = 0;
        for (int i = 0; i < 17; i++) {
            total += Character.getNumericValue(idCardNo.charAt(i)) * factor[i];
        }
        // 判断随机码是否相等
        return random[total % 11] == idCardNo.charAt(17);
    }

    /**
     * .
     * base64编码成图片存储
     *
     * @param base64   图标base64字符串
     * @param filePath 文件存放路径
     * @return 转换结果，true成功，false失败
     */
    public static boolean base64DecoderImg(String base64, String filePath) {
        if (base64 == null) { //图像数据为空
            return false;
        }
        base64 = base64.replaceAll("\r|\n", "");

        Decoder decoder = Base64.getDecoder();
        try {
            //Base64解码
            byte[] b = decoder.decode(base64);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) { //调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(filePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * .
     * 把图片转换为base64编码字符串
     *
     * @param imgFile 图片文件完整路径
     * @return base64字符串
     */
    public static String base64Encoder(File imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }

    /**
     * 获取base64中的图片的url-课程
     *
     * @return
     */
    public static List<String> getBase64ImgUrl(String htmlStr) {
        if (StringUtil.notBlank(htmlStr)) {
            List<String> list = new ArrayList<>();
            String img = "";
            Pattern p_image;
            Matcher m_image;
            // String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
            String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
            p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
            m_image = p_image.matcher(htmlStr);
            while (m_image.find()) {
                // 得到<img />数据
                img = m_image.group();
                // 匹配<img>中的src数据
                Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
                while (m.find()) {
                    list.add(m.group(1));
                }
            }
            return list;
        }
        return null;
    }


    /**
     * 获取base64中的图片的url-讲义
     *
     * @return
     */
    public static List<String> getBase64ImgUrlByChandouts(String htmlStr) {
        if (StringUtil.notBlank(htmlStr)) {
            List<String> list = new ArrayList<>();
            String img = "";
            Pattern p_image;
            Matcher m_image;
            // String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
            String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
            p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
            m_image = p_image.matcher(htmlStr);
            while (m_image.find()) {
                // 得到<img />数据
                img = m_image.group();
                String temp = img.replaceAll(" ", "+");
                String newImg = temp.replace("img+src", "img src");
                // 匹配<img>中的src数据
                Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(newImg);
                while (m.find()) {
                    list.add(m.group(1));
                }
            }
            return list;
        }
        return null;
    }


    /**
     * .
     * 字符变成列表
     *
     * @param origin 字符串
     * @param sp     分隔符
     * @return
     */
    public static List<String> strToList(String origin, String sp) {
        try {
            if (StringUtil.isBlank(origin)) {
                return new ArrayList<String>();
            }
            String[] strArray = origin.split(sp);
            return Arrays.asList(strArray);
        } catch (Exception ex) {
            return new ArrayList<String>();
        }
    }


    /**
     * 获取域名
     *
     * @param request
     * @return
     */
    public static String getDomain(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String domain = referer.substring(0, referer.indexOf(".cn/") + 3);
        return domain;
    }


    /**
     * 获取唯一文件名
     * @param file
     * @return
     */
    public static String getFileName(File file){
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhssmm");
        String fileName=file.getName().substring(0,file.getName().lastIndexOf("."))+"_"+sdf.format(date);//文件名称
        String suffixIndex =file.getName().substring(file.getName().lastIndexOf("."),file.getName().length());//后缀
        fileName=fileName+suffixIndex;
        System.out.println(fileName);
        return fileName;
    }
    /***
     * 在idea中读取同级目录config下的项目配置文件路径
     * @return
     */
    public static String getRealPathByIdea() {
        String realPath = StringUtil.class.getClassLoader().getResource("").getFile();
        File file = new File(realPath);
        realPath = file.getAbsolutePath();//去掉了最前面的斜杠/
        try {
//            realPath=realPath.substring(4,realPath.indexOf("FormDesigner"))+"config\\db.properties";//打包后修改为4
            realPath=realPath.substring(0,realPath.indexOf("FormDesigner"))+"config\\db.properties";//idea运行为0
            System.out.println(realPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return realPath;
    }

    /***
     * 打包获取项目配置文件路径
     * @return
     */
    public static String getRealPathByPack() {
        String realPath = StringUtil.class.getClassLoader().getResource("").getFile();
        File file = new File(realPath);
        realPath = file.getAbsolutePath();//去掉了最前面的斜杠/
        try {
            realPath=realPath.substring(realPath.lastIndexOf(":")-1,realPath.indexOf("wsda"))+"config\\db.properties";
            System.out.println(realPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return realPath;
    }

    /**
     * 验证日期格式 是否为yyyy-MM-dd
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        // 指定日期格式为四位年-两位月份-两位日期，注意yyyy/MM/dd区分大小写
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007-02-29会被接受，并转换成2007-03-01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }


    public static void main(String[] args) {//"conditions":
        String column="AAAA-12343423-sss";
        for (int i = 0; i <column.split("-").length ; i++) {
            System.out.println(column.split("-")[i]);
            System.out.println(getUuid().length());
        }
    }
}