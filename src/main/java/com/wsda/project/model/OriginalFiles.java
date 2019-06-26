package com.wsda.project.model;

/**
 * 原文实体
 */
public class OriginalFiles {
    private String FILECODE;//原文编号
    private String RECORDCODE;//记录编号
    private String FILENAME;//原文名称
    private String PAGENO;//页号
    private String FILETYPE;//文件类型
    private String FILELENGTH;//文件长度
    private String USERCODE;//用户编号
    private String DEPARTMENTCODE;//用户部门编号
    private String UPLOADTIME;//上传时间
    private String STATUS;//状态
    private String FONDSNO;//全宗号
    private String FILLINGYEAR;//归档年度
    private String UPLOADUNIT;//上传单位
    private String READWRITECHECK;//读取检验
    private String CHECKUNIT;//检验人
    private String DOCUMENTCODE;//稿本代号
    private String BINGDUJIANYAN;//病毒检验
    private String ZAITIJIANYAN;//载体检验
    private String WANZHENGXINGJIANYA;//完整性检验
    private String YOUXIAOJIANYAN;//有效检验
    private String JISHUJIANYAN;//技术检验
    private String JIANRONGXINGJIANYAN;//兼容性检验
    private String USESOFT;//所用软件
    private String MAINTITLE;//标题
    private String BEIZHU;//备注
    private String CD_MARK;//光盘标记
    private String AUTOPDF;//自动PDF
    private String OSPOSITION;//本地主目录
    private String FILELOC;//FTP 目录
    private String FTPIP;//FTP 地址
    private String FTPPORT;//FTP 端口号
    private String FTPUSERNAME;//FTP 服务器用户名
    private String FTPPASSWORD;//FTP 密码
    private String OSTYPE;//存储类型        1本地,2ftp
    private String TRASHSTATUS;//回收站     0默认,1垃圾站
    private String ARCHIVECATALOGNO;//存档日志编号
    private String RETENTION;//保留
    private String ORIGINALFILEPATH;//原始文件路径
    private String FILE_NAME_ZH_CN;//文件中文名称
    private String YUAN_WEN_SHU_LIANG;//原文数量
    private String PDFPATH;//pdf路径
    private String ORIGINAPATH;//原文存放路径
    private String WATERMARKPATH;//水印pdf存放路径

    public OriginalFiles() {
    }

    public OriginalFiles(String FILECODE, String RECORDCODE, String FILENAME, String PAGENO, String FILETYPE, String FILELENGTH, String USERCODE, String DEPARTMENTCODE, String UPLOADTIME, String STATUS, String FONDSNO, String FILLINGYEAR, String UPLOADUNIT, String READWRITECHECK, String CHECKUNIT, String DOCUMENTCODE, String BINGDUJIANYAN, String ZAITIJIANYAN, String WANZHENGXINGJIANYA, String YOUXIAOJIANYAN, String JISHUJIANYAN, String JIANRONGXINGJIANYAN, String USESOFT, String MAINTITLE, String BEIZHU, String CD_MARK, String AUTOPDF, String OSPOSITION, String FILELOC, String FTPIP, String FTPPORT, String FTPUSERNAME, String FTPPASSWORD, String OSTYPE, String TRASHSTATUS, String ARCHIVECATALOGNO, String RETENTION, String ORIGINALFILEPATH, String FILE_NAME_ZH_CN, String YUAN_WEN_SHU_LIANG, String PDFPATH, String ORIGINAPATH, String WATERMARKPATH) {
        this.FILECODE = FILECODE;
        this.RECORDCODE = RECORDCODE;
        this.FILENAME = FILENAME;
        this.PAGENO = PAGENO;
        this.FILETYPE = FILETYPE;
        this.FILELENGTH = FILELENGTH;
        this.USERCODE = USERCODE;
        this.DEPARTMENTCODE = DEPARTMENTCODE;
        this.UPLOADTIME = UPLOADTIME;
        this.STATUS = STATUS;
        this.FONDSNO = FONDSNO;
        this.FILLINGYEAR = FILLINGYEAR;
        this.UPLOADUNIT = UPLOADUNIT;
        this.READWRITECHECK = READWRITECHECK;
        this.CHECKUNIT = CHECKUNIT;
        this.DOCUMENTCODE = DOCUMENTCODE;
        this.BINGDUJIANYAN = BINGDUJIANYAN;
        this.ZAITIJIANYAN = ZAITIJIANYAN;
        this.WANZHENGXINGJIANYA = WANZHENGXINGJIANYA;
        this.YOUXIAOJIANYAN = YOUXIAOJIANYAN;
        this.JISHUJIANYAN = JISHUJIANYAN;
        this.JIANRONGXINGJIANYAN = JIANRONGXINGJIANYAN;
        this.USESOFT = USESOFT;
        this.MAINTITLE = MAINTITLE;
        this.BEIZHU = BEIZHU;
        this.CD_MARK = CD_MARK;
        this.AUTOPDF = AUTOPDF;
        this.OSPOSITION = OSPOSITION;
        this.FILELOC = FILELOC;
        this.FTPIP = FTPIP;
        this.FTPPORT = FTPPORT;
        this.FTPUSERNAME = FTPUSERNAME;
        this.FTPPASSWORD = FTPPASSWORD;
        this.OSTYPE = OSTYPE;
        this.TRASHSTATUS = TRASHSTATUS;
        this.ARCHIVECATALOGNO = ARCHIVECATALOGNO;
        this.RETENTION = RETENTION;
        this.ORIGINALFILEPATH = ORIGINALFILEPATH;
        this.FILE_NAME_ZH_CN = FILE_NAME_ZH_CN;
        this.YUAN_WEN_SHU_LIANG = YUAN_WEN_SHU_LIANG;
        this.PDFPATH = PDFPATH;
        this.ORIGINAPATH = ORIGINAPATH;
        this.WATERMARKPATH = WATERMARKPATH;
    }

    public String getFILECODE() {
        return FILECODE;
    }

    public void setFILECODE(String FILECODE) {
        this.FILECODE = FILECODE;
    }

    public String getRECORDCODE() {
        return RECORDCODE;
    }

    public void setRECORDCODE(String RECORDCODE) {
        this.RECORDCODE = RECORDCODE;
    }

    public String getFILENAME() {
        return FILENAME;
    }

    public void setFILENAME(String FILENAME) {
        this.FILENAME = FILENAME;
    }

    public String getPAGENO() {
        return PAGENO;
    }

    public void setPAGENO(String PAGENO) {
        this.PAGENO = PAGENO;
    }

    public String getFILETYPE() {
        return FILETYPE;
    }

    public void setFILETYPE(String FILETYPE) {
        this.FILETYPE = FILETYPE;
    }

    public String getFILELENGTH() {
        return FILELENGTH;
    }

    public void setFILELENGTH(String FILELENGTH) {
        this.FILELENGTH = FILELENGTH;
    }

    public String getUSERCODE() {
        return USERCODE;
    }

    public void setUSERCODE(String USERCODE) {
        this.USERCODE = USERCODE;
    }

    public String getDEPARTMENTCODE() {
        return DEPARTMENTCODE;
    }

    public void setDEPARTMENTCODE(String DEPARTMENTCODE) {
        this.DEPARTMENTCODE = DEPARTMENTCODE;
    }

    public String getUPLOADTIME() {
        return UPLOADTIME;
    }

    public void setUPLOADTIME(String UPLOADTIME) {
        this.UPLOADTIME = UPLOADTIME;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getFONDSNO() {
        return FONDSNO;
    }

    public void setFONDSNO(String FONDSNO) {
        this.FONDSNO = FONDSNO;
    }

    public String getFILLINGYEAR() {
        return FILLINGYEAR;
    }

    public void setFILLINGYEAR(String FILLINGYEAR) {
        this.FILLINGYEAR = FILLINGYEAR;
    }

    public String getUPLOADUNIT() {
        return UPLOADUNIT;
    }

    public void setUPLOADUNIT(String UPLOADUNIT) {
        this.UPLOADUNIT = UPLOADUNIT;
    }

    public String getREADWRITECHECK() {
        return READWRITECHECK;
    }

    public void setREADWRITECHECK(String READWRITECHECK) {
        this.READWRITECHECK = READWRITECHECK;
    }

    public String getCHECKUNIT() {
        return CHECKUNIT;
    }

    public void setCHECKUNIT(String CHECKUNIT) {
        this.CHECKUNIT = CHECKUNIT;
    }

    public String getDOCUMENTCODE() {
        return DOCUMENTCODE;
    }

    public void setDOCUMENTCODE(String DOCUMENTCODE) {
        this.DOCUMENTCODE = DOCUMENTCODE;
    }

    public String getBINGDUJIANYAN() {
        return BINGDUJIANYAN;
    }

    public void setBINGDUJIANYAN(String BINGDUJIANYAN) {
        this.BINGDUJIANYAN = BINGDUJIANYAN;
    }

    public String getZAITIJIANYAN() {
        return ZAITIJIANYAN;
    }

    public void setZAITIJIANYAN(String ZAITIJIANYAN) {
        this.ZAITIJIANYAN = ZAITIJIANYAN;
    }

    public String getWANZHENGXINGJIANYA() {
        return WANZHENGXINGJIANYA;
    }

    public void setWANZHENGXINGJIANYA(String WANZHENGXINGJIANYA) {
        this.WANZHENGXINGJIANYA = WANZHENGXINGJIANYA;
    }

    public String getYOUXIAOJIANYAN() {
        return YOUXIAOJIANYAN;
    }

    public void setYOUXIAOJIANYAN(String YOUXIAOJIANYAN) {
        this.YOUXIAOJIANYAN = YOUXIAOJIANYAN;
    }

    public String getJISHUJIANYAN() {
        return JISHUJIANYAN;
    }

    public void setJISHUJIANYAN(String JISHUJIANYAN) {
        this.JISHUJIANYAN = JISHUJIANYAN;
    }

    public String getJIANRONGXINGJIANYAN() {
        return JIANRONGXINGJIANYAN;
    }

    public void setJIANRONGXINGJIANYAN(String JIANRONGXINGJIANYAN) {
        this.JIANRONGXINGJIANYAN = JIANRONGXINGJIANYAN;
    }

    public String getUSESOFT() {
        return USESOFT;
    }

    public void setUSESOFT(String USESOFT) {
        this.USESOFT = USESOFT;
    }

    public String getMAINTITLE() {
        return MAINTITLE;
    }

    public void setMAINTITLE(String MAINTITLE) {
        this.MAINTITLE = MAINTITLE;
    }

    public String getBEIZHU() {
        return BEIZHU;
    }

    public void setBEIZHU(String BEIZHU) {
        this.BEIZHU = BEIZHU;
    }

    public String getCD_MARK() {
        return CD_MARK;
    }

    public void setCD_MARK(String CD_MARK) {
        this.CD_MARK = CD_MARK;
    }

    public String getAUTOPDF() {
        return AUTOPDF;
    }

    public void setAUTOPDF(String AUTOPDF) {
        this.AUTOPDF = AUTOPDF;
    }

    public String getOSPOSITION() {
        return OSPOSITION;
    }

    public void setOSPOSITION(String OSPOSITION) {
        this.OSPOSITION = OSPOSITION;
    }

    public String getFILELOC() {
        return FILELOC;
    }

    public void setFILELOC(String FILELOC) {
        this.FILELOC = FILELOC;
    }

    public String getFTPIP() {
        return FTPIP;
    }

    public void setFTPIP(String FTPIP) {
        this.FTPIP = FTPIP;
    }

    public String getFTPPORT() {
        return FTPPORT;
    }

    public void setFTPPORT(String FTPPORT) {
        this.FTPPORT = FTPPORT;
    }

    public String getFTPUSERNAME() {
        return FTPUSERNAME;
    }

    public void setFTPUSERNAME(String FTPUSERNAME) {
        this.FTPUSERNAME = FTPUSERNAME;
    }

    public String getFTPPASSWORD() {
        return FTPPASSWORD;
    }

    public void setFTPPASSWORD(String FTPPASSWORD) {
        this.FTPPASSWORD = FTPPASSWORD;
    }

    public String getOSTYPE() {
        return OSTYPE;
    }

    public void setOSTYPE(String OSTYPE) {
        this.OSTYPE = OSTYPE;
    }

    public String getTRASHSTATUS() {
        return TRASHSTATUS;
    }

    public void setTRASHSTATUS(String TRASHSTATUS) {
        this.TRASHSTATUS = TRASHSTATUS;
    }

    public String getARCHIVECATALOGNO() {
        return ARCHIVECATALOGNO;
    }

    public void setARCHIVECATALOGNO(String ARCHIVECATALOGNO) {
        this.ARCHIVECATALOGNO = ARCHIVECATALOGNO;
    }

    public String getRETENTION() {
        return RETENTION;
    }

    public void setRETENTION(String RETENTION) {
        this.RETENTION = RETENTION;
    }

    public String getORIGINALFILEPATH() {
        return ORIGINALFILEPATH;
    }

    public void setORIGINALFILEPATH(String ORIGINALFILEPATH) {
        this.ORIGINALFILEPATH = ORIGINALFILEPATH;
    }

    public String getFILE_NAME_ZH_CN() {
        return FILE_NAME_ZH_CN;
    }

    public void setFILE_NAME_ZH_CN(String FILE_NAME_ZH_CN) {
        this.FILE_NAME_ZH_CN = FILE_NAME_ZH_CN;
    }

    public String getYUAN_WEN_SHU_LIANG() {
        return YUAN_WEN_SHU_LIANG;
    }

    public void setYUAN_WEN_SHU_LIANG(String YUAN_WEN_SHU_LIANG) {
        this.YUAN_WEN_SHU_LIANG = YUAN_WEN_SHU_LIANG;
    }

    public String getPDFPATH() {
        return PDFPATH;
    }

    public void setPDFPATH(String PDFPATH) {
        this.PDFPATH = PDFPATH;
    }

    public String getORIGINAPATH() {
        return ORIGINAPATH;
    }

    public void setORIGINAPATH(String ORIGINAPATH) {
        this.ORIGINAPATH = ORIGINAPATH;
    }

    public String getWATERMARKPATH() {
        return WATERMARKPATH;
    }

    public void setWATERMARKPATH(String WATERMARKPATH) {
        this.WATERMARKPATH = WATERMARKPATH;
    }
}
