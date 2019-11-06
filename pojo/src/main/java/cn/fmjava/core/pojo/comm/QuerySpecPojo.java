package cn.fmjava.core.pojo.comm;

import cn.fmjava.core.pojo.specification.Specification;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author hy
 * @blame Development Group
 * createDate:2019/11/4/004 20:23
 * @since
 */
@Setter@Getter
public class QuerySpecPojo implements Serializable {
    private static final long serialVersionUID = -2989255900911519219L;
    private int errorCode;
    private String msg;
    private Specification specification;
}
