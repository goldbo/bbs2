package cn.vcity.wbase.common.helper;
import java.io.*;

import java.util.Enumeration;
import java.util.zip.*;


public class ZipFileHelper {

	/**
	 * 解压缩文件(假设压缩文件只存在一个文件夹)
	 * @param zipFilePath    zip文件所在的文件路径
	 * @param outFolderName   输出的文件夹名称
	 * @param zipFileName    zip文件名
	 */
	public static void extZipFileList(String zipFilePath, String outFolderName,String zipFileName) {
		File oldFolder = new File(zipFilePath+outFolderName);
		if (oldFolder.exists()) {
			deleteFile(zipFilePath+outFolderName);
		}
		try {

			//使用apache解决中文文件名乱码问题
			org.apache.tools.zip.ZipFile zf =  new org.apache.tools.zip.ZipFile(zipFilePath+zipFileName);
			
			boolean b = false;
			String enen  = "";
			Enumeration entries = null;
			
			// 先创建目录
			for ( entries = zf.getEntries(); entries.hasMoreElements();) {
				org.apache.tools.zip.ZipEntry entry1 = ((org.apache.tools.zip.ZipEntry) entries.nextElement()); 
				String entryName = entry1.getName();
				if (entry1.isDirectory()) {	  	
					File file = new File(zipFilePath+entryName);
					enen = entryName;
					if (file.exists()) {
						b = true;
					} else {
						b = file.mkdirs();
					}		
				} else {
					//
				}
			
	        }  
			
			//创建文件
			if (b) {
				for (entries = zf.getEntries(); entries.hasMoreElements();) {
					org.apache.tools.zip.ZipEntry entry1 = ((org.apache.tools.zip.ZipEntry) entries
							.nextElement());
					if (!entry1.isDirectory()) {
						String entryName = entry1.getName();
						InputStream in1 = zf.getInputStream(entry1);  
						FileOutputStream os = null;

						if (!enen.equals("")) {
							os = new FileOutputStream(zipFilePath + entryName);
						}

						byte[] buf = new byte[1024];
						int len;
						while ((len = in1.read(buf)) > 0) {
							os.write(buf, 0, len);
						}
						os.close();
						in1.close();
					}
				}
			}  
			zf.close();
			
		/*	while ((entry = in.getNextEntry()) != null) {
				String entryName = entry.getName();
				if (entry.isDirectory()) {	  	
					File file = new File(zipFilePath+entryName);
					enen = entryName;
					file.mkdirs();
				} else {
					FileOutputStream os = null;
					if (!enen.equals("")) {
						os = new FileOutputStream(zipFilePath+ entryName);
					} else {
						File outFolder = new File(zipFilePath+outFolderName);   //如果没有目录则创建
						if (!outFolder.exists()) {
							outFolder.mkdirs();
						}
						os = new FileOutputStream(zipFilePath+outFolderName+"/"+ entryName);
					}
					
					//   Transfer   bytes   from   the   ZIP   file   to   the   output   file
					byte[] buf = new byte[1024];

					int len;
					while ((len = in.read(buf)) > 0) {
						os.write(buf, 0, len);
					}
					os.close();
					in.closeEntry();
				}
			}*/
			if (!enen.equals("")) {    // 如果有目录，重命名
				File rename = new File(zipFilePath+enen);
				rename.renameTo(new File(zipFilePath+outFolderName));
			}	
			System.out.println("解压文件成功 ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void deleteFile(String fileArr) {

		File file = new File(fileArr);
		if (file.exists()) {
			if (file.isFile()) {
				boolean b = file.delete();
			}
			if (file.isDirectory()) {
				File delFile[] = file.listFiles();
				for (int j = 0; j < delFile.length; j++) {
					if (delFile[j].isDirectory()) {
						deleteFile(delFile[j].getAbsolutePath()); // 递归调用del方法并取得子目录路径
					}
					delFile[j].delete();// 删除文件
				}
				file.delete();
			}
		}
	}		

	public static void deleteFiles(String[] fileArr){
		for (int i = 0; i < fileArr.length; i++) {
			deleteFile(fileArr[i]);
		}
	}

	public static void main(String[] args) throws Exception {
	
		File file = new File("E:\\Developer\\Tomcat6.0\\webapps\\bbs2\\uploadfile\\VCity\\Model\\Wingo\\test002.zip");
		//extZipFileList("E:\\Developer\\Tomcat6.0\\webapps\\bbs2\\uploadfile\\VCity\\Model\\Wingo\\", "test","三星 i909.zip");
		//deleteFile("E:\\Developer\\Tomcat6.0\\webapps\\bbs2\\uploadfile\\VCity\\Model\\Wingo\\test002.zip");
		file.delete();
	}
   

}