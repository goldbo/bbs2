package cn.jsprun.struts.foreg.actions;

//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.DataInputStream;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.util.List;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.struts.action.BaseAction;
//import cn.jsprun.utils.JspRunConfig;

public class OnlineAction extends BaseAction {
	public ActionForward doexecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		String sql = "select max(votess),tid,reporttype,subject from (select t.fid, po.tid,sum(votes) as votess,p.reporttype,t.subject from jrun_threads t inner join jrun_polls p on t.tid=p.tid inner join jrun_polloptions po on t.tid=po.tid  where t.displayorder>=0  group by t.tid order by votes desc )  temp  group by fid limit 0,3";
		List<Map<String,String>> poll=dataBaseService.executeQuery(sql);
		request.setAttribute("poll",poll);
        request.setAttribute("addrName",request.getServerName()+":"+request.getServerPort()+request.getContextPath());
        request.setAttribute("addrNameopen",request.getServerName()+":"+request.getServerPort());
		return mapping.findForward("onlinehome");
	}

//	public void writexml() {
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder = null;
//		try {
//			builder = factory.newDocumentBuilder();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Document document = builder.newDocument();
//		Element bcaster = document.createElement("bcaster");
//		bcaster.setAttribute("autoPlayTime", 3 + "");
//		List<Image> list = imageService.getAllImage(1);
//		if (list != null && list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				Image image = list.get(i);
//				Element item = document.createElement("item");
//				item.setAttribute("item_url", image.getImagePath());
//				item.setAttribute("link", image.getUrl());
//				item.setAttribute("itemtitle", "啊啊啊");
//				System.out.println(image.getRemark());
//				bcaster.appendChild(item);
//				if (i >= 3)
//					break;
//			}
//		}
//		document.appendChild(bcaster);
//		try {
//			TransformerFactory tf = TransformerFactory.newInstance();
//			Transformer transformer = tf.newTransformer();
//			DOMSource source = new DOMSource(document);
//			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
//			PrintWriter pw = new PrintWriter(new FileOutputStream(JspRunConfig.realPath + "/OnlineHome/inc/bcastr.xml"));
//			StreamResult result = new StreamResult(pw);
//			transformer.transform(source, result);
//		} catch (TransformerException mye) {
//			mye.printStackTrace();
//		} catch (IOException exp) {
//			exp.printStackTrace();
//		}
//		String content="";
//		try {
//			content=this.getContent(JspRunConfig.realPath + "/OnlineHome/inc/bcastr.xml", "utf-8");
//			this.createFile(JspRunConfig.realPath + "/OnlineHome/inc/bcastr.xml", content, "utf-8");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void createFile(String file, String content, String encodType)
//			throws IOException {
//		FileOutputStream writerStream = new FileOutputStream(file);
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
//				writerStream, encodType));
//		writer.write(content);
//		writer.close();
//	}
//
//	public String getContent(String file, String encodType)
//			throws IOException {
//		// "xx\r\n" read -> "xx"
//		StringBuffer content = new StringBuffer();
//		FileInputStream fis = new FileInputStream(file);
//		DataInputStream dis = new DataInputStream(fis);
//		BufferedReader br = new BufferedReader(new InputStreamReader(dis,
//				encodType));
//		String line = null;
//		if ((line = br.readLine()) != null) {
//			content.append(line);
//		}
//		while ((line = br.readLine()) != null) {
//			content.append("\r\n" + line);
//		}
//		br.close();
//		dis.close();
//		fis.close();
//		return content.toString();
//	}

}
