package com.treebear.starwifi.biz.autoscan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.treebear.starwifi.biz.scanfile.ScanFileService;
import com.treebear.starwifi.common.util.HttpUtils;
import com.treebear.starwifi.common.util.LinuxCmdUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DUCHONG
 * @since 2018-05-03 15:02
 **/
@Service
public class AutoScanFileForSyncService {

    Logger logger= LoggerFactory.getLogger(AutoScanFileForSyncService.class);

    @Autowired
    private ScanFileService scanFileService;
    /**
     * 需要同步的文件夹路径
     */
    @Value("${sourcePath}")
    private String sourcePath;

    /**
     * 需要同步到的目标文件夹路径
     */
    @Value("${targetPath}")
    private String targetPath;

    /**
     * zip文件临时存放路径，同步文件夹下如果有子目录，需要先生成zip
     */
    @Value("${zipFileTempPath}")
    private String zipFileTempPath;
    /**
     * 主服务的请求路径
     */
    @Value("${url}")
    private String url;
    /**
     * 单个文件的同步命令
     */
    @Value("${rsync.cmd.single}")
    private String cmdSingle;

    /**
     * 多个文件的同步命令
     */
    @Value("${rsync.cmd.double}")
    private String cmdDouble;

    /**
     * 目标主机ip
     */
    @Value("${rsync.cmd.target.ip}")
    private String cmdTargetIp;

    /**
     * 定时任务的实际执行方法
     */
    public void autoScan(){

        try {
            long start=System.currentTimeMillis();

            String result= scanFileService.getScanFileParam(sourcePath,zipFileTempPath);
            //System.out.println(result);
            if(StringUtils.isNotEmpty(result)){

                Map<String,String> map=new HashMap();
                map.put("json", result);
                String http_result= HttpUtils.sendPost(url,map);
                if(StringUtils.isNotEmpty(http_result)){
                    long end=System.currentTimeMillis();
                    System.out.println("http请求共耗时："+(float)(end-start)/1000+"s");

                    JSONArray re= JSON.parseArray(http_result);
                    if(re.size()>0){
                        for (Object o : re) {
                            JSONObject obj=(JSONObject) o;

                            String fileName="/"+obj.getString("fileName");
                            String cmd=null;
                            if(obj.getString("isDir").equals("true")){
                                //rsync -rcv /usr/local/nginx/html root@192.168.75.129:/usr/local/nginx/html 多文件
                                cmd=cmdDouble+" "+sourcePath+fileName+" "+cmdTargetIp+targetPath;
                            }
                            else{
                                //rsync -avz /usr/local/nginx/html root@192.168.75.129:/usr/local/nginx/html 单个文件
                                cmd=cmdSingle+" "+sourcePath+fileName+" "+cmdTargetIp+targetPath;
                            }
                            //执行
                            boolean cmdResult= LinuxCmdUtils.executeLinuxCmd(cmd);
                            if(cmdResult){
                                System.out.println("同步成功！");
                            }
                            else{
                                System.out.println("同步失败！");
                            }
                        }
                    }
                }
                else{
                    System.out.println("http请求结果为空！");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
