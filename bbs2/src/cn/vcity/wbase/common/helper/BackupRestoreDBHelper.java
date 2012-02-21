package cn.vcity.wbase.common.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

public class BackupRestoreDBHelper {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

	class ExeCom extends Thread {
		InputStream is;

		ExeCom(InputStream is) {
			this.is = is;
		}

		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line;
				while ((line = br.readLine()) != null) {
					System.err.println(line);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	/**
	 * LINUX备份DB
	 * @param savePath
	 * @param user
	 * @param password
	 * @param dbname
	 * @return
	 */
	public boolean backupDBLinux(String savePath, String user, String password,
			String dbname) {
		boolean flag = false;
		try {
//			String sqlname = savePath + dbname + "_"
//					+ sdf.format(new Date().getTime()) + ".sql";
			String excshell = "mysqldump --user=" + user + " --password="
					+ password + " --opt " + dbname + "> " + savePath;

			String[] command = new String[] { "sh", "-c", excshell };
			Process process = Runtime.getRuntime().exec(command);
			new ExeCom(process.getErrorStream()).start();
			process.waitFor();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * WINDOWS备份DB
	 * @param savePath
	 * @param user
	 * @param password
	 * @param dbname
	 * @return
	 */
	public boolean backupDBWindows(String savePath, String user, String password,
			String dbname) {
		boolean flag = false;
		try {
//			String sqlname = savePath + dbname + "_"
//					+ sdf.format(new Date().getTime()) + ".sql";
			String excshell = "mysqldump --user=" + user + " --password="
					+ password + " --opt " + dbname + "> " + savePath;

			String[] command = new String[] { "cmd", "/c", excshell };
			Process process = Runtime.getRuntime().exec(command);
			new ExeCom(process.getErrorStream()).start();
			process.waitFor();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * LINUX还原DB
	 * @param restoreSource
	 * @param user
	 * @param password
	 * @param dbname
	 * @return
	 */
	public boolean restoreDBLinux(String restoreSource, String user, String password,
			String dbname) {
		boolean flag = false;
		try {
			String excshell = "mysql --user=" + user + " --password="
					+ password + " " + dbname + "< " + restoreSource;

			String[] command = new String[] { "sh", "-c", excshell };
			Process process = Runtime.getRuntime().exec(command);
			new ExeCom(process.getErrorStream()).start();
			process.waitFor();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * WINDOWS还原DB
	 * @param restoreSource
	 * @param user
	 * @param password
	 * @param dbname
	 * @return
	 */
	public boolean restoreDBWindows(String restoreSource, String user, String password,
			String dbname) {
		boolean flag = false;
		try {
			String excshell = "mysql -u" + user + " -p"
					+ password + " " + dbname + "< " + restoreSource;

			String[] command = new String[] { "cmd", "/c", excshell };
			Process process = Runtime.getRuntime().exec(command);
			new ExeCom(process.getErrorStream()).start();
			process.waitFor();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
}
