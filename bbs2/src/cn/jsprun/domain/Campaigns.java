package cn.jsprun.domain;

public class Campaigns  implements java.io.Serializable {

	private static final long serialVersionUID = 2689066365766763015L;
	private CampaignsId id;		
     private Short fid;				
     private Integer tid;			
     private Byte status;			
     private Integer begintime;		
     private Integer starttime;		
     private Integer endtime;		
     private Integer expiration;		
     private Integer nextrun;		

    public Campaigns() {
    }

    public Campaigns(CampaignsId id, Short fid, Integer tid, Byte status, Integer begintime, Integer starttime, Integer endtime, Integer expiration, Integer nextrun) {
        this.id = id;
        this.fid = fid;
        this.tid = tid;
        this.status = status;
        this.begintime = begintime;
        this.starttime = starttime;
        this.endtime = endtime;
        this.expiration = expiration;
        this.nextrun = nextrun;
    }

    public CampaignsId getId() {
        return this.id;
    }
    
    public void setId(CampaignsId id) {
        this.id = id;
    }

    public Short getFid() {
        return this.fid;
    }
    
    public void setFid(Short fid) {
        this.fid = fid;
    }

    public Integer getTid() {
        return this.tid;
    }
    
    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Byte getStatus() {
        return this.status;
    }
    
    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getBegintime() {
        return this.begintime;
    }
    
    public void setBegintime(Integer begintime) {
        this.begintime = begintime;
    }

    public Integer getStarttime() {
        return this.starttime;
    }
    
    public void setStarttime(Integer starttime) {
        this.starttime = starttime;
    }

    public Integer getEndtime() {
        return this.endtime;
    }
    
    public void setEndtime(Integer endtime) {
        this.endtime = endtime;
    }

    public Integer getExpiration() {
        return this.expiration;
    }
    
    public void setExpiration(Integer expiration) {
        this.expiration = expiration;
    }

    public Integer getNextrun() {
        return this.nextrun;
    }
    
    public void setNextrun(Integer nextrun) {
        this.nextrun = nextrun;
    }
   








}