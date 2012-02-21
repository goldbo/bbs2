package cn.jsprun.vo;
public class GroupsVO {
	private short groupid;
	private String grouptitle;
	private boolean isspacal;
	private String price;
	private String days;
	private int dateline;
	private boolean isextgroup;
	private boolean isaddgroup;
	private boolean ismaingroup;
	public int getDateline() {
		return dateline;
	}
	public void setDateline(int dateline) {
		this.dateline = dateline;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getGrouptitle() {
		return grouptitle;
	}
	public void setGrouptitle(String grouptitle) {
		this.grouptitle = grouptitle;
	}
	public boolean isIsaddgroup() {
		return isaddgroup;
	}
	public void setIsaddgroup(boolean isaddgroup) {
		this.isaddgroup = isaddgroup;
	}
	public boolean isIsextgroup() {
		return isextgroup;
	}
	public void setIsextgroup(boolean isextgroup) {
		this.isextgroup = isextgroup;
	}
	public boolean isIsmaingroup() {
		return ismaingroup;
	}
	public void setIsmaingroup(boolean ismaingroup) {
		this.ismaingroup = ismaingroup;
	}
	public boolean isIsspacal() {
		return isspacal;
	}
	public void setIsspacal(boolean isspacal) {
		this.isspacal = isspacal;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public short getGroupid() {
		return groupid;
	}
	public void setGroupid(short groupid) {
		this.groupid = groupid;
	}
}
