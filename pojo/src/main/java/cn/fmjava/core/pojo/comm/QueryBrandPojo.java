package cn.fmjava.core.pojo.comm;

import cn.fmjava.core.pojo.good.Brand;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author hy
 * @blame Development Group
 * createDate:2019/10/31/031 10:43
 * @since
 */
@Setter@Getter
public class QueryBrandPojo implements Serializable {
  private static final long serialVersionUID = -5793416425859161815L;
  private Brand t;
  private int errorCode;
  private String msg;
}
