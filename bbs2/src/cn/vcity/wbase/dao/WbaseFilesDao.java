package cn.vcity.wbase.dao;

import java.io.File;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.vcity.wbase.common.base.BaseDAOHibernate;

public class WbaseFilesDao extends BaseDAOHibernate {
	private static final Log log = LogFactory.getLog(WbaseFilesDao.class);

	public boolean delFromDb(String fileName) {
		boolean flag = false;
		return flag;
	}

	// 文件夹文件删除
	public boolean delFromDisk(String fileName) {
		boolean flag = false;
		try {
			final File file = new File(fileName);
			if (file.exists()) {
				file.delete();
				flag = true;
			}
		} catch (final NullPointerException e) {
			log.error("删除文件失败");
			flag = false;
		}
		return flag;
	}
}
