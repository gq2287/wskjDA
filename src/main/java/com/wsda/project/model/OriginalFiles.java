package com.wsda.project.model;

/**
 * 原文实体
 */
public class OriginalFiles {
    private String fileCode;//原文编号
    private String recordCode;//记录编号
    private String fileName;//原文名称
    private String pageNo;//页号
    private String fileType;//文件类型
    private String fileLength;//文件长度
    private String userCode;//用户编号
    private String departmentCode;//用户部门编号
    private String uploadTime;//上传时间
    private String status;//状态
    private String fondsNo;//全宗号
    private String fillingYear;//归档年度
    private String uploadUnit;//上传单位
    private String readWriteCheck;//读取检验
    private String checkUnit;//检验人
    private String documentCode;//稿本代号
    private String bingdujianyan;//病毒检验
    private String zaiTiJianYan;//载体检验
    private String wanZhengXingJianYa;//完整性检验
    private String youxiaojianyan;//有效检验
    private String jiShuJianYan;//技术检验
    private String jianRongXingJianYan;//兼容性检验
    private String usesoft;//所用软件
    private String mainTitle;//标题
    private String beiZhu;//备注
    private String CD_MARK;//光盘标记
    private String autoPdf;//自动PDF
    private String osPosition;//本地主目录
    private String fileLoc;//FTP 目录
    private String ftpIp;//FTP 地址
    private String ftpPort;//FTP 端口号
    private String ftpuserName;//FTP 服务器用户名
    private String ftppassWord;//FTP 密码
    private String osType;//存储类型        1本地,2ftp
    private String trashStatus;//回收站     0默认,1垃圾站
    private String archivecataLogNo;//存档日志编号
    private String retention;//保留
    private String FILE_NAME_CH_CN;//文件中文名称
    private String YUAN_WEN_SHU_LIANG;//原文数量
    private String pdfPath;//pdf路径
    //    private String originaPath;//原文存放路径
    private String originalFilePath;//原始文件路径
    private String watermarkPath;//水印pdf存放路径
    private String top;//置顶状态0默认显示 1置顶显示

    public OriginalFiles() {
    }

    public OriginalFiles(String fileCode, String recordCode, String fileName, String pageNo, String fileType, String fileLength, String userCode, String departmentCode, String uploadTime, String status, String fondsNo, String fillingYear, String uploadUnit, String readWriteCheck, String checkUnit, String documentCode, String bingdujianyan, String zaiTiJianYan, String wanZhengXingJianYa, String youxiaojianyan, String jiShuJianYan, String jianRongXingJianYan, String usesoft, String mainTitle, String beiZhu, String CD_MARK, String autoPdf, String osPosition, String fileLoc, String ftpIp, String ftpPort, String ftpuserName, String ftppassWord, String osType, String trashStatus, String archivecataLogNo, String retention, String originalFilePath, String FILE_NAME_CH_CN, String YUAN_WEN_SHU_LIANG, String pdfPath, String watermarkPath) {
        this.fileCode = fileCode;
        this.recordCode = recordCode;
        this.fileName = fileName;
        this.pageNo = pageNo;
        this.fileType = fileType;
        this.fileLength = fileLength;
        this.userCode = userCode;
        this.departmentCode = departmentCode;
        this.uploadTime = uploadTime;
        this.status = status;
        this.fondsNo = fondsNo;
        this.fillingYear = fillingYear;
        this.uploadUnit = uploadUnit;
        this.readWriteCheck = readWriteCheck;
        this.checkUnit = checkUnit;
        this.documentCode = documentCode;
        this.bingdujianyan = bingdujianyan;
        this.zaiTiJianYan = zaiTiJianYan;
        this.wanZhengXingJianYa = wanZhengXingJianYa;
        this.youxiaojianyan = youxiaojianyan;
        this.jiShuJianYan = jiShuJianYan;
        this.jianRongXingJianYan = jianRongXingJianYan;
        this.usesoft = usesoft;
        this.mainTitle = mainTitle;
        this.beiZhu = beiZhu;
        this.CD_MARK = CD_MARK;
        this.autoPdf = autoPdf;
        this.osPosition = osPosition;
        this.fileLoc = fileLoc;
        this.ftpIp = ftpIp;
        this.ftpPort = ftpPort;
        this.ftpuserName = ftpuserName;
        this.ftppassWord = ftppassWord;
        this.osType = osType;
        this.trashStatus = trashStatus;
        this.archivecataLogNo = archivecataLogNo;
        this.retention = retention;
        this.originalFilePath = originalFilePath;
        this.FILE_NAME_CH_CN = FILE_NAME_CH_CN;
        this.YUAN_WEN_SHU_LIANG = YUAN_WEN_SHU_LIANG;
        this.pdfPath = pdfPath;
        this.watermarkPath = watermarkPath;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileLength() {
        return fileLength;
    }

    public void setFileLength(String fileLength) {
        this.fileLength = fileLength;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFondsNo() {
        return fondsNo;
    }

    public void setFondsNo(String fondsNo) {
        this.fondsNo = fondsNo;
    }

    public String getFillingYear() {
        return fillingYear;
    }

    public void setFillingYear(String fillingYear) {
        this.fillingYear = fillingYear;
    }

    public String getUploadUnit() {
        return uploadUnit;
    }

    public void setUploadUnit(String uploadUnit) {
        this.uploadUnit = uploadUnit;
    }

    public String getReadWriteCheck() {
        return readWriteCheck;
    }

    public void setReadWriteCheck(String readWriteCheck) {
        this.readWriteCheck = readWriteCheck;
    }

    public String getCheckUnit() {
        return checkUnit;
    }

    public void setCheckUnit(String checkUnit) {
        this.checkUnit = checkUnit;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getBingdujianyan() {
        return bingdujianyan;
    }

    public void setBingdujianyan(String bingdujianyan) {
        this.bingdujianyan = bingdujianyan;
    }

    public String getZaiTiJianYan() {
        return zaiTiJianYan;
    }

    public void setZaiTiJianYan(String zaiTiJianYan) {
        this.zaiTiJianYan = zaiTiJianYan;
    }

    public String getWanZhengXingJianYa() {
        return wanZhengXingJianYa;
    }

    public void setWanZhengXingJianYa(String wanZhengXingJianYa) {
        this.wanZhengXingJianYa = wanZhengXingJianYa;
    }

    public String getYouxiaojianyan() {
        return youxiaojianyan;
    }

    public void setYouxiaojianyan(String youxiaojianyan) {
        this.youxiaojianyan = youxiaojianyan;
    }

    public String getJiShuJianYan() {
        return jiShuJianYan;
    }

    public void setJiShuJianYan(String jiShuJianYan) {
        this.jiShuJianYan = jiShuJianYan;
    }

    public String getJianRongXingJianYan() {
        return jianRongXingJianYan;
    }

    public void setJianRongXingJianYan(String jianRongXingJianYan) {
        this.jianRongXingJianYan = jianRongXingJianYan;
    }

    public String getUsesoft() {
        return usesoft;
    }

    public void setUsesoft(String usesoft) {
        this.usesoft = usesoft;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getBeiZhu() {
        return beiZhu;
    }

    public void setBeiZhu(String beiZhu) {
        this.beiZhu = beiZhu;
    }

    public String getCD_MARK() {
        return CD_MARK;
    }

    public void setCD_MARK(String CD_MARK) {
        this.CD_MARK = CD_MARK;
    }

    public String getAutoPdf() {
        return autoPdf;
    }

    public void setAutoPdf(String autoPdf) {
        this.autoPdf = autoPdf;
    }

    public String getOsPosition() {
        return osPosition;
    }

    public void setOsPosition(String osPosition) {
        this.osPosition = osPosition;
    }

    public String getFileLoc() {
        return fileLoc;
    }

    public void setFileLoc(String fileLoc) {
        this.fileLoc = fileLoc;
    }

    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpuserName() {
        return ftpuserName;
    }

    public void setFtpuserName(String ftpuserName) {
        this.ftpuserName = ftpuserName;
    }

    public String getFtppassWord() {
        return ftppassWord;
    }

    public void setFtppassWord(String ftppassWord) {
        this.ftppassWord = ftppassWord;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getTrashStatus() {
        return trashStatus;
    }

    public void setTrashStatus(String trashStatus) {
        this.trashStatus = trashStatus;
    }

    public String getArchivecataLogNo() {
        return archivecataLogNo;
    }

    public void setArchivecataLogNo(String archivecataLogNo) {
        this.archivecataLogNo = archivecataLogNo;
    }

    public String getRetention() {
        return retention;
    }

    public void setRetention(String retention) {
        this.retention = retention;
    }

    public String getOriginalFilePath() {
        return originalFilePath;
    }

    public void setOriginalFilePath(String originalFilePath) {
        this.originalFilePath = originalFilePath;
    }

    public String getFILE_NAME_CH_CN() {
        return FILE_NAME_CH_CN;
    }

    public void setFILE_NAME_CH_CN(String FILE_NAME_CH_CN) {
        this.FILE_NAME_CH_CN = FILE_NAME_CH_CN;
    }

    public String getYUAN_WEN_SHU_LIANG() {
        return YUAN_WEN_SHU_LIANG;
    }

    public void setYUAN_WEN_SHU_LIANG(String YUAN_WEN_SHU_LIANG) {
        this.YUAN_WEN_SHU_LIANG = YUAN_WEN_SHU_LIANG;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getWatermarkPath() {
        return watermarkPath;
    }

    public void setWatermarkPath(String watermarkPath) {
        this.watermarkPath = watermarkPath;
    }
}
