package cn.hy.fm.core.service;

import cn.fmjava.core.pojo.comm.PageReuslt;
import cn.fmjava.core.pojo.comm.QueryBrandPojo;
import cn.fmjava.core.pojo.good.Brand;


/**
 * @author hy
 * @blame Development Group
 * createDate:2019/10/26/026 15:41
 * @since
 */
public interface BrandService {
    /**
     * 品牌分页
     * 功能详细描述
     * @param page 当前页
     * @param rows 每页多少数据
     * @return 查出的数据及 当前页
     */
    PageReuslt findAll(Integer page, Integer rows,Brand brand);

    /**
     * 添加品牌
     * 功能详细描述
     * @param brand 参数1说明
     * @return 返回添加信息
     */
    QueryBrandPojo addBrand(Brand brand);

    /**
     * 根据brandId 查询品牌实体
     * @param brandId 参数1说明
     * @return 公共包装类
     */
    QueryBrandPojo findById(Long brandId);

    /**
     * 修改品牌
     * 功能详细描述
     * @param brand 参数2说明
     * @return 公共brand包装类
     */
    QueryBrandPojo updateBrand(Brand brand);

    /**
     * 删除选中的brand
     * 功能详细描述
     * @param ids 选中brand的 id
     * @return [返回类型说明]
     */
    QueryBrandPojo deleteBrandByArray(Long[] ids);
}
