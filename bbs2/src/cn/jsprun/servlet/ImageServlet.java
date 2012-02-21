package cn.jsprun.servlet;

import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import cn.jsprun.service.DataBaseService;
import cn.jsprun.utils.BeanFactory;

public class ImageServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ImageServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	protected DataBaseService dataBaseService = (DataBaseService) BeanFactory
			.getBean("dataBaseService");

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String type = request.getParameter("type");
		String subject=request.getParameter("subject");
		
		 subject=new String(subject.getBytes( "iso-8859-1" ), "UTF-8" );   ;
		String tid=request.getParameter("tid");
		String sqlbingtu = "select votes,polloption from jrun_polloptions po where tid="+tid;
		List<Map<String, String>> list = dataBaseService.executeQuery(sqlbingtu);
		OutputStream out = null;
		try {
			
			out = response.getOutputStream();

			if (list != null && list.size() > 0) {
				Map<String,String> first=list.get(0);
				int width = 300;
				int height = 250;
				JFreeChart chart = null;
				if (type.equals("1")) {
					chart = this.createPie(list,subject);
					chart.getTitle().setFont(new Font("黑体", Font.BOLD, 20));// 设置标题字体
					PiePlot piePlot = (PiePlot) chart.getPlot();// 获取图表区域对象
					piePlot.setLabelFont(new Font("黑体", Font.BOLD, 10));
					chart.getLegend()
							.setItemFont(new Font("黑体", Font.BOLD, 10));
				} else if (type.equals("2")) {
					width = 330;
					height = 280;
					chart = this.createBar(list,subject);
					CategoryPlot plot = chart.getCategoryPlot();// 获取图表区域对象
					CategoryAxis domainAxis = plot.getDomainAxis();
					NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
					// 水平底部列表
					domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14));
					// 水平底部标题
					domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));
					// 垂直标题
					ValueAxis rangeAxis = plot.getRangeAxis();// 获取柱状
					rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));

					TextTitle textTitle = chart.getTitle();
					textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
					domainAxis.setTickLabelFont(new Font("sans-serif",
							Font.PLAIN, 11));
					domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
					numberaxis.setTickLabelFont(new Font("sans-serif",
							Font.PLAIN, 12));
					numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));
					chart.getLegend().setItemFont(
							new Font("宋体", Font.PLAIN, 12));

				} else if(type.equals("3")){
					width = 330;
					height = 280;
					chart = this.createHORIZONTALBar(list,subject);
					CategoryPlot plot = chart.getCategoryPlot();// 获取图表区域对象
					CategoryAxis domainAxis = plot.getDomainAxis();
					NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
					// 水平底部列表
					domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14));
					// 水平底部标题
					domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));
					// 垂直标题
					ValueAxis rangeAxis = plot.getRangeAxis();// 获取柱状
					rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));
					TextTitle textTitle = chart.getTitle();
//					if(textTitle!=null){
						textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
//					}
					domainAxis.setTickLabelFont(new Font("sans-serif",
							Font.PLAIN, 11));
					domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
					numberaxis.setTickLabelFont(new Font("sans-serif",
							Font.PLAIN, 12));
					numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));
					chart.getLegend().setItemFont(
							new Font("宋体", Font.PLAIN, 12));
				}

				response.setContentType("image/png");
				try {
					ChartUtilities.writeChartAsPNG(out, chart, width, height);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private JFreeChart createPie(List<Map<String, String>> list,String title) {
		DefaultPieDataset data = new DefaultPieDataset();
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			data.setValue(map.get("polloption"), new Double("".equals(map
					.get("votes")) ? "0" : map.get("votes")));
		}
		JFreeChart chart = ChartFactory.createPieChart(title, data, true, true,
				false);
		return chart;
	}

	private JFreeChart createBar(List<Map<String, String>> list,String title) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			title = map.get("subject");
			dataset.addValue(new Double("".equals(map.get("votes")) ? "0"
					: map.get("votes")), map.get("polloption"), map
					.get("polloption"));
		}

		JFreeChart chart = ChartFactory.createBarChart3D(title, "", "",
				dataset, PlotOrientation.VERTICAL, true, true, false);
		return chart;
	}

	private JFreeChart createHORIZONTALBar(List<Map<String, String>> list,String title) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			dataset.addValue(new Double("".equals(map.get("votes")) ? "0"
					: map.get("votes")), map.get("polloption"), map
					.get("polloption"));
		}

		JFreeChart chart = ChartFactory.createBarChart3D(title, "", "",
				dataset, PlotOrientation.HORIZONTAL, true, true, false);
		return chart;
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
