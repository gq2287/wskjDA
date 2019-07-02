package com.wsda.project.util;

import org.hyperic.sigar.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @author anly
 */
public class CPUUtil {
//sigar全名是System Information
//Gatherer And
//Reporter，Sigar是Hyperic-hq产品的基础包,
//是Hyperic HQ主要的数据收集组件。它用来从许多平台收集系统和处理信息.
// 这些平台包括：Linux,Windows,Solaris,AIX,HP-UX,FreeBSD and Mac OSX.
//Sigar可以获得系统的如下介个方面的信息：
//1.操作系统的信息，包括：dataModel、cpuEndian、name、version、arch、machine、description、patchLevel、vendor、vendorVersion、vendorName、vendorCodeName
//2.CPU信息，包括：基本信息（vendor、model、mhz、cacheSize）和统计信息（user、sys、idle、nice、wait）
//3.内存信息，物理内存和交换内存的总数、使用数、剩余数；RAM的大小
//4.进程信息，包括每个进程的内存、CPU占用数、状态、参数、句柄等。
//5.文件系统信息，包括名称、容量、剩余数、使用数、分区类型等
//6.网络接口信息，包括基本信息和统计信息。
//7.网络路由和链接表信息。
    private  static Logger logger = LoggerFactory.getLogger(CPUUtil.class);

    /**
     * 服务器信息
     *
     * @return
     */
    public static  Map<String, Object> serverInfo() {
        Properties props = System.getProperties();
        Map<String, String> map = System.getenv();
        Map<String, Object> serviceInfo = new HashMap<>();
        serviceInfo.put("server.user.name(用户名)", map.get("USERNAME")); //用户名
        serviceInfo.put("server.computer.name(计算机名)", map.get("COMPUTERNAME")); //计算机名
        serviceInfo.put("server.computer.domain(计算机域名)", map.get("USERDOMAIN")); //计算机域名
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            serviceInfo.put("server.ip(本机ip)", addr.getHostAddress()); //本机ip
            serviceInfo.put("server.host.name(本机主机名)", addr.getHostName()); //本机主机名
            serviceInfo.put("server.user.home(用户的主目录)", props.getProperty("user.home")); //用户的主目录
            serviceInfo.put("server.user.dir(用户的当前工作目录)", props.getProperty("user.dir")); //用户的当前工作目录
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return serviceInfo;
    }


    /**
     * 系统信息
     *
     * @return
     */
    public static  Map<String, Object> systemInfo() {
        OperatingSystem OS = OperatingSystem.getInstance();
        Map<String, Object> SystemInfo = new HashMap<>();
        SystemInfo.put("os.name(操作系统名称)", OS.getVendorName()); //操作系统名称
        SystemInfo.put("os.arch(内核构架)", OS.getArch()); //内核构架
        SystemInfo.put("os.description(操作系统的描述)", OS.getDescription()); //操作系统的描述
        SystemInfo.put("os.version(操作系统的版本号)", OS.getVersion()); //操作系统的版本号
        return SystemInfo;
    }


    /**
     * CPU信息
     *
     * @return
     * @throws SigarException
     */
    public static  Map<String, Object> cpuInfo() throws SigarException {
        Sigar sigar = new Sigar();
        CpuInfo infos[] = sigar.getCpuInfoList();//获取cpu详情
        CpuPerc cpuList[] = sigar.getCpuPercList();//当前核心
        Map<String, Object> cpuInfo = new HashMap<>();
        List<Object> cpuArray = new ArrayList<>();
        for (int i = 0, len = infos.length; i < len; i++) {// 不管是单块CPU还是多CPU都适用
            CpuInfo info = infos[i];
            Map<String, Object> jso = new HashMap<>();
            jso.put("mhz(CPU的总量MHz)", info.getMhz()); //CPU的总量MHz
            jso.put("company(CPU的厂商)", info.getVendor()); //CPU的厂商
            jso.put("model(CPU型号类别)", info.getModel()); //CPU型号类别
            jso.put("cache.size(缓冲缓存数量)", info.getCacheSize()); // 缓冲缓存数量

            CpuPerc cpu = cpuList[i];
            jso.put("freq.user(CPU的用户使用率)", CpuPerc.format(cpu.getUser())); //CPU的用户使用率
            jso.put("freq.sys(CPU的系统使用率)", CpuPerc.format(cpu.getSys())); //CPU的系统使用率
            jso.put("freq.wait(CPU的当前等待率)", CpuPerc.format(cpu.getWait())); //CPU的当前等待率
            jso.put("freq.nice(CPU的当前错误率)", CpuPerc.format(cpu.getNice())); //CPU的当前错误率
            jso.put("freq.idle(CPU的当前空闲率)", CpuPerc.format(cpu.getIdle())); //CPU的当前空闲率
            jso.put("freq.combined(CPU总的使用率)", CpuPerc.format(cpu.getCombined())); //CPU总的使用率

            cpuArray.add(jso);
        }
        cpuInfo.put("CPU", cpuArray);
        return cpuInfo;
    }


    /**
     * JVM信息
     *
     * @return
     * @throws UnknownHostException
     */
    public static  Map<String, Object> jvmInfo() throws UnknownHostException {

        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        Map<String, Object> jvmInfo = new HashMap<>();

        jvmInfo.put("jvm.memory.total(JVM可以使用的总内存)", r.totalMemory()); //JVM可以使用的总内存
        jvmInfo.put("jvm.memory.free(JVM可以使用的剩余内存)", r.freeMemory()); //JVM可以使用的剩余内存
        jvmInfo.put("jvm.processor.avaliable(JVM可以使用的处理器个数)", r.availableProcessors()); //JVM可以使用的处理器个数
        jvmInfo.put("jvm.java.version(Java的运行环境版本)", props.getProperty("java.version")); //Java的运行环境版本
        jvmInfo.put("jvm.java.vendor(Java的运行环境供应商)", props.getProperty("java.vendor")); //Java的运行环境供应商
        jvmInfo.put("jvm.java.home(Java的安装路径)", props.getProperty("java.home")); //Java的安装路径
        jvmInfo.put("jvm.java.specification.version(Java运行时环境规范版本)", props.getProperty("java.specification.version")); //Java运行时环境规范版本
        jvmInfo.put("jvm.java.class.path(Java的类路径)", props.getProperty("java.class.path")); //Java的类路径
        jvmInfo.put("jvm.java.library.path(Java加载库时搜索的路径列表)", props.getProperty("java.library.path")); //Java加载库时搜索的路径列表
        jvmInfo.put("jvm.java.io.tmpdir(默认的临时文件路径)", props.getProperty("java.io.tmpdir")); //默认的临时文件路径
        jvmInfo.put("jvm.java.ext.dirs(扩展目录的路径)", props.getProperty("java.ext.dirs")); //扩展目录的路径
        return jvmInfo;

    }


    /**
     * 内存信息
     * @return
     * @throws SigarException
     */
    public static  Map<String, Object> memoryInfo() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        Map<String, Object> memoryInfo = new HashMap<>();

        memoryInfo.put("memory.total(内存总量)", mem.getTotal() / (1024 * 1024L));// 内存总量
        memoryInfo.put("memory.used(当前内存使用量)", mem.getUsed() / (1024 * 1024L));// 当前内存使用量
        memoryInfo.put("memory.free(当前内存剩余量)", mem.getFree() / (1024 * 1024L));// 当前内存剩余量

        Swap swap = sigar.getSwap();
        memoryInfo.put("memory.swap.total(交换区总量)", swap.getTotal() / (1024 * 1024L));// 交换区总量
        memoryInfo.put("memory.swap.used(当前交换区使用量)", swap.getUsed() / (1024 * 1024L));// 当前交换区使用量
        memoryInfo.put("memory.swap.free(当前交换区剩余量)", swap.getFree() / (1024 * 1024L));// 当前交换区剩余量
        return memoryInfo;

    }


    /**
     * 磁盘文件信息
     *@return
     * @throws SigarException
     */
    public  static  Map<String, Object> fileSystemInfo() throws SigarException {
        Sigar sigar = new Sigar();
        FileSystem fslist[] = sigar.getFileSystemList();
        Map<String, Object> fileSystemInfo = new HashMap<>();
        List<Object> fileSystemList = new ArrayList<>();

        for (int i = 0, len = fslist.length; i < len; i++) {
            FileSystem fs = fslist[i];
            Map<String, Object> jso = new HashMap<>();
            jso.put("dev.name(分区盘符名称)", fs.getDevName()); //分区盘符名称
            jso.put("dir.name(分区盘符名称)", fs.getDirName()); //分区盘符名称
            jso.put("flags(分区盘符类型)", fs.getFlags()); //分区盘符类型
            jso.put("sys.type.name(文件系统类型)", fs.getSysTypeName()); //文件系统类型
            jso.put("type.name(分区盘符类型名)", fs.getTypeName()); //分区盘符类型名
            jso.put("type(分区盘符文件系统类型)", fs.getType()); //分区盘符文件系统类型
            FileSystemUsage usage = null;
            try {
                usage = sigar.getFileSystemUsage(fs.getDirName());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            if (usage == null) {
                continue;
            }
            switch (fs.getType()) {
                case 0: // TYPE_UNKNOWN ：未知
                    break;
                case 1: // TYPE_NONE
                    break;
                case 2: // TYPE_LOCAL_DISK : 本地硬盘
                    jso.put("usage.totle(分区总大小)", usage.getTotal() / 1024); // 分区总大小
                    jso.put("usage.free(分区剩余大小)", usage.getFree() / 1024); // 分区剩余大小
                    jso.put("usage.avail(分区可用大小)", usage.getAvail() / 1024); // 分区可用大小
                    jso.put("usage.used(分区已经使用量)", usage.getUsed() / 1024); // 分区已经使用量
                    jso.put("usage.use.percent(分区资源的利用率)", usage.getUsePercent() * 100D); // 分区资源的利用率
                    break;
                case 3:// TYPE_NETWORK ：网络

                    break;

                case 4:// TYPE_RAM_DISK ：闪存

                    break;

                case 5:// TYPE_CDROM ：光驱

                    break;

                case 6:// TYPE_SWAP ：页面交换

                    break;

            }

            jso.put("disk.reads(读出)", usage.getDiskReads()); // 读出

            jso.put("disk.writes(写入)", usage.getDiskWrites()); // 写入

            fileSystemList.add(jso);

        }
        fileSystemInfo.put("file.system", fileSystemList);
        return fileSystemInfo;
    }


    /**
     * 网络信息
     * @return
     * @throws SigarException
     */
    public static Map<String, Object> netInfo() throws SigarException {
        Sigar sigar = new Sigar();
        String ifNames[] = sigar.getNetInterfaceList();
        Map<String, Object> netInfo = new HashMap<>();
        List<Object> netList = new ArrayList<>();

        for (int i = 0, len = ifNames.length; i < len; i++) {
            String name = ifNames[i];
            Map<String, Object> jso = new HashMap<>();
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            jso.put("name", name); // 网络设备名
            jso.put("address", ifconfig.getAddress()); // IP地址
            jso.put("mask", ifconfig.getNetmask()); // 子网掩码
            if ((ifconfig.getFlags() & 1L) <= 0L) {
                logger.info("!IFF_UP...skipping getNetInterfaceStat");
                continue;
            }

            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
            jso.put("rx.packets(接收的总包裹数)", ifstat.getRxPackets());// 接收的总包裹数
            jso.put("tx.packets(发送的总包裹数)", ifstat.getTxPackets());// 发送的总包裹数
            jso.put("rx.bytes(接收到的总字节数)", ifstat.getRxBytes());// 接收到的总字节数
            jso.put("tx.bytes(发送的总字节数)", ifstat.getTxBytes());// 发送的总字节数
            jso.put("rx.errors(接收到的错误包数)", ifstat.getRxErrors());// 接收到的错误包数
            jso.put("tx.errors(发送数据包时的错误数)", ifstat.getTxErrors());// 发送数据包时的错误数
            jso.put("rx.dropped(接收时丢弃的包数)", ifstat.getRxDropped());// 接收时丢弃的包数
            jso.put("tx.dropped(发送时丢弃的包数)", ifstat.getTxDropped());// 发送时丢弃的包数
            netList.add(jso);

        }
        netInfo.put("net", netList);
        return netInfo;
    }


    /**
     * 以太网信息
     * @return
     * @throws SigarException
     */
    public static Map<String, Object> ethernetInfo() throws SigarException {
        Sigar sigar = new Sigar();
        String[] ifaces = sigar.getNetInterfaceList();
        Map<String, Object> ethernetInfo = new HashMap<>();
        List<Object> ethernetList = new ArrayList<>();

        for (int i = 0, len = ifaces.length; i < len; i++) {
            NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
            if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0 || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
                continue;
            }
            Map<String, Object> jso = new HashMap<>();
            jso.put("address(IP地址)", cfg.getAddress());// IP地址
            jso.put("broad.cast(网关广播地址)", cfg.getBroadcast());// 网关广播地址
            jso.put("hwaddr(网卡MAC地址)", cfg.getHwaddr());// 网卡MAC地址
            jso.put("net.mask(子网掩码)", cfg.getNetmask());// 子网掩码
            jso.put("description(网卡描述信息)", cfg.getDescription());// 网卡描述信息
            jso.put("type(网卡类型)", cfg.getType());// 网卡类型
            ethernetList.add(jso);
        }
        ethernetInfo.put("ethernet", ethernetList);
        return ethernetInfo;
    }



}  