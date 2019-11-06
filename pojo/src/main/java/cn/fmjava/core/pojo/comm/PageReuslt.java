package cn.fmjava.core.pojo.comm;


import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

/**
 * @author hy
 * @blame Development Group
 * createDate:2019/10/30/030 20:27
 * @since  公共pagePojo
 */
@Setter
@Getter
public class PageReuslt<T> implements Serializable {
  private static final long serialVersionUID = -6935237895567617797L;
  private  Long total;
  private List<T> rows;
  private int errorCode=0;
  private  String errorMsg="success";
  public PageReuslt(Long page, List<T> rows) {
    this.total = page;
    this.rows = rows;
  }

  public PageReuslt() {
  }
}
