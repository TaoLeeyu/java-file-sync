package com.treebear.starwifi.web.test;/**
 * Created by Administrator on 2018/5/10.
 */

import com.treebear.starwifi.common.util.HttpUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @author DUCHONG
 * @since 2018-05-10 16:46
 **/
public class ActiveMqTest {


    @Test
    public void test() {
        new Thread(new Sender()).start();
    }

    class Sender implements Runnable{

        public  AtomicInteger count = new AtomicInteger(0);

        public void run() {
            try {
                System.out.println(Thread.currentThread().getName()+"---Send message-"+count.getAndIncrement());
                String sendUrl="http://127.0.0.1:8866/sendMessage";
                Map<String,String> param=new HashMap<>();
                param.put("msg","hello kitty");

                String result= HttpUtils.sendPost(sendUrl,param);
                System.out.println("result:"+result);
                System.out.println(Thread.currentThread().getName()+"---Send message Success-"+count.getAndIncrement());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
