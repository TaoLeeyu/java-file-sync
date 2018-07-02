package com.treebear.starwifi.web.controller.generatehtml;

import com.treebear.starwifi.common.util.GenerateHtmlUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DUCHONG
 * @since 2018-04-28 18:51
 **/
@Controller
public class GenerateController {

    @Value("${filePath}")
    private String filePath;

    @Value("${templatePath}")
    private String templatePath;

    @ResponseBody
    @RequestMapping(value = "/html",method = RequestMethod.GET)
    public String generateHtml(){


        //一般这里是数据库查出的记录的list,然后遍历list,逐个生成html，存放路径，一般是取 "配置+表字段值"，作为存放的路径
        //这里用for代替
        for(int i=1;i<10;i++){

            //页面要展示的数据
            Map<String,Object> map=new HashMap<>();
            map.put("title","news"+i);
            map.put("userId",i);
            map.put("userName","test"+i);
            map.put("mobile","18106519020");
            map.put("email","1427222829@qq.com");

            GenerateHtmlUtils.generateHtmlByVelocity("news"+i,filePath,templatePath,map,"list");
        }

        return "Over";
    }

}
