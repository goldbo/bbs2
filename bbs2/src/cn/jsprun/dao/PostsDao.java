package cn.jsprun.dao;

import java.util.List;
import java.util.Map;
import cn.jsprun.domain.Posts;
import cn.jsprun.struts.form.ModrepliesPageForm;
import cn.jsprun.struts.form.PostsPageForm;
 
public interface PostsDao {

	List validatePostsIDArray(List validateList);

	public PostsPageForm fidnByForums(short fid,String displayorder);

	public boolean deletePostsIDArray(List deleteList);

	public boolean ignorePostsIDArray(List ignoreList);

	public boolean deleteModrepliesIDArray(List<String> modrepliesList);

	public boolean ignoreModrepliesIDArray(List<String> modrepliesList);

	public boolean validateModrepliesIDArray(List<Posts> modrepliesList);

	public ModrepliesPageForm fidnByModreplies(short fid,String invisible);

	public List<Posts> findByPosts(Posts posts);

	public boolean modifyPosts(Posts posts);

	public boolean deletePostsByUserUid(Integer uid);

	public List<Posts> findByUserName(String userName);

	public Map batchPrune(String sb, boolean b);

	public void deleteForumrecommend(StringBuffer deleteSB);

	public Integer updateForumrecommendList(List list);
	 
	public Posts findByTid(Integer tid);
 
	public Posts getPostsById(Integer pid);
	
	public Posts findPostByThreadId(int tid);
 
	public int findPostCount();
	 
	public int findPostCountByHql(String hql);
	public List<Posts> findPostByhql(String hql,int start,int max);
	public List<Posts> getPostsListByPidList(List<Integer> pidList);
	
	public Map<String,String> getBestmemAndBestmemposts(Integer nowTime);
	public Map<String,String> getPostsAndRuntime();
	 
	public Integer getPostsaddtoday();
	public boolean updatePosts(Posts post);
	public int saveOrupdatePosts(Posts post);
	
	public List<Posts> getPostsListByTid(Integer tid);
	public void deletePosts(List<Integer> pidList);
	public Posts getLastPosts(Integer tid);
	 
	public Posts getLastPosts(Short fid);
 
	public Posts getFirstPosts(Integer tid);
 
	public void updatePosts(List<Posts> postsList);
	 
	public Integer getCountOfReplyForTopic(Integer topicId);
	 
	public void updatePostsByHQL(String hql);
}
