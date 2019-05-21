package com.order.machine.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.order.machine.common_const.CommonEnum;
import com.order.machine.exception.LogicException;
import com.order.machine.po.ActivatePo;
import com.order.machine.po.OrderConfigPo;
import com.order.machine.query.ActivateMachineQuery;
import com.order.machine.query.OrderConfigQuery;
import com.order.machine.service.IOrderConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author miou
 * @date 2019-04-17
 */
@RestController
@RequestMapping(value = "/order")
public class OrderConfigController {

    @Autowired
    IOrderConfigService orderConfigService;

    /**
     * 导入订单授权报盘表格
     * @return
     */
    @PostMapping("/v1/addOrderConfig")
    public String addOrderConfig(){
        orderConfigService.importOrderConfigInfo("");
        return "";
    }

    /**
     * 获取订单报盘信息
     * @param id
     * @return
     */
    @GetMapping("/v1/getOrderConfig")
    public String getOrderConfigInfo(@RequestParam("id") String id){
        OrderConfigPo orderConfigPo = orderConfigService.getOrderConfig(id);
        String result = JSON.toJSONString(orderConfigPo);
        return result;
    }

    /**
     * 获取订单信息列表
     * @param beginDate
     * @param endDate
     * @param companyId 用户所属客户id
     * @param pageNo
     * @param pageSize
     * @param isClose
     * @return
     */
    @GetMapping("v1/listOrderConfig")
    public String listOrderConfigInfo(@RequestParam("beginDate") String beginDate,
                                      @RequestParam("endDate") String endDate,
                                      @RequestParam("companyId") String companyId,
                                      @RequestParam("pageNo") Integer pageNo,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam(value = "isClose", required = false) String isClose){
        OrderConfigQuery orderConfigQuery = new OrderConfigQuery();
        orderConfigQuery.setBeginDate(beginDate);
        orderConfigQuery.setEndDate(endDate);
        orderConfigQuery.setCompanyId(companyId);
        orderConfigQuery.setPageNo(pageNo);
        orderConfigQuery.setPageSize(pageSize);
        orderConfigQuery.setIsClose(isClose);
        PageInfo<OrderConfigPo> orderConfigPoPageInfo = orderConfigService.listOrderConfig(orderConfigQuery);
        String result = JSON.toJSONString(orderConfigPoPageInfo);
        return result;
    }

    /**
     * 更新订单配置
     * @param id
     * @param isClose
     * @return
     */
    @PostMapping("v1/updateOrderConfig")
    public String updateOrderConfig(@RequestParam("id") String id,
                                    @RequestParam("isClose") Integer isClose){
        OrderConfigPo orderConfigPo = new OrderConfigPo();
        orderConfigPo.setId(id);
        orderConfigPo.setIsClose(String.valueOf(isClose));
        orderConfigService.modifyOrderConfig(orderConfigPo);
        return "";
    }

    /**
     * 激活机器
     * @param activateParam
     * @return
     */
    @PostMapping("v1/activateMachine")
    public String activateMachine(@RequestParam("activateParam") String activateParam){
        ActivatePo activatePo = JSON.parseObject(activateParam,ActivatePo.class);
        if (Strings.isNullOrEmpty(activatePo.getOrderId()))
            throw LogicException.le(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue(),
                    "订单号未提供");
        if (Strings.isNullOrEmpty(activatePo.getChipSn()))
            throw LogicException.le(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue(),
                    "机顶盒的ID未提供");
        String result = orderConfigService.checkActivate(activatePo);
        return result;
    }

    /**
     * 获取机器激活信息列表
     * @param orderId
     * @param beginDate
     * @param endDate
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("v1/listActivateMachine")
    public String listActivateMachine(@RequestParam("orderId") String orderId,
                                      @RequestParam(value = "beginDate", required = false) String beginDate,
                                      @RequestParam(value = "endDate", required = false) String endDate,
                                      @RequestParam("pageNo") Integer pageNo,
                                      @RequestParam("pageSize") Integer pageSize){
        ActivateMachineQuery activateMachineQuery = new ActivateMachineQuery();
        activateMachineQuery.setOrderId(orderId);
        activateMachineQuery.setBeginDate(beginDate);
        activateMachineQuery.setEndDate(endDate);
        activateMachineQuery.setPageNo(pageNo);
        activateMachineQuery.setPageSize(pageSize);
        PageInfo<ActivatePo> activatePoPageInfo = orderConfigService.listActivateMachine(activateMachineQuery);
        String result = JSON.toJSONString(activatePoPageInfo);
        return result;
    }
}
