package com.treebear.starwifi.common.util;/**
 * Created by Administrator on 2018/5/2.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author DUCHONG
 * @since 2018-05-02 16:28
 **/
public class ZipUtils {

    private static final int  BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩成ZIP
     * @param srcDir  需要压缩的文件夹路径
     * @param outDir  生成的压缩文件夹的路径
     * @param keepDirOrder  是否保留原来的目录结构,true:保留目录结构;
     * 							false:所有文件跑到压缩包根目录下
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, String outDir, boolean keepDirOrder)
            throws Exception{

        long start = System.currentTimeMillis();

        File sourceFile = new File(srcDir);
        FileOutputStream out=new FileOutputStream(outDir);
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            System.out.println("开始压缩文件夹["+sourceFile+"]--->["+outDir+"]");
            compress(sourceFile,zos,sourceFile.getName(),keepDirOrder);
            long end = System.currentTimeMillis();
            System.out.println("文件夹 ["+sourceFile+"] 压缩完毕！耗时：" + (end - start) +"ms");

        }
        catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }
        finally{
            if(zos != null){
                try {
                    zos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out != null){
                try {
                    out.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos		 zip输出流
     * @param name		 压缩后的名称 比如 F:\\webapp.zip
     * @param keepDirOrder  是否保留原来的目录结构,true:保留目录结构;
     * 							false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean keepDirOrder) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];
        //单个文件直接压缩
        if(sourceFile.isFile()){
            FileInputStream in=null;
            try {
                // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
                ZipEntry zipEntry =new ZipEntry(name);
                //设置同一个时间，防止两次生成的zip文件md5值不一致
                zipEntry.setTime(1);
                zos.putNextEntry(zipEntry);
                // copy文件到zip输出流中
                int len;
                in = new FileInputStream(sourceFile);
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
                // Complete the entry
                zos.closeEntry();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(in !=null){
                    try {
                        in.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else {

            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(keepDirOrder){
                    // 空文件夹的处理
                    ZipEntry zipEntry=new ZipEntry(name + "/");
                    //设置同一个时间，防止两次生成的zip文件md5值不一致
                    zipEntry.setTime(1);
                    zos.putNextEntry(zipEntry);
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }

            }
            else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (keepDirOrder) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(),keepDirOrder);
                    }
                    else {
                        compress(file, zos, file.getName(),keepDirOrder);
                    }

                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        //long start= System.currentTimeMillis();

        ZipUtils.toZip("F:\\webapp","F:\\webapp.zip",true);

        //long end=System.currentTimeMillis();

        //System.out.println("共耗时："+(float)(end-start)/1000+"s");

    }
}
