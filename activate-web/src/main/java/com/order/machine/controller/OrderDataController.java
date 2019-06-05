package com.order.machine.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.order.machine.dto.OrderStatistics;
import com.order.machine.dto.OrderTimesCount;
import com.order.machine.po.ActivatePo;
import com.order.machine.po.OrderConfigPo;
import com.order.machine.query.ActivateMachineQuery;
import com.order.machine.query.OrderConfigQuery;
import com.order.machine.query.OrderStatisticsQuery;
import com.order.machine.service.IOrderConfigService;
import com.order.machine.service.IOrderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author miou
 * @date 2019-06-05
 */
@RestController
@RequestMapping("data")
public class OrderDataController {

    @Autowired
    IOrderDataService orderDataService;
    @Autowired
    IOrderConfigService orderConfigService;

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
    public PageInfo<OrderConfigPo> listOrderConfigInfo(@RequestParam("beginDate") String beginDate,
                                      @RequestParam("endDate") String endDate,
                                      @RequestParam(value = "companyId",required = false) String companyId,
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
        PageInfo<OrderConfigPo> orderConfigPoPageInfo = orderDataService.listOrderConfig(orderConfigQuery);
//        String result = JSON.toJSONString(orderConfigPoPageInfo);
        return orderConfigPoPageInfo;
    }

    /**
     * 获取订单报盘信息
     * @param orderId
     * @return
     */
    @GetMapping("/v1/getOrderConfig")
    public OrderConfigPo getOrderConfigInfo(@RequestParam("orderId") String orderId){
        OrderConfigPo orderConfigPo = orderConfigService.getOrderConfig(orderId);
//        String result = JSON.toJSONString(orderConfigPo);
        return orderConfigPo;
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
    public PageInfo<ActivatePo> listActivateMachine(@RequestParam("orderId") String orderId,
                                      @RequestParam(value = "beginDate", required = false) String beginDate,
                                      @RequestParam(value = "endDate", required = false) String endDate,
                                      @RequestParam("pageNo") Integer pageNo,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam(value = "activateTimes",required = false, defaultValue = "1")
                                                  Integer activateTimes){
        ActivateMachineQuery activateMachineQuery = new ActivateMachineQuery();
        activateMachineQuery.setOrderId(orderId);
        activateMachineQuery.setBeginDate(beginDate);
        activateMachineQuery.setEndDate(endDate);
        activateMachineQuery.setPageNo(pageNo);
        activateMachineQuery.setPageSize(pageSize);
        activateMachineQuery.setActivateTimes(activateTimes);
        PageInfo<ActivatePo> activatePoPageInfo = orderDataService.listActivateMachine(activateMachineQuery);
//        String result = JSON.toJSONString(activatePoPageInfo);
        return activatePoPageInfo;
    }

    /**
     * 获取订单激活统计表
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("v1/listOrderCountTotal")
    public String listOrderCountTotal(@RequestParam(value = "companyId", required = false) String companyId,
                                      @RequestParam(value = "orderId", required = false) String orderId,
                                      @RequestParam(value = "beginDate") String beginDate,
                                      @RequestParam(value = "endDate") String endDate,
                                      @RequestParam("pageNo") Integer pageNo,
                                      @RequestParam("pageSize") Integer pageSize){
        OrderStatisticsQuery orderStatisticsQuery = new OrderStatisticsQuery();
        orderStatisticsQuery.setCompanyId(companyId);
        orderStatisticsQuery.setOrderId(orderId);
        orderStatisticsQuery.setOrderDateBegin(beginDate);
        orderStatisticsQuery.setOrderDateEnd(endDate);
        orderStatisticsQuery.setPageNo(pageNo);
        orderStatisticsQuery.setPageSize(pageSize);
        PageInfo<OrderStatistics> orderStatisticsPageInfo = orderDataService.listOrderCount(orderStatisticsQuery);
        String result = JSON.toJSONString(orderStatisticsPageInfo);
        return result;
    }

    /**
     * 获取激活次数统计表
     * @param orderId
     * @param activateTimes
     * @return
     */
    @GetMapping("v1/getOrderTimesCount")
    public List<OrderTimesCount> listActivateMachine(@RequestParam("orderId") String orderId,
                                                     @RequestParam(value = "activateTimes",required = false)
                                                        Integer activateTimes){
        List<OrderTimesCount> orderCountList = orderDataService.getOrderCount(orderId,activateTimes);
//        String result = JSON.toJSONString(orderCountList);
        return orderCountList;
    }
}
