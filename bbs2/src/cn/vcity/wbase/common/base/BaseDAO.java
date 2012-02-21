package cn.vcity.wbase.common.base;

/**
 * <code>BaseDAO.java</code>
 * Description:DAO层的接口,所有DAO接口都必须继承该接口.
 * @author 罗留庭 EN:Aly.Luo，开发部-珠海市网佳科技有限公司. Create-Time: 2007-9-8.
 * @version 1.0
 */
public interface BaseDAO {
    
    /**
     * 按升序排序
     */
    String SORT_ORDER_ASC = "ASC";
    
    /**
     * 按降序排序
     */
    String SORT_ORDER_DESC = "DESC";
    
    /**
     * 默认排序字段
     */
    String DEFAULT_SORT_PROPERTY = "id";
    
    /**
     * 默认排序类型
     */
    String DEFAULT_SORT_ORDER = "DESC";
}