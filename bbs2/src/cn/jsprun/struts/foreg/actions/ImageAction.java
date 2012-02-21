package cn.jsprun.struts.foreg.actions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;

import cn.jsprun.domain.Image;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FileUploadUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Md5Token;

public class ImageAction extends BaseAction {
	public final static String FILEPATHTIME = JspRunConfig.realPath
			+ "forumdata/temp";
	public final static int memeoryBlock = 1572864;

	public ActionForward doFindAllImage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String submitimg = request.getParameter("submitimg");
		if (submitimg != null) {

		} else {
			List<Image> list = imageService.getAllImage(0);
			if (list != null) {
				request.setAttribute("imglist", list);
			} else {
				request.setAttribute("imglist", null);
			}
		}
		return mapping.findForward("jiaodiantulist");
	}

	public ActionForward doAddImg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		File tempfile = isMakeDir();
		ModuleConfig ac = (ModuleConfig) request
				.getAttribute(Globals.MODULE_KEY);
		FileUploadUtil fileupload = new FileUploadUtil(tempfile, memeoryBlock,
				ac);
		try {
			fileupload.parse(request, JspRunConfig.CHARSET);
		} catch (IllegalStateException e) {
			request.setAttribute("message", getMessage(request,
					"post_attachment_toobig"));
			return mapping.findForward("message");
		}
		if (fileupload.getParameter("addsubmit") != null) {

			String id = fileupload.getParameter("imgid");
			String url = fileupload.getParameter("url");

			if (!"".equals(url)) {
				Pattern p = Pattern
						.compile("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
				Matcher m = p.matcher(url);
				boolean isfind = m.find();
				if (!isfind) {
					request.setAttribute("message", "链接地址错误");
					request.setAttribute("imgerror", "jfkldsf");
					return mapping.findForward("message");
				}
			}
			String remark = fileupload.getParameter("remark");
			String imgtype = fileupload.getParameter("imgtype");
			Image img = new Image();
			if (!id.equals("-1")) {
				img.setId(Integer.parseInt(id));
			}
			img.setImageType(Integer.parseInt(imgtype));
			img.setRemark(remark);
			img.setStatus(1);
			img.setUrl(url);
			if (imgtype.equals("1")) {
				FileItem fileItem = fileupload.getFileItem("filepic");
				String result = this.uploadImage(request, fileItem);
				if ("-1".equals(id)) {
					if (result.equals("no")) {
						request.setAttribute("message", "没有上传图片");
						request.setAttribute("imgerror", "jfkldsf");
						return mapping.findForward("message");
					} else if (result.equals("error")) {
						request.setAttribute("message", "请上传图片文件");
						request.setAttribute("imgerror", "jfkldsf");
						return mapping.findForward("message");
					} else if (result.equals("fail")) {
						request.setAttribute("message", "图片上传失败");
						request.setAttribute("imgerror", "jfkldsf");
						return mapping.findForward("message");
					}
					img.setImagePath(result);
				} else {
					if (result.equals("error")) {
						request.setAttribute("message", "请上传图片文件");
						request.setAttribute("imgerror", "jfkldsf");
						return mapping.findForward("message");
					} else if (result.equals("fail")) {
						request.setAttribute("message", "图片上传失败");
						request.setAttribute("imgerror", "jfkldsf");
						return mapping.findForward("message");
					} else if (!result.equals("no")) {
						img.setImagePath(result);
						File file = new File(servlet.getServletContext()
								.getRealPath("/")
								+ fileupload.getParameter("impath"));
						if (file.exists()) {
							file.delete();
						}
					} else {
						img.setImagePath(fileupload.getParameter("impath"));
					}
				}

			} else {
				String picurl = fileupload.getParameter("picurl");
				if ("".equals(picurl)) {
					request.setAttribute("img", img);
					request.setAttribute("message", "请输入图片的网络地址");
					request.setAttribute("imgerror", "jfkldsf");
					return mapping.findForward("message");
				} else {
					img.setImagePath(picurl);
				}
			}
			img.setStatus(Integer.parseInt(fileupload.getParameter("status")));

			boolean isadd = imageService.addImage(img);
			if (isadd) {
				try {
					request.getRequestDispatcher(
							"admincp.jsp?action=jiaodiantulist").forward(
							request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			} else {
				request.setAttribute("message",
						"-1".equals(id) == true ? "添加失败" : "修改失败");
				return mapping.findForward("message");
			}
		} else {
			String imgid = request.getParameter("imgid");
			if (imgid != null) {
				Image img = imageService.getImageById(Integer.parseInt(imgid));
				if (img != null) {
					request.setAttribute("img", img);
				}
			}
			return mapping.findForward("addjiaodiantu");
		}
	}

	public ActionForward doDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String[] id = request.getParameterValues("id[]");
		if (id == null || id.length == 0) {
			request.setAttribute("message", "请选择要删除的数据");
			request.setAttribute("imgerror", "jfkldsf");
			return mapping.findForward("message");
		}
		for (String imgid : id) {
			int iid = Integer.parseInt(imgid);
			Image img = imageService.getImageById(iid);
			File file = new File(servlet.getServletContext().getRealPath("/")
					+ img.getImagePath());
			if (file.exists()) {
				file.delete();
			}
			imageService.deleteImage(iid);
		}
		try {
			request.getRequestDispatcher("admincp.jsp?action=jiaodiantulist")
					.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private File isMakeDir() {
		File file = new File(FILEPATHTIME);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	private String uploadImage(HttpServletRequest request, FileItem fileItem) {
		String result = "";
		Map<String, String> settings = ForumInit.settings;
		String timeoffset = settings.get("timeoffset");
		String attachurl = settings.get("attachurl");
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		if (fileItem != null && fileItem.getSize() > 0) {
			String filename = fileItem.getName();
			int start = filename.lastIndexOf('\\');
			filename = filename.substring(start + 1);
			int last = filename.lastIndexOf(".");
			String filetype = filename.substring(last + 1);
			filetype = filetype.toLowerCase();
			if (filetype.matches("jpg|jpeg|gif|png|bmp")) {
				String targetname = Common.gmdate("yyyyMMdd", timestamp,
						timeoffset)
						+ Md5Token.getInstance()
								.getLongToken(
										filename
												+ Math.rint(System
														.currentTimeMillis()))
						+ "." + filetype;
				String filedir = "month_"
						+ Common.gmdate("yyMM", timestamp, timeoffset);
				filedir = "day_"
						+ Common.gmdate("yyMMdd", timestamp, timeoffset);
				String filesubdir = servlet.getServletContext()
						.getRealPath("/")
						+ "./" + attachurl + "/" + filedir;
				File subdirfile = new File(filesubdir);
				if (!subdirfile.exists()) {
					subdirfile.mkdir();
				}
				String targetpath = servlet.getServletContext()
						.getRealPath("/")
						+ attachurl + "/" + filedir + "/" + targetname;
				if (!FileUploadUtil.write2file(fileItem, new File(targetpath))) {
					return "fail";
				}
				result = attachurl + "/" + filedir + "/" + targetname;
			} else {
				result = "error";
			}
		} else {
			result = "no";
		}
		return result;
	}
}
