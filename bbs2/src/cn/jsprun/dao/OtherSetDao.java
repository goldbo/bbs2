package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.Announcements;
import cn.jsprun.domain.Faqs;
import cn.jsprun.domain.Forumlinks;
import cn.jsprun.domain.Magicmarket;
import cn.jsprun.domain.Magics;
import cn.jsprun.domain.Medals;
import cn.jsprun.domain.Settings;

public interface OtherSetDao {
	
	public boolean magicBasicSet(List<Settings> setBeanList);
	
	public List<Magics> queryAllMagic();
	 
	public List<Magics> queryAllMagic(Integer begin,Integer maxNum);
	
	public List<Magics> queryAllMagicByType(Short typeNum);
 
	public List<Magics> queryAllMagicByType(Short typeNum,Integer begin,Integer Max);
 
	public Magics queryMagicById(short id);
	
	public List<Magics> getMagicsListByMagicIdList(List<Short> magicIdList);
 
	public List<Magics> getAvailableMagics();
	 
	public boolean updateMagic(Magics magicBean);
	
	public boolean updateMagics(List<Magics> magicBeanList);
	
	public boolean delAnyMagicByIds(String ids[]);
	
	public List<Magicmarket> queryAllMagicInMarket();
	
	public Magicmarket queryMagicMarketById(short id);
	
	public boolean updateMagicMarket(List<Magicmarket> mmBeanList);
	
	public boolean delAnyMagicFromMagicMarket(String deletes[]);
	
	public List<Magics> getMagicByIdentifier(String identifier);
	
	public Announcements queryAnnouncementById(short id);
	
	public boolean updateAnnouncement(Announcements ann);
	
	public boolean delAnyAnnouncement(String deletes[]);
	
	public boolean updateAnnouncement(List<Announcements> annBeanList);
	
	public boolean addAnnouncement(Announcements annBean);
	
	public List<Medals> queryAllMedals();
	
	public Medals queryMedalById(short id);
 
	public boolean addMedal(Medals medalBean);
	
	public boolean delAnyMedals(String deletes[]);
	
	public boolean updateMedals(List<Medals> medalBeanList);
	
	public boolean addForumLink(Forumlinks fBean);
	
	public List<Forumlinks> queryAllForumLink();
	
	public Forumlinks queryForumLinkById(short id);
	
	public boolean delAnyForumLinks(String deletes[]);
	
	public boolean updateForumLinks(List<Forumlinks> flList);
	
	public List<Faqs> queryAllFaq();
	
	public Faqs queryFaqById(Short id);
	
	public boolean delAnyFaqs(List<Faqs> faqsListDelete);
	
	public boolean updateFaq(Faqs fBean);
	
	public boolean updateFaqs(List<Faqs> faqsListUpdate);
	
	public boolean addFaqs(Faqs faqs);
	
	public List<Faqs> getRootFaqs();
}
