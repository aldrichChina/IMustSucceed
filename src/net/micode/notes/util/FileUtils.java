package net.micode.notes.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.SyncStateContract.Constants;
import android.util.Log;

/**
 * @fileName FileUtils.java
 * @package com.immomo.momo.android.util
 * @description 文件工具类
 * @author 任东卫
 * @email 86930007@qq.com
 * @version 1.0
 */
public class FileUtils {
    
    
    public static final long B = 1;  
    public static final long KB = B * 1024;  
    public static final long MB = KB * 1024;  
    public static final long GB = MB * 1024;  
    private static final int BUFFER = 8192;  
    /** 
     * 格式化文件大小<b> 带有单位 
     *  
     * @param size 
     * @return 
     */  
    public static String formatFileSize(long size) {  
        StringBuilder sb = new StringBuilder();  
        String u = null;  
        double tmpSize = 0;  
        if (size < KB) {  
            sb.append(size).append("B");  
            return sb.toString();  
        } else if (size < MB) {  
            tmpSize = getSize(size, KB);  
            u = "KB";  
        } else if (size < GB) {  
            tmpSize = getSize(size, MB);  
            u = "MB";  
        } else {  
            tmpSize = getSize(size, GB);  
            u = "GB";  
        }  
        return sb.append(twodot(tmpSize)).append(u).toString();  
    }  
    /**
     * 换算文件大小
     * 
     * @param size
     * @return
     */
    /*public static String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "未知大小";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }*/
    /** 
     * 保留两位小数 
     *  
     * @param d 
     * @return 
     */  
    public static String twodot(double d) {  
        return String.format("%.2f", d);  
    }  
  
    public static double getSize(long size, long u) {  
        return (double) size / (double) u;  
    }  
  
    /** 
     * sd卡挂载且可用 
     *  
     * @return 
     */  
    public static boolean isSdCardMounted() {  
        return android.os.Environment.getExternalStorageState().equals(  
                android.os.Environment.MEDIA_MOUNTED);  
    }  
  
    /** 
     * 递归创建文件目录 
     *  
     * @param path 
     * */  
    public static void CreateDir(String path) {  
        if (!isSdCardMounted())  
            return;  
        File file = new File(path);  
        if (!file.exists()) {  
            try {  
                file.mkdirs();  
            } catch (Exception e) {  
                Log.e("hulutan", "error on creat dirs:" + e.getStackTrace());  
            }  
        }  
    }  
  
    /** 
     * 读取文件 
     *  
     * @param file 
     * @return 
     * @throws IOException 
     */  
    public static String readTextFile(File file) throws IOException {  
        String text = null;  
        InputStream is = null;  
        try {  
            is = new FileInputStream(file);  
            text = readTextInputStream(is);;  
        } finally {  
            if (is != null) {  
                is.close();  
            }  
        }  
        return text;  
    }  
  
    /** 
     * 从流中读取文件 
     *  
     * @param is 
     * @return 
     * @throws IOException 
     */  
    public static String readTextInputStream(InputStream is) throws IOException {  
        StringBuffer strbuffer = new StringBuffer();  
        String line;  
        BufferedReader reader = null;  
        try {  
            reader = new BufferedReader(new InputStreamReader(is));  
            while ((line = reader.readLine()) != null) {  
                strbuffer.append(line).append("\r\n");  
            }  
        } finally {  
            if (reader != null) {  
                reader.close();  
            }  
        }  
        return strbuffer.toString();  
    }  
  
    /** 
     * 将文本内容写入文件 
     *  
     * @param file 
     * @param str 
     * @throws IOException 
     */  
    public static void writeTextFile(File file, String str) throws IOException {  
        DataOutputStream out = null;  
        try {  
            out = new DataOutputStream(new FileOutputStream(file));  
            out.write(str.getBytes());  
        } finally {  
            if (out != null) {  
                out.close();  
            }  
        }  
    }  
  
    /** 
     * 将Bitmap保存本地JPG图片 
     * @param url 
     * @return 
     * @throws IOException 
     */  
    public static String saveBitmap2File(String url) throws IOException {  
  
        BufferedInputStream inBuff = null;  
        BufferedOutputStream outBuff = null;  
  
        SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");  
        String timeStamp = sf.format(new Date());  
//        File targetFile = new File(Constants.ENVIROMENT_DIR_SAVE, timeStamp + ".jpg");
        File targetFile = new File(Constants._COUNT, timeStamp + ".jpg");  
        File oldfile = ImageLoader.getInstance().getDiscCache().get(url);  
        try {  
  
            inBuff = new BufferedInputStream(new FileInputStream(oldfile));  
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));  
            byte[] buffer = new byte[BUFFER];  
            int length;  
            while ((length = inBuff.read(buffer)) != -1) {  
                outBuff.write(buffer, 0, length);  
            }  
            outBuff.flush();  
            return targetFile.getPath();  
        } catch (Exception e) {  
  
        } finally {  
            if (inBuff != null) {  
                inBuff.close();  
            }  
            if (outBuff != null) {  
                outBuff.close();  
            }  
        }  
        return targetFile.getPath();  
    }  
  
    /** 
     * 读取表情配置文件 
     *  
     * @param context 
     * @return 
     */  
    public static List<String> getEmojiFile(Context context) {  
        try {  
            List<String> list = new ArrayList<String>();  
            InputStream in = context.getResources().getAssets().open("emoji");// 文件名字为rose.txt  
            BufferedReader br = new BufferedReader(new InputStreamReader(in,  
                    "UTF-8"));  
            String str = null;  
            while ((str = br.readLine()) != null) {  
                list.add(str);  
            }  
  
            return list;  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * 获取一个文件夹大小 
     *  
     * @param f 
     * @return 
     * @throws Exception 
     */  
    public static long getFileSize(File f) {  
        long size = 0;  
        File flist[] = f.listFiles();  
        for (int i = 0; i < flist.length; i++) {  
            if (flist[i].isDirectory()) {  
                size = size + getFileSize(flist[i]);  
            } else {  
                size = size + flist[i].length();  
            }  
        }  
        return size;  
    }  
  
    /** 
     * 删除文件 
     *  
     * @param file 
     */  
    public static void deleteFile(File file) {  
  
        if (file.exists()) { // 判断文件是否存在  
            if (file.isFile()) { // 判断是否是文件  
                file.delete(); // delete()方法 你应该知道 是删除的意思;  
            } else if (file.isDirectory()) { // 否则如果它是一个目录  
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];  
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件  
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代  
                }  
            }  
            file.delete();  
        }  
    }  

	/**
	 * 判断SD是否可以
	 * 
	 * @return
	 */
	public static boolean isSdcardExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 创建根目录
	 * 
	 * @param path
	 *            目录路径
	 */
	public static void createDirFile(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 *            文件路径
	 * @return 创建的文件
	 */
	public static File createNewFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				return null;
			}
		}
		return file;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹的路径
	 */
	public static void delFolder(String folderPath) {
		delAllFile(folderPath);
		String filePath = folderPath;
		filePath = filePath.toString();
		java.io.File myFilePath = new java.io.File(filePath);
		myFilePath.delete();
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            文件的路径
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
			}
		}
	}

	/**
	 * 获取文件的Uri
	 * 
	 * @param path
	 *            文件的路径
	 * @return
	 */
	public static Uri getUriFromFile(String path) {
		File file = new File(path);
		return Uri.fromFile(file);
	}

	
}
