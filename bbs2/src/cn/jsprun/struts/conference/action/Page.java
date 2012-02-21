package cn.jsprun.struts.conference.action;

import java.util.List;




public class Page {
	
	 int currentPage = 1;
	private int pageSize = 15;
	private int pageTotal = 0;
    private int recordTotal =0;
	 List dataList = null;
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageTotal() {
		return pageTotal;
	}
	public void setRecordTotal(int recordTotal) {
		this.pageTotal =  recordTotal % pageSize == 0 ? recordTotal / pageSize : recordTotal  / pageSize + 1;
        this.recordTotal = recordTotal;
        if(currentPage > pageTotal)this.currentPage =1;
	}    
	
    public int getRecordTotal() {
        return recordTotal;
    }
    public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
	public void next(){
		if(this.currentPage < this.pageTotal){
			this.currentPage = this.currentPage + 1 ;
		}else if(this.pageTotal == 0){
			this.currentPage = 1;
		}else{
			this.currentPage = this.pageTotal;
		}
	}
	public void prvs(){
		if(this.currentPage > 1){
			this.currentPage = this.currentPage - 1 ;
		}else{
			this.currentPage = 1;
		}
	}
	

}
