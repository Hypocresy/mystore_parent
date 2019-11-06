package cn.hy.core.service;

import cn.fmjava.core.dao.specification.SpecificationDao;
import cn.fmjava.core.dao.specification.SpecificationOptionDao;
import cn.fmjava.core.pojo.comm.PageReuslt;
import cn.fmjava.core.pojo.specification.*;
import cn.hy.fm.core.service.SpecService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author hy
 * @blame Development Group
 * createDate:2019/11/4/004 14:02
 * @since
 */
@Service@Transactional(rollbackFor = Exception.class)
public class SpecServiceImpl implements SpecService {
    @Autowired
    SpecificationDao specificationDao;
    @Autowired
    SpecificationOptionDao specificationOptionDao;
    @Override
    public PageReuslt search(Specification spec, Integer page, Integer rows) {
        SpecificationQuery specificationQuery = new SpecificationQuery();
        PageReuslt<Specification> pageReuslt = new PageReuslt<>();
        try {
            SpecificationQuery.Criteria criteria = specificationQuery.createCriteria();
            if (!StringUtils.isBlank(spec.getSpecName())) {
                criteria.andSpecNameLike("%" + spec.getSpecName() + "%");
            }
            PageHelper.startPage(page, rows);
            List<Specification> specifications = specificationDao.selectByExample(specificationQuery);
            PageInfo<Specification> pageInfo = new PageInfo<>(specifications);
            pageReuslt.setTotal(pageInfo.getTotal());
            pageReuslt.setRows(specifications);
            pageReuslt.setErrorCode(0);
            pageReuslt.setErrorMsg("success");
        }catch (Exception e){
            pageReuslt.setErrorCode(-1);
            pageReuslt.setErrorMsg(e.getMessage());
            e.printStackTrace();
        }
        return pageReuslt;
    }

    @Override
    public PageReuslt deleteSpec(Long[] ids) {
        PageReuslt<Specification> pageReuslt = new PageReuslt<>();
        try {
            SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
            SpecificationOptionQuery.Criteria criteria1 = specificationOptionQuery.createCriteria();
            criteria1.andSpecIdIn(Arrays.asList(ids));
            specificationOptionDao.deleteByExample(specificationOptionQuery);
            SpecificationQuery specificationQuery = new SpecificationQuery();
            SpecificationQuery.Criteria criteria = specificationQuery.createCriteria();
            criteria.andIdIn(Arrays.asList(ids));
            specificationDao.deleteByExample(specificationQuery);
        }catch (Exception e){
            e.printStackTrace();
            pageReuslt.setErrorCode(-1);
            pageReuslt.setErrorMsg(e.getMessage());
        }
        return pageReuslt;
    }

    @Override
    public PageReuslt addAndUpdateSpec(SpecificationPjo specificationPjo) {
        PageReuslt<Specification> pageReuslt = new PageReuslt<>();
        try {
            Specification specification = specificationPjo.getSpecification();
            List<SpecificationOption> optionList = specificationPjo.getSpecificationOptionList();
            if(specification.getId()==null) {
                specificationDao.insert(specification);
                for(SpecificationOption specificationOption:optionList){
                    specificationOption.setSpecId(specification.getId());
                    specificationOptionDao.insert(specificationOption);
                }
            }else{
                specificationDao.updateByPrimaryKey(specification);
                for(SpecificationOption specificationOption:optionList){
                    if(specificationOption.getId()!=null){
                        specificationOption.setSpecId(specification.getId());
                        specificationOptionDao.updateByPrimaryKey(specificationOption);
                    }else{
                        specificationOption.setSpecId(specification.getId());
                        specificationOptionDao.insert(specificationOption);
                    }
                }
            }
            pageReuslt.setErrorCode(0);
            pageReuslt.setErrorMsg("success");
        }catch (Exception e){
            pageReuslt.setErrorCode(-1);
            pageReuslt.setErrorMsg(e.getMessage());
            e.printStackTrace();
        }
        return pageReuslt;
    }

    @Override
    public SpecificationPjo findSpecById(Long id) {
        SpecificationPjo specificationPjo = new SpecificationPjo();
        try {
            Specification specification = specificationDao.selectByPrimaryKey(id);
            specificationPjo.setSpecification(specification);
            SpecificationOptionQuery specificationOptionQuery = new SpecificationOptionQuery();
            SpecificationOptionQuery.Criteria criteria = specificationOptionQuery.createCriteria();
            criteria.andSpecIdEqualTo(specification.getId());
            List<SpecificationOption> specificationOptions = specificationOptionDao.selectByExample(specificationOptionQuery);
            specificationPjo.setSpecificationOptionList(specificationOptions);
            specificationPjo.setErrorCode(0);
            specificationPjo.setMsg("success");
        }catch (Exception e){
            specificationPjo.setMsg(e.getMessage());
            specificationPjo.setErrorCode(-1);
            e.printStackTrace();
        }
        return specificationPjo;
    }

    @Override
    public PageReuslt deleteSpecOption(Long id) {
        PageReuslt<SpecificationOption> pageReuslt = new PageReuslt<>();
        try {
            specificationOptionDao.deleteByPrimaryKey(id);
            pageReuslt.setErrorMsg("success");
            pageReuslt.setErrorCode(0);
        }catch (Exception e){
            pageReuslt.setErrorCode(-1);
            pageReuslt.setErrorMsg(e.getMessage());
            e.printStackTrace();
        }
        return pageReuslt;
    }


}
