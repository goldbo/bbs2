package cn.vcity.wbase.common.helper;

import java.util.List;

public class Pagelet implements java.io.Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 4050411484445383124L;

  private long totalRecord; // 总记录数

  private List pageList; // 对象集

  private String pageName; // 对象名称

  public Pagelet() {

  }

  public Pagelet(String name) {

    pageName = name;
  }

  /**
   * @return
   */
  public long getTotalRecord() {

    return totalRecord;
  }

  public List getPageList() {

    return pageList;
  }

  public void setPageList(
      List pageList) {

    this.pageList = pageList;
  }

  public String getPageName() {

    return pageName;
  }

  public void setPageName(
      String pageName) {

    this.pageName = pageName;
  }

  public void setTotalRecord(
      long totalRecord) {

    this.totalRecord = totalRecord;
  }

}
