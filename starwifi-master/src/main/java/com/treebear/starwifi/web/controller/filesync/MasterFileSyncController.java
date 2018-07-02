package com.treebear.starwifi.web.controller.filesync;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.treebear.starwifi.biz.scanfile.ScanFileService;
import com.treebear.starwifi.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author DUCHONG
 * @since 2018-05-03 15:17
 **/
@Controller
public class MasterFileSyncController extends BaseController {

    Logger logger= LoggerFactory.getLogger(MasterFileSyncController.class);

    public static final String FILE_SYNC_PATH="/file_sync";

    @Autowired
    private ScanFileService scanFileService;
    /**
     * 需要同步的文件夹路径
     */
    @Value("${sourcePath}")
    private String sourcePath;

    /**
     * zip文件临时存放路径，同步文件夹下如果有子目录，需要先生成zip
     */
    @Value("${zipFileTempPath}")
    private String zipFileTempPath;

    @ResponseBody
    @RequestMapping(value = FILE_SYNC_PATH,method = RequestMethod.POST)
    public String getSyncFileList(String json){

        //System.out.println("接受到的参数："+json);

        JSONArray resultArray=new JSONArray();

        String masterFile= scanFileService.getScanFileParam(sourcePath,zipFileTempPath);

        if(StringUtils.isNotEmpty(json) && StringUtils.isNotEmpty(masterFile)){

            //从post的json串
            JSONArray httpArray= JSON.parseArray(json) ;
            //主的json串
            JSONArray masterArray= JSON.parseArray(masterFile);
            //修改和新增
            resultArray=getMasterModifyAndNew(resultArray,masterArray,httpArray);
            //主删除
            resultArray=getMasterDelete(resultArray,masterArray,httpArray);

        }

        return resultArray.toJSONString();
    }

    /**
     * 获取master新增或者修改
     * @param resultArray 结果集 json数组
     * @param masterArray 主服务目录集 json数组
     * @param httpArray   http post 过来的从服务目录集合，json数组
     * @return
     */
    public JSONArray getMasterModifyAndNew(JSONArray resultArray,JSONArray masterArray,JSONArray httpArray){

        for (int i = 0; i < masterArray.size(); i++) {

            JSONObject master=(JSONObject)masterArray.get(i);
            String masterFileName=master.getString("fileName");
            String masterFileHash=master.getString("fileHash");
            String masterIsDir=master.getString("isDir");

            boolean isNew=true;
            for (int j = 0; j < httpArray.size(); j++) {

                JSONObject slave=(JSONObject)httpArray.get(j);
                String slaveFileName=slave.getString("fileName");
                String slaveFileHash=slave.getString("fileHash");

                if(masterFileName.equals(slaveFileName)){

                    if(!masterFileHash.equals(slaveFileHash)){

                        JSONObject re=new JSONObject();
                        re.put("fileName",masterFileName);
                        re.put("isDir",masterIsDir);

                        resultArray.add(re);

                    }
                    isNew=false;
                }

            }
            //主新增 从没有
            if(isNew){

                JSONObject re=new JSONObject();
                re.put("fileName",masterFileName);
                re.put("isDir",masterIsDir);

                resultArray.add(re);
            }

        }

        return resultArray;
    }

    /**
     * 获取master删除的
     * @param resultArray 结果集 json数组
     * @param masterArray 主服务目录集 json数组
     * @param httpArray   http post 过来的从服务目录集合，json数组
     * @return
     */
    public JSONArray getMasterDelete(JSONArray resultArray,JSONArray masterArray,JSONArray httpArray){

        for (int i = 0; i < httpArray.size(); i++) {

            JSONObject slave=(JSONObject)httpArray.get(i);
            String slaveFileName=slave.getString("fileName");
            String slaveIsDir=slave.getString("isDir");

            boolean isDelete=true;
            for (int j = 0; j < masterArray.size(); j++) {

                JSONObject master=(JSONObject)masterArray.get(j);
                String masterFileName=master.getString("fileName");

                if(masterFileName.equals(slaveFileName)){
                    isDelete=false;
                }

            }
            //主删除 从没有
            if(isDelete){

                JSONObject re=new JSONObject();
                re.put("fileName",slaveFileName);
                re.put("isDir",slaveIsDir);

                resultArray.add(re);
            }

        }

        return resultArray;
    }
}
