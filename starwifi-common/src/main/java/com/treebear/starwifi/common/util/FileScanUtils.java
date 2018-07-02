package com.treebear.starwifi.common.util;/**
 * Created by Administrator on 2018/4/30.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DUCHONG
 * @since 2018-04-30 13:20
 **/
public class FileScanUtils {

    Logger logger= LoggerFactory.getLogger(FileScanUtils.class);


    /**
     * 添加所有的文件到list中
     * @param list
     * @param path 文件夹路径 比如：F:\\webapp
     */
    public  void scanFile(List list,String path){

        File root=new File(path);

        if(root.exists()){

            File[] files = root.listFiles();

            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            }
            else {

                for (File file : files) {

                    if (file.isDirectory()) {

                        System.out.println(Thread.currentThread().getName()+"开始扫秒文件夹："+file.getAbsolutePath());

                        scanFile(list,file.getAbsolutePath());
                    }
                    else {

                        list.add(file.getAbsoluteFile());
                    }
                }

            }
        }
        else{
            System.out.println("文件路径不存在");
        }

    }

    /**
     * 获取文件夹下的所有文件，并标记是文件还是目录
     * @param path
     * @return
     */
    public static List<Map<String,String>> scanDir(String path){

        List<Map<String,String>> list=new ArrayList<>();

        File root=new File(path);

        if(root.exists()){

            File [] files=root.listFiles();

            if(files.length>0){

                for (File file : files) {

                    Map<String,String> map=new HashMap<>();

                    if(file.isDirectory()){
                        map.put("isDir","true");
                    }
                    else{
                        map.put("isDir","false");
                    }
                    map.put("fileName",file.getName());

                    list.add(map);
                }
            }

        }
        else{
            System.out.println("文件路径不存在");
        }

        return list;
    }


    public static void main(String[] args) {

//        List<String> fileNameList=new ArrayList<>();
//
//        long startTime= System.currentTimeMillis();
//
//        FileScanUtils fileScanUtil=new FileScanUtils();
//
//        fileScanUtil.scanFile(fileNameList,"F:\\webapp");
//
//        long endTime= System.currentTimeMillis();
//
//        System.out.println("共"+fileNameList.size()+"个文件，共耗时："+(float)(endTime-startTime)/1000+"s");

        List list=scanDir("F:\\webapp");
        System.out.println(list);
    }
}
