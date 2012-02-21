package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.Comment;

public interface CommentDao {
    public Comment queryCommentById(Integer id);
    public List<Comment> queryCommentBythreadId(Integer threadid);
    public boolean addComment(Comment comment);
    public boolean saveOrUpdate(Comment comment);
}
