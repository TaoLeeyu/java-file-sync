package com.treebear.starwifi.common.util;


import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

/**
 * @author DUCHONG
 * @since 2018-04-28 18:35
 **/
public class GenerateHtmlUtils {

   static Logger logger = LoggerFactory.getLogger(GenerateHtmlUtils.class);


    /**
     * 通过velocity 模板生成静态HTML 文件
     * @param fileName 生成的文件的文件名称
     * @param filePath 保存文件位置
     * @param templatePath velocity模板文件路径
     * @param params 集合
     * @param pageName 页面上需要便利或者使用的变量，可以为任意值
     */
    public  static void generateHtmlByVelocity(String fileName, String filePath,
                                      String templatePath, Object params, String pageName){


        String finalFilePath=filePath+ File.separator+fileName+".html";

        try {

            //设置加载模版文件的方式，在classpath 下面查找
            Properties p = new Properties();
            p.put("file.resource.loader.class",
                    "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            Velocity.init(p);

            FileOutputStream fos = new FileOutputStream(finalFilePath);
            BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(
                    fos, "utf8"));
            Template velocity_template = Velocity.getTemplate(templatePath,"utf8");

            VelocityContext context = new VelocityContext();
            context.put(pageName, params);
            velocity_template.merge(context,writer);
            writer.close();

        }
        catch (Exception e) {
            logger.error("文件路径失败！",e);
        }
    }


}
