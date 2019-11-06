package cn.fmjava.core.pojo.specification;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author hy
 * @blame Development Group
 * createDate:2019/11/4/004 21:09
 * @since
 */
@Getter@Setter
public class SpecificationPjo implements Serializable {
    private static final long serialVersionUID = -5351953839006082439L;
    private Specification specification;
    private List<SpecificationOption> specificationOptionList;
    private int errorCode;
    private String msg;

    @Override
    public String toString() {
        return "SpecificationPjo{" +
                "specification=" + specification +
                ", specificationOptionList=" + specificationOptionList +
                ", errorCode=" + errorCode +
                ", msg='" + msg + '\'' +
                '}';
    }
}
