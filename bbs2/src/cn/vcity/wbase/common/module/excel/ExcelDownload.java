package cn.vcity.wbase.common.module.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @Title: ExcelDownload.java
 * @Package cn.vcity.wbase.common.module.excel
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-5-11 上午11:09:12
 * @version V1.0
 */
public class ExcelDownload {

	/**
	 * 在已文件已存在的情况下，采用读取文件流的方式实现左键点击下载功能， 本系统没有采取这个方法,而是直接将数据传往输出流,效率更高。
	 * 
	 * @param inPutFileName
	 *            读出的文件名
	 * @param outPutFileName
	 *            保存的文件名
	 * @param HttpServletResponse
	 * @see HttpServletResponse
	 * @throws IOException
	 */
	public void downLoad(String inPutFileName, String outPutFileName,
			HttpServletResponse response) throws IOException {

		// path是指欲下载的文件的路径。
		File file = new File(inPutFileName);
		// 取得文件名。
		String filename = file.getName();
		// 取得文件的后缀名。
		// String ext = filename.substring(filename.lastIndexOf(".") + 1)
		// .toUpperCase();

		// 以流的形式下载文件。
		InputStream fis = new BufferedInputStream(new FileInputStream(
				inPutFileName));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		// 清空response
		response.reset();
		// 设置response的Header
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(filename.getBytes()));
		response.addHeader("Content-Length", "" + file.length());
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		os.write(buffer);
		os.flush();
		os.close();
		response.flushBuffer();
	}

	/**
	 * 在文件不存在的情况下，采用生成输出流的方式实现左键点击下载功能。
	 * 
	 * @param outPutFileName
	 *            保存的文件名
	 * @param out
	 *            ServletOutputStream对象
	 * @param downExcel
	 *            填充了数据的ExcelModel
	 * @param HttpServletResponse
	 * @see HttpServletResponse
	 * @throws Exception
	 */
	public void downLoad(String outPutFileName, HSSFWorkbook workbook,
			HttpServletResponse response) throws Exception {

		// 取得输出流
		OutputStream os = response.getOutputStream();
		// 清空输出流
		response.reset();

		// 设置响应头和下载保存的文件名
		response.setHeader("content-disposition", "attachment;filename="
				+ outPutFileName);
		// 定义输出类型
		response.setContentType("application/octet-stream");

		// os:传入的输出流
		workbook.write(os);
		os.flush();
		os.close();
		// 这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
		response.flushBuffer(); // 强行将响应缓存中的内容发送到目的地

	}

	/**
	 * 不保存到磁盘，进行读流下载EXCEL文件
	 * 
	 * @param templateFilePath
	 *            模板文件路径
	 * @param outPutFileName
	 *            输出下载的文件名
	 * @param map
	 *            数据MAP
	 * @param response
	 * @throws Exception
	 */
	public void downLoad(String templateFilePath, String outPutFileName,
			Map map, HttpServletResponse response) throws Exception {
		// 取得输出流
		OutputStream os = response.getOutputStream();
		// 清空输出流
		response.reset();
		// 设置响应头和下载保存的文件名
		response.setHeader("content-disposition", "attachment;filename="
				+ outPutFileName);
		// 定义输出类型
		response.setContentType("application/octet-stream");

		InputStream fis = new BufferedInputStream(new FileInputStream(
				templateFilePath));

		XLSTransformer transformer = new XLSTransformer();
		try {

			HSSFWorkbook workbook = transformer.transformXLS(fis, map);
			workbook.write(os);
			fis.close();
			os.flush();
			os.close();
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
