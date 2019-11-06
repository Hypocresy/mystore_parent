package cn.hy.fm.core.service;

import cn.fmjava.core.pojo.comm.PageReuslt;
import cn.fmjava.core.pojo.comm.QuerySpecPojo;
import cn.fmjava.core.pojo.specification.Specification;
import cn.fmjava.core.pojo.specification.SpecificationPjo;

/**
 * @author hy
 * @blame Development Group
 * createDate:2019/11/4/004 13:58
 * @since
 */
public interface SpecService {

    /**
     * 一句话功能描述
     * 功能详细描述
     * @param spec 参数1说明
     * @param page 参数2说明
     */
    PageReuslt search(Specification spec,Integer page,Integer rows);

    PageReuslt deleteSpec(Long[] ids);

    PageReuslt addAndUpdateSpec(SpecificationPjo specification);

    SpecificationPjo findSpecById(Long id);

    PageReuslt deleteSpecOption(Long id);

}
