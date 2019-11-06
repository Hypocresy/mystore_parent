package cn.hy.core.controller;

import cn.fmjava.core.pojo.comm.PageReuslt;
import cn.fmjava.core.pojo.comm.QueryBrandPojo;
import cn.fmjava.core.pojo.good.Brand;
import cn.hy.fm.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hy
 * @blame Development Group
 * createDate:2019/10/26/026 16:09
 * @since
 */
@Slf4j
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    BrandService brandService;

    @RequestMapping("/findPage")
    public PageReuslt findPage(Integer page, Integer rows,@RequestBody Brand brand){
       return brandService.findAll(page,rows,brand);
    }

    @RequestMapping("/addBrand")
    public QueryBrandPojo addBrand(@RequestBody Brand brand){
        return brandService.addBrand(brand);
    }

    @RequestMapping("/findById")
    public QueryBrandPojo findById( Long brandId){
        return brandService.findById(brandId);
    }

    @RequestMapping("/updateById")
    public QueryBrandPojo updateBrand(@RequestBody Brand brand){
        return brandService.updateBrand(brand);
    }

    @RequestMapping("/deleteBrand")
    public QueryBrandPojo deleteBrandByArray(Long [] ids){
        log.info(">>>>>>>ids:"+ids);
        return brandService.deleteBrandByArray(ids);
    }
}
