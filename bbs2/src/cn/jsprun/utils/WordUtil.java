package cn.jsprun.utils;

import java.io.*;
import java.util.*;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.util.LittleEndian;

public class WordUtil {
	public WordUtil() {
	}

	public static boolean writeWordFile(String path, String content) {
		boolean w = false;
		try {

			// byte b[] = content.getBytes("ISO-8859-1");
			byte b[] = content.getBytes();

			ByteArrayInputStream bais = new ByteArrayInputStream(b);

			POIFSFileSystem fs = new POIFSFileSystem();
			DirectoryEntry directory = fs.getRoot();

			DocumentEntry de = directory.createDocument("WordDocument", bais);

			FileOutputStream ostream = new FileOutputStream(path);

			fs.writeFilesystem(ostream);

			bais.close();
			ostream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return w;
	}

	public static void main(String[] args) {
		boolean b = writeWordFile("E://test.doc", "hello");
	}
}
