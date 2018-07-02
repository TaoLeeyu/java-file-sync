package com.treebear.starwifi.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author DUCHONG
 * @since 2018-05-02 9:18
 **/
public class EncryptionUtils {

    public static String base64Encode(String data){

        return Base64.encodeBase64String(data.getBytes());

    }

    public static byte[] base64Decode(String data){

        return Base64.decodeBase64(data.getBytes());

    }

    public static String md5(String data) {

        return DigestUtils.md5Hex(data);

    }

    public static String sha1(String data) {

        return DigestUtils.shaHex(data);

    }

    public static String sha256Hex(String data) {

        return DigestUtils.sha256Hex(data);

    }

    public static String getMD5File(File file){
        FileInputStream fis=null;
        try {
            fis=new FileInputStream(file);
            return DigestUtils.md5Hex(fis);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis != null){
                try {
                    fis.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static void main(String[] args) {


        //long start= System.currentTimeMillis();

        System.out.println(getMD5File(new File("F:\\temp\\WEB-INF.zip")));
        System.out.println(getMD5File(new File("F:\\temp2\\WEB-INF.zip")));


        //long end=System.currentTimeMillis();

        //System.out.println("共耗时："+(float)(end-start)/1000+"s");
    }
}
