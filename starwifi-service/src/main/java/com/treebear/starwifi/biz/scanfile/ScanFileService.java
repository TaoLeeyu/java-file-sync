package com.treebear.starwifi.biz.scanfile;

import com.treebear.starwifi.common.util.ChannelUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author DUCHONG
 * @since 2018-05-03 11:28
 **/
@Service
public class ScanFileService {

    Logger logger= LoggerFactory.getLogger(ScanFileService.class);

    /**
     * 获取需要同步的文件夹的参数信息
     * @param sourcePath
     * @param tempPath
     * @return
     */
    public String getScanFileParam(String sourcePath,String tempPath){

        String result=null;
        try {
            if(StringUtils.isNotEmpty(sourcePath)&&StringUtils.isNotEmpty(tempPath)){
                result= ChannelUtils.getPostParam(sourcePath,tempPath);
            }
        }
        catch (Exception e) {
            logger.error("ScanFileService.getScanFileParam error",e);
        }

        return result;
    }
}
