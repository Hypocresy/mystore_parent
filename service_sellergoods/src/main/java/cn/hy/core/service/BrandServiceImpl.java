package cn.hy.core.service;

import cn.fmjava.core.dao.good.BrandDao;
import cn.fmjava.core.pojo.comm.PageReuslt;
import cn.fmjava.core.pojo.comm.QueryBrandPojo;
import cn.fmjava.core.pojo.good.Brand;
import cn.fmjava.core.pojo.good.BrandQuery;
import cn.hy.fm.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author hy
 * @blame Development Group
 * createDate:2019/10/26/026 15:43
 * @since
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;

    @Override
    public PageReuslt<Brand> findAll(Integer page, Integer rows,Brand brand) {
        log.info("BrandServiceImpl.findAll>>>>>>page:"+page+"  ，rows:"+rows);
        PageReuslt pageReuslt= new PageReuslt();
        BrandQuery brandQuery = new BrandQuery();
        if(brand!=null){
            BrandQuery.Criteria criteria = brandQuery.createCriteria();
            if(!StringUtils.isBlank(brand.getName())){
                 criteria.andNameLike("%"+brand.getName()+"%");
            }
            if(!StringUtils.isBlank(brand.getFirstChar())){
                criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
            }
        }
        try {
           PageHelper.startPage(page, rows);
           List brands = brandDao.selectByExample(brandQuery);
           PageInfo<Brand> pageinfo = new PageInfo<>(brands);
           pageReuslt.setRows(brands);
           pageReuslt.setTotal(pageinfo.getTotal());
       }catch (Exception e){
           pageReuslt.setErrorCode(-1);
           pageReuslt.setErrorMsg("未知错误");
           e.printStackTrace();
       }
       return pageReuslt;
    }

    @Override
    public QueryBrandPojo addBrand(Brand brand) {
        QueryBrandPojo queryBrandPojo=new QueryBrandPojo();
        try{
            brandDao.insert(brand);
            queryBrandPojo.setErrorCode(0);
            queryBrandPojo.setMsg("success");
        }catch (Exception e){
            e.printStackTrace();
           queryBrandPojo.setMsg("操作失败，请检查添加信息");
           queryBrandPojo.setErrorCode(-1);
        }
        return queryBrandPojo;
    }

    @Override
    public QueryBrandPojo findById(Long brandId){
        QueryBrandPojo queryBrandPojo = new QueryBrandPojo();
        try {
            Brand brand = brandDao.selectByPrimaryKey(brandId);
            log.info(">>>brandId:"+brandId+">>>>"+brand);
            queryBrandPojo.setT(brand);
            queryBrandPojo.setErrorCode(0);
            queryBrandPojo.setMsg("success");
        }catch (Exception e){
            e.printStackTrace();
            queryBrandPojo.setMsg("未知错误");
            queryBrandPojo.setErrorCode(-1);
        }
        return queryBrandPojo;
    }

    @Override
    public QueryBrandPojo updateBrand(Brand brand) {
        QueryBrandPojo queryBrandPojo = new QueryBrandPojo();
        try{
          brandDao.updateByPrimaryKey(brand);
          queryBrandPojo.setErrorCode(0);
          queryBrandPojo.setMsg("操作成功");
        }catch (Exception e){
          e.printStackTrace();
          queryBrandPojo.setErrorCode(-1);
          queryBrandPojo.setMsg("操作失败，请检查修改信息");
        }
        return queryBrandPojo;
    }

    @Override
    public QueryBrandPojo deleteBrandByArray(Long[] ids) {
        QueryBrandPojo queryBrandPojo = new QueryBrandPojo();
        BrandQuery brandQuery = new BrandQuery();
        try{
            BrandQuery.Criteria criteria = brandQuery.createCriteria();
            criteria.andIdIn(Arrays.asList(ids));
            brandDao.deleteByExample(brandQuery);
            queryBrandPojo.setMsg("操作成功");
            queryBrandPojo.setErrorCode(0);
        }catch (Exception e){
            e.printStackTrace();
            queryBrandPojo.setMsg("操作失败,未知错误");
            queryBrandPojo.setErrorCode(-1);
        }
        return queryBrandPojo;
    }
}
