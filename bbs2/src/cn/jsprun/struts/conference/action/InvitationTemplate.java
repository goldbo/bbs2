package cn.jsprun.struts.conference.action;

import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import cn.jsprun.utils.JspRunConfig;

public class InvitationTemplate {

	// 编写email的格式的方法
	public static String getEmailTemplate(HttpServletRequest request,
			String user, String message, String invitation_link, String type,
			String dStart, String dEnd) {

		// String filePath =
		// AppointmentAction.class.getResource("/").getPath();//AppointmentAction.class.getResource("/templates").getPath();
		String filePath = JspRunConfig.realPath + "/WEB-INF/classes/";
		String emailTamplateName = "emailInvitation.vm";
		try {
			String title = "邀请";
			if ("baoming".equals(type)) {
				title = "报名";
			} else if ("canhui".equals(type)) {
				title = "参会";
			} else if ("updateMeeting".equals(type)) {
				title = "会议更新";
			} else if ("cancelMeeting".equals(type)) {
				title = "会议取消";
				invitation_link = "";
			}
			title += "通知";

			/* lets make a Context and put data into it */
			VelocityContext context = new VelocityContext();

			context.put("title", title);// 邀请类型标题
			context.put("userName", user);// 用户名称
			context.put("message", message);// 会议消息
			context.put("invitation_link", invitation_link);// 链接
			context.put("invitation_times", "开始时间: " + dStart
					+ "&nbsp;&nbsp;预计结束时间：" + dEnd);// 时间

			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, filePath);
			ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
			ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
			ve.init();
			Template template = ve.getTemplate(emailTamplateName);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getEmailTemplate(HttpServletRequest request,
			String username, String taskName) {

		String filePath = JspRunConfig.realPath + "/WEB-INF/classes/";
		String emailTamplateName = "task.vm";
		try {
			String title = "《第一体验俱乐部》提醒：您有新的任务。";
			/* lets make a Context and put data into it */
			VelocityContext context = new VelocityContext();

			context.put("name", username);// 用户名称
			context.put("task", taskName);// 任务名

			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, filePath);
			ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
			ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
			ve.init();
			Template template = ve.getTemplate(emailTamplateName);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 专题帖邮件模板
	 * @param request
	 * @param username
	 * @param taskName
	 * @return
	 */
	public static String getEmailSpecialTemplate(HttpServletRequest request,
			String username, String taskName) {

		String filePath = JspRunConfig.realPath + "/WEB-INF/classes/";
		String emailTamplateName = "special.vm";
		try {
			/* lets make a Context and put data into it */
			VelocityContext context = new VelocityContext();

			context.put("name", username);// 用户名称
			context.put("special", taskName);// 任务名

			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, filePath);
			ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
			ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
			ve.init();
			Template template = ve.getTemplate(emailTamplateName);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
