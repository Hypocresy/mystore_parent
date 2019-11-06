package cn.hy.core.controller;

import cn.fmjava.core.pojo.comm.PageReuslt;
import cn.fmjava.core.pojo.comm.QuerySpecPojo;
import cn.fmjava.core.pojo.specification.Specification;
import cn.fmjava.core.pojo.specification.SpecificationPjo;
import cn.hy.fm.core.service.SpecService;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hy
 * @blame Development Group
 * createDate:2019/11/4/004 11:30
 * @since
 */
@RestController
@RequestMapping("/spec")
@Slf4j
public class SpecController {
 @Reference
 SpecService specService;

 @RequestMapping("/search")
 public PageReuslt search( Integer page, Integer rows,@RequestBody Specification searchObject){
     log.info("cn.hy.core.controller.SpecController.search>>>>>>>>>>>>>page:"+page+" rows:"+rows+" searchObject"+searchObject);
     return specService.search(searchObject,page,rows);
 }

 @RequestMapping("/deleteSpec")
 public PageReuslt deleteSpec(Long [] ids){
     log.info("cn.hy.core.controller.SpecController.deleteSpec>>>>>>>>>>ids:"+ids);
     return specService.deleteSpec(ids);
 }

 @RequestMapping("/addAndUptateSpec")
 public PageReuslt addAndUpdateSpec(@RequestBody SpecificationPjo specification){
     log.info("cn.hy.core.controller.SpecController.addSpec>>>>>>>>>specification:"+specification);
     return specService.addAndUpdateSpec(specification);
 }

 @RequestMapping("/findSpecById")
 public SpecificationPjo findSpecById(Long id){
     return specService.findSpecById(id);
 }

 @RequestMapping("/deleteSpecOption")
 public  PageReuslt deleteSpecOption(Long id){
    return specService.deleteSpecOption(id);
 }


}
