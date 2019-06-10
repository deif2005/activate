package com.order.machine.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.order.machine.po.CompanyPo;
import com.order.machine.service.IOrderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author miou
 * @date 2019-05-27
 */
@RestController
@RequestMapping(value = "base")
public class BaseDataController {

    @Autowired
    IOrderDataService orderDataService;

    /**
     * 新增客户
     * @param companyName
     * @return
     */
    @PostMapping("v1/addCustomer")
    public void addCompanyInfo(@RequestParam("companyName") String companyName){
        CompanyPo companyPo = new CompanyPo();
        companyPo.setCompanyName(companyName);
        orderDataService.addCompanyInfo(companyPo);
    }

    /**
     * 更新客户
     * @param companyId
     * @param companyName
     * @return
     */
    @PostMapping("v1/updateCustomer")
    public void updateCompanyInfo(@RequestParam("companyId") String companyId,
                                  @RequestParam("companyName") String companyName){
        CompanyPo companyPo = new CompanyPo();
        companyPo.setCompanyId(companyId);
        companyPo.setCompanyName(companyName);
        orderDataService.updateCompanyInfo(companyPo);
    }

    /**
     * 删除客户
     * @param companyId
     * @return
     */
    @PostMapping("v1/deleteCustomer")
    public void deleteCompanyInfo(@RequestParam("companyId") String companyId){
        CompanyPo companyPo = new CompanyPo();
        companyPo.setCompanyId(companyId);
        companyPo.setStatus("2");
        orderDataService.updateCompanyInfo(companyPo);
    }

    /**
     * 获取客户列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("v1/listCustomer")
//    @RequestMapping("v1/listCompany")
    public PageInfo<CompanyPo> listCustomer(@RequestParam(value = "pageNo",required = false) Integer pageNo,
                                            @RequestParam(value = "pageSize",required = false) Integer pageSize){
        Page page = new Page();
        if (pageNo != null && pageSize != null){
            page.setPageNum(pageNo);
            page.setPageSize(pageSize);
        }
        PageInfo<CompanyPo> companyPoPageInfo = orderDataService.listCompany(page);
        return companyPoPageInfo;
    }
}
