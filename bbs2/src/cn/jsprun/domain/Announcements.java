package cn.jsprun.domain;


public class Announcements  implements java.io.Serializable {


	private static final long serialVersionUID = -8855185146444212597L;
	private Short id;				
     private String author;			
     private String subject;		
     private Byte type;				
     private Byte displayorder;	
     private String starttime;		
     private String endtime;		
     private String message;		
     private String groups;			

    public Announcements() {
    }


    public Announcements(String author, String subject, Byte type, Byte displayorder, String starttime, String endtime, String message, String groups) {
        this.author = author;
        this.subject = subject;
        this.type = type;
        this.displayorder = displayorder;
        this.starttime = starttime;
        this.endtime = endtime;
        this.message = message;
        this.groups = groups;
    }


    public Short getId() {
        return this.id;
    }
    
    public void setId(Short id) {
        this.id = id;
    }

    public String getAuthor() {
        return this.author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSubject() {
        return this.subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Byte getType() {
        return this.type;
    }
    
    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getDisplayorder() {
        return this.displayorder;
    }
    
    public void setDisplayorder(Byte displayorder) {
        this.displayorder = displayorder;
    }

    public String getStarttime() {
        return this.starttime;
    }
    
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return this.endtime;
    }
    
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroups() {
        return this.groups;
    }
    
    public void setGroups(String groups) {
        this.groups = groups;
    }
   








}