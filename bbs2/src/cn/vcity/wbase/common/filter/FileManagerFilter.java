package cn.vcity.wbase.common.filter;

import java.io.File;
import java.io.FilenameFilter;
/**
 * 文件过滤器：用于过滤文件
 * @author samuel
 *
 */
public class FileManagerFilter implements FilenameFilter {
	private String name;

	private String extension;

	public FileManagerFilter(String name, String extension) {
		this.name = name;
		this.extension = extension;

	}

	public boolean accept(File dir, String filename) {
		boolean fileOK = true;
		if ("*".equals(name) && "*".equals(extension)) {
			return fileOK = true;

		}
		if (name != null) {
			// 不大解理"&="的运行过程,
			fileOK &= filename.startsWith(name);

		}
		if (extension != null) {
			fileOK &= filename.endsWith('.' + extension);
		}

		return fileOK;
	}

}
