package cn.vcity.wbase.common.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.vcity.wbase.common.filter.FileManagerFilter;

/**
 * 文件管理类：列表指定目录下的所有文件，删除指定目录下的文件
 * 
 * @author samuel
 * 
 */
public class FileManager {
	/**
	 * 列表所有文件 按需求改造该方法
	 */
	public static void listRoots() {
		// 将根目录存入File数组roots中
		File[] roots = File.listRoots();
		// 打印出根目录
		try {
			for (int i = 0; i < roots.length; i++) {
				System.out.println("======================================");
				System.out.println("根目录" + roots[i] + "的文件列表:");
				// System.out.println("该目录的容量为:"+roots[i].length());只有文件才有此方法才会返回字节长度,目录为0
				System.out.println("======================================\n");
				// 打印出根目录下的文件
				File[] rootfile = roots[i].listFiles();
				for (File rf : rootfile) {
					System.out.println(rf);
					// System.out.println("------------------------------------");
				}

			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 列表指定目录下的所有文件
	 */
	public static List<FileForm> listDirFiles(String path) {
		// 将目录存入File数组files中
		File dir = new File(path);
		File[] files = dir.listFiles();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<FileForm> filelist = new ArrayList<FileForm>();
		try {
			for (int i = 0; i < files.length; i++) {
				FileForm ff = new FileForm();
				ff.setFileName(files[i].getName());
				ff.setFilePath(files[i].getPath());
				ff.setCreateTime(sdf.format(files[i].lastModified()));
				filelist.add(ff);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return filelist;
	}


	/**
	 * 删除指定文件或一些文件
	 * 
	 * @param path
	 * @param inname
	 * @param inextension
	 */
	public static void deleteFiles(String path, String inname, String inextension) {
		FileManagerFilter fmf = new FileManagerFilter(inname, inextension);
		File file = new File(path);
		File[] filelist = file.listFiles(fmf);
		if (filelist.length != 0) {
			for (File fl : filelist) {
				fl.delete();
			}
		}
	}

	/**
	 * 删除指定文件或一些文件
	 * 
	 * @param path
	 * @param inname
	 * @param inextension
	 */
	public static void deleteFilesByName(String path, String inname) {
		FileManagerFilter fmf = new FileManagerFilter("*", "*");
		File file = new File(path);
		File[] filelist = file.listFiles(fmf);
		if (filelist.length != 0) {
			for (File fl : filelist) {
				if (fl.getName().equals(inname)) {
					fl.delete();
					break;
				}
			}
		}
	}

	/**
	 * 删除所有目录下所有文件,不删除目录
	 */

	@SuppressWarnings("static-access")
	public static void deletAllFile() {
		FileManager FM = new FileManager();
		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			if (roots[i].isDirectory()) {
				FM.deleteFiles(roots[i].toString(), "*", "*");
			}
		}
	}
	
	/**
	 * 创建目录
	 * @param dir
	 * @return
	 */
	public static boolean mkdirs(String dir) {
		boolean bool = true;
		try {
			/**//* 查找文件，如果不存在，就创建 */
			File fdir = new File(dir);
			if (!fdir.exists()){
				if (!fdir.mkdirs()){
					bool = false;
					throw new Exception("目录不存在，创建失败！");
				}
			}
			
		} catch (Exception e) {
			e.getMessage();
		}
		return bool;
	}

	/**
	 * 创建文件
	 * @param path
	 * @return
	 */
	public static boolean createNewFile(String path) {
		boolean bool = true;
		try {
			/**//* 查找文件，如果不存在，就创建 */
			File file = new File(path);
			if (!file.exists()){
				if (!file.createNewFile()){
					bool = false;
					throw new Exception("文件不存在，创建失败！");
				}
			}
			
		} catch (Exception e) {
			e.getMessage();
		}
		return bool;
	}

}
