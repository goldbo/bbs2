package cn.vcity.model;

import java.util.Comparator;



/**
 *自定义排序,在一的一方的set列设置sort属性
 */

public class QuestionitemSort  implements Comparator {
	  public   int   compare(Object   o1,   Object   o2)   {
		  VcityQuestionitem   u1   =   (VcityQuestionitem)o1;
		  VcityQuestionitem   u2   =   (VcityQuestionitem)o2;
          return  u1.getItemIndex() - u2.getItemIndex();
      }
  }
