package cn.vcity.web.form;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import cn.vcity.model.VcityQuestionitem;
import cn.vcity.model.VcityResType;
import cn.vcity.model.VcitySubTask;
import cn.vcity.wbase.common.base.BaseForm;

/**
 * 虚拟资源管理的form,用于接收页面数据
 * @author 黄金波  huangjinbo@wingotech.com
 * @see com.wingo.wbase.common.base.BaseForm
 * @version 1.0
 * @time:2011-7-2 110:08:20
 *
 */
public class VcityResourceForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8817165661360490937L;

	private Integer id;

	private String resName;

	private String resDesc;

	private Integer senceId;

	private Integer resType;

	private String resImage;
	
	private VcityResType vcityResType; 
	
	private String recommendImage;

	private String moduleFileName;

	private String resURL;

	private Integer isRecommend;
	
	private Integer productType;
	
	private String senceName;
	
	private String[] selectedRow;
	
	private FormFile imageFile;
	private FormFile modelFile;
	private FormFile recommendImageFile;
	private String flag = "";

	private String questionIds;
	
	private String userName;
    private int taskId;


	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
	/*******************************/
	private Map subTaskMap = new HashMap();
	

	public Map getSubTaskMap() {
		return subTaskMap;
	}

	public void setSubTaskMap(Map subTaskMap) {
		this.subTaskMap = subTaskMap;
	}
	public VcitySubTask getValue(String key){
		if(getSubTaskMap().get(key)==null){
			VcitySubTask vcitySubTask = new VcitySubTask();
			getSubTaskMap().put(key, vcitySubTask);
			return vcitySubTask;
		}
		return (VcitySubTask)getSubTaskMap().get(key);
	}
	
	public void setValue(String key, VcitySubTask vcitySubTask){
		getSubTaskMap().put(key, vcitySubTask);
	}
	
	
	
	/*******************************/
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public FormFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(FormFile imageFile) {
		this.imageFile = imageFile;
	}

	public FormFile getModelFile() {
		return modelFile;
	}

	public void setModelFile(FormFile modelFile) {
		this.modelFile = modelFile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getModuleFileName() {
		return moduleFileName;
	}

	public void setModuleFileName(String moduleFileName) {
		this.moduleFileName = moduleFileName;
	}

	public String getResDesc() {
		return resDesc;
	}

	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}

	public String getResImage() {
		return resImage;
	}

	public void setResImage(String resImage) {
		this.resImage = resImage;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public Integer getResType() {
		return resType;
	}

	public void setResType(Integer resType) {
		this.resType = resType;
	}

	public String getResURL() {
		return resURL;
	}

	public void setResURL(String resURL) {
		this.resURL = resURL;
	}

	public String[] getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}

	public VcityResType getVcityResType() {
		return vcityResType;
	}

	public void setVcityResType(VcityResType vcityResType) {
		this.vcityResType = vcityResType;
	}

	public Integer getSenceId() {
		return senceId;
	}

	public void setSenceId(Integer senceId) {
		this.senceId = senceId;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getSenceName() {
		return senceName;
	}

	public void setSenceName(String senceName) {
		this.senceName = senceName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getQuestionIds() {
		return questionIds;
	}

	public void setQuestionIds(String questionIds) {
		this.questionIds = questionIds;
	}

	public String getRecommendImage() {
		return recommendImage;
	}

	public void setRecommendImage(String recommendImage) {
		this.recommendImage = recommendImage;
	}

	public FormFile getRecommendImageFile() {
		return recommendImageFile;
	}

	public void setRecommendImageFile(FormFile recommendImageFile) {
		this.recommendImageFile = recommendImageFile;
	}
	
	public String getFullImage(){
		if (this.resType==1) {
			return "uploadfile/VCity/Application/image/"+resImage;
		} else {
			return "uploadfile/VCity/Model/image/"+resImage;
		}		
	}
	
	public String getViewRecommendImage(){
		 return "uploadfile/VCity/Recommend/image/"+this.recommendImage;
	}
	
	public String getViewAppFileName(){
		return "uploadfile/VCity/Application/software/"+this.moduleFileName;
	}
	
	public String getViewSkinFileName(){
		return "uploadfile/VCity/Model/Wingo/"+this.moduleFileName;
	}

}
