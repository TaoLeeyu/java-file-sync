package com.treebear.starwifi.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DUCHONG
 * @since 2018-05-04 10:57
 **/
public class LinuxCmdUtils {

    static Logger logger= LoggerFactory.getLogger(LinuxCmdUtils.class);

    public  static boolean executeLinuxCmd(String cmd) {

        boolean result=false;

        System.out.println("got cmd : " + cmd);
        Runtime run = Runtime.getRuntime();
        //InputStream in=null;
        try {
            Process process = run.exec(cmd);
//            in = process.getInputStream();
//            //记录cmd 输出 如果费正常结束，打印执行结果
//            BufferedReader read = new BufferedReader(new InputStreamReader(in));
//            StringBuffer sb=new StringBuffer();
//            String line = null;
//            while((line = read.readLine())!=null){
//                sb.append(line).append("\n");
//            }
            //执行结果 0 表示正常退出
            int exeResult=process.waitFor();
            if(exeResult==0){
                System.out.println("执行成功");
                result=true;
            }

        }
        catch (Exception e) {
            logger.error("LinuxCmdUtils.executeLinuxCmd error",e);
        }
        return result;
    }

}
