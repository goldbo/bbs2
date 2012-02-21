package cn.vcity.wbase.common.base;

import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.struts.action.ActionForm;

import cn.vcity.wbase.common.conveter.DateConvert;

/**
 * Struts实现的ActionForm基类，该类继承了ActionForm.<br/>
 * 
 * @author 罗留庭 EN:Aly.Luo，开发部-珠海市网佳科技有限公司. Create-Time: 2007-9-8.
 * @version 1.0
 * @see org.apache.struts.validator.ActionForm
 * 
 */
public class BaseForm extends ActionForm {
	
	/**
	 * 注册专有转换器
	 */
	static{
		//ConvertUtils.register(new CurrencyConvert(),Double.class);
		ConvertUtils.register(new DateConvert(),Date.class);
		
	}
	/**
	 * todo 传入EDIT页面时的必传参数.
	 * update 表示修改。
	 * show/view 表示查看
	 * create 表示新增
	 */
	private String todo = "";
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	 


}
