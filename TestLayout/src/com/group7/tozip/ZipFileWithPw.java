package com.group7.tozip;

import java.io.File;

/*import com.training.file.FileUtils;
import com.ytd.encrypt.extend.ZipOutput2Flex;*/

import nochump.util.extend.ZipOutput;  

import com.training.commons.file.FileUtils;  
  
public class ZipFileWithPw {  
	
    public void upToZip(String zipdir, String encryptzipfile , String password){
    	String ZipDir =zipdir;
        String EncryptZipFile =  encryptzipfile;
        String PassWord = password;  
          
//        System.out.println("=====Encrypt=====");  
        File file = new File(ZipDir);  
        byte[] zipByte = ZipOutput.getEncryptZipByte(file.listFiles(), PassWord);  
        FileUtils.writeByteFile(zipByte, new File(EncryptZipFile));  
//        System.out.println("===== Encrypt Success =====");  
    	
    }
}  