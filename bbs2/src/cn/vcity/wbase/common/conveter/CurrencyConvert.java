package cn.vcity.wbase.common.conveter;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * 该类的作用是将财务表示的字值型对象转换成为double型，通过注册该转换器，实现系统的自动转换.<br/>
 * 
 * @author 罗留庭 EN:Aly.Luo，开发部-珠海市网佳科技有限公司. Create-Time: 2007-9-8.
 * @version 1.0  
 * @see Converter
 */
public class CurrencyConvert implements Converter {
    protected final DecimalFormat formatter = new DecimalFormat("###,###.00");

    /**
     * 将一个字符串转换成为Double或将一个Double转换成为String
     *
     * @param type the class type to output
     * @param value the object to convert
     * @return object the converted object (Double or String)
     */
    public final Object convert(final Class type, final Object value) {
        // 如果为空，则返回null.
        if (value == null) {
            return null;
        } else {
            if (value instanceof String) {
                try {
                    if (StringUtils.isEmpty(String.valueOf(value))) {
                        return null;
                    }

                    Number num = formatter.parse(String.valueOf(value));

                    return new Double(num.doubleValue());
                } catch (ParseException pe) {
                    pe.printStackTrace();
                }
            } else if (value instanceof Double) {
                return formatter.format(value);
            }
        }

        throw new ConversionException("无法转换值: " + value + " to " + type.getName() + "!");
    }
}
