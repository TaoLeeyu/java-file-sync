package com.treebear.starwifi.web.controller.image;/**
 * Created by DUCHONG on 2017/11/21.
 */

import com.treebear.starwifi.web.controller.BaseController;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 图片压缩控制器
 *
 * @author DUCHONG
 * @since 2017-11-21 11:24
 **/
public class ImagePressController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ImagePressController.class);

    public  static void pressImageByBatch(File file){
        try {
            File [] files=file.listFiles();
            for(File f:files){
                // 计算宽高
                BufferedImage bim = ImageIO.read(f);
                int srcWdith = bim.getWidth();
                int srcHeigth = bim.getHeight();
                long fileSize=f.length();
                if(fileSize<80*1024){
                    continue;
                }
                else{
                    Thumbnails.of(f)
                            .scale(0.5)//图大小按比例
                            //.size(srcWdith,srcHeigth)
                            .outputQuality(0.8)//输出图片质量
                            .outputFormat("jpg")
                            .toFiles(Rename.PREFIX_DOT_THUMBNAIL);
                            //.toFiles(Rename.NO_CHANGE);
                }
            }

        }
        catch (IOException e) {
            logger.error("ImagePressController.pressImageByBatch error",e);
        }
    }

    public static void main(String[] args) {
        pressImageByBatch(new File("D:\\PIC\\test"));
        /*getFiles("D:\\PIC\\img");

        for(String s:filelist){
            //System.out.println(s);
            pressImageByBatch(new File(s));
        }*/
    }

        private static ArrayList<String> filelist = new ArrayList<String>();

        /*
         * 通过递归得到某一路径下所有的目录及其文件
         */
        static void getFiles(String filePath){
            File root = new File(filePath);
            File[] files = root.listFiles();
            for(File file:files){
                if(file.isDirectory()){

                    getFiles(file.getAbsolutePath());
                    filelist.add(file.getAbsolutePath());

                    System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
                }else{
                    System.out.println("显示"+filePath+"下所有子目录"+file.getAbsolutePath());
                }
            }
        }
    }
