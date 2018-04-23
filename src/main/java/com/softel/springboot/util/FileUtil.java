package com.softel.springboot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileUtil {

	public static void createFile(String path) throws IOException{
		Long aa = 429458251663584116l;
    	File file = new File(path);
    	if(!file.exists()){
    		file.createNewFile();
    	}
    	FileInputStream fis = null;  
        InputStreamReader isr = null;  
        BufferedReader br = null;  
        FileOutputStream fos = null;  
        PrintWriter pw = null;  
        try {  
            // 将文件读入输入流  
            fis = new FileInputStream(file);  
            isr = new InputStreamReader(fis);  
            br = new BufferedReader(isr);  
            StringBuffer buf = new StringBuffer();  
            // 保存该文件原有的内容  
            for (int j = 0; j < 1000; j++) {
            	aa+=j;
            	System.out.println(aa);
                buf = buf.append(aa.toString());  
                buf = buf.append(System.getProperty("line.separator"));  
            }  
            fos = new FileOutputStream(file);  
            pw = new PrintWriter(fos);  
            pw.write(buf.toString().toCharArray());  
            pw.flush();  
        } catch (IOException e1) {  
            
        } finally {  
            if (pw != null) {  
                pw.close();  
            }  
            if (fos != null) {  
                fos.close();  
            }  
            if (br != null) {  
                br.close();  
            }  
            if (isr != null) {  
                isr.close();  
            }  
            if (fis != null) {  
                fis.close();  
            }  
        }
	}
	
	public static void main(String[] args) throws IOException{
		createFile("F:/IDCard.txt");
    }
}
