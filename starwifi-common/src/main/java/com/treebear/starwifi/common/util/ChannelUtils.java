package com.treebear.starwifi.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 数据同步通道辅助类
 * @author DUCHONG
 * @since 2018-05-02 17:35
 **/
public class ChannelUtils {


    //文件后缀
    public static final String FILE_SUFFIX=".zip";


    /**
     *  获取交互的json字符串
     * @param sourcePath 需要同步的文件夹路径 比如：F:\\webapp
     * @param zipFileTempPath 生成的zip文件夹临时存放路径 比如：F:\\
     * @return
     */
    public static String getPostParam(String sourcePath,String zipFileTempPath){


        JSONArray arr=new JSONArray();

        boolean hasZip=false;
        List<Map<String,String>> fileList=FileScanUtils.scanDir(sourcePath);

        if(fileList.size()>0){

            for (Map<String, String> map : fileList) {

                JSONObject item=new JSONObject();
                //文件夹-->压缩-->获取hash
                if(map.get("isDir").equals("true")){


                    item.put("isDir","true");
                    String fileName=map.get("fileName");
                    try {
                        //需要zip的文件夹为：/www/
                        ZipUtils.toZip(sourcePath+File.separator+fileName,zipFileTempPath+File.separator+fileName+FILE_SUFFIX,true);
                        String fileHash=EncryptionUtils.getMD5File(new File(zipFileTempPath+File.separator+fileName+FILE_SUFFIX));
                        item.put("fileHash",fileHash);
                        hasZip=true;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else{
                    item.put("isDir","false");
                    item.put("fileHash",EncryptionUtils.getMD5File(new File(sourcePath+File.separator+map.get("fileName"))));
                }

                item.put("fileName",map.get("fileName"));

                arr.add(item);
            }
        }

        if(hasZip){
            deleteFiles(zipFileTempPath);
        }
        if(arr.size()>0){
            return arr.toJSONString();
        }

        return null;
    }

    /**
     * 组装json完成后，删除生成的临时zip文件
     */
    public static void deleteFiles(String filePath){

        File file =new File(filePath);

        if(file.exists()){

            File[] files=file.listFiles();

            if(files.length>0){

                for (File file1 : files) {

                    System.out.println("删除["+file1.getName()+"] 成功");
                    file1.delete();

                }

            }

        }
    }

    public static void main(String[] args) {

        deleteFiles("F:\\temp2");
    }


}
