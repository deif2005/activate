package com.order.machine.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.order.machine.StringUtils;
import com.order.machine.common_const.CommonEnum;
import com.order.machine.dto.OrderStatistics;
import com.order.machine.exception.LogicException;
import com.order.machine.po.ActivatePo;
import com.order.machine.po.BoxExchangePo;
import com.order.machine.po.OrderConfigPo;
import com.order.machine.query.ActivateMachineQuery;
import com.order.machine.query.OrderConfigQuery;
import com.order.machine.query.OrderStatisticsQuery;
import com.order.machine.service.IOrderConfigService;
import com.order.machine.service.IOrderDataService;
import com.wd.encrypt.AESUtil;
import com.wd.util.DateUtil;
import com.wd.util.UUIDGenerator;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
    @Autowired
    IOrderDataService orderDataService;
    @Autowired
    private Environment environment;

    /**
     * 添加订单信息
     * @param companyId
     * @param chipSn
     * @param licenceCount
     * @param dateStr
     * @param key1
     * @return
     */
    @PostMapping("/v1/addOrderConfig")
    public String addOrderConfig(@RequestParam("companyId") String companyId,
                                 @RequestParam("chipSn") String chipSn,
                                 @RequestParam("licenceCount") Integer licenceCount,
                                 @RequestParam("dateStr") String dateStr,
                                 @RequestParam(value = "key1",required = false) String key1){
        StringBuilder sb = new StringBuilder();
        companyId = StringUtils.completeFixCode(companyId,3);
        sb.append(companyId).append("_").append(chipSn).append("_").append(dateStr).append("_");
        if (Strings.isNullOrEmpty(key1)){
            key1 = UUIDGenerator.getUUID();
        }
        Integer sn = orderDataService.getMaxOrderSn(companyId);
        String s = StringUtils.completeFixCode(String.valueOf(sn),4);
        sb.append(s);
        //上传文档
        orderConfigService.addOrderConfigInfo(sb.toString(),companyId,licenceCount,dateStr,key1);
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
                                    @RequestParam(value="licenceCount", required = false) Integer licenceCount,
                                    @RequestParam(value = "isClose",required = false) String isClose) throws
            MissingServletRequestParameterException{
        OrderConfigPo orderConfigPo = new OrderConfigPo();
        orderConfigPo.setId(id);
        if (licenceCount == null && Strings.isNullOrEmpty(isClose))
            throw new MissingServletRequestParameterException("[licenceCount,isClose]","[Integer,String]");
        if (licenceCount != null)
            orderConfigPo.setLicenceCount(licenceCount);
        if (!Strings.isNullOrEmpty(isClose))
            orderConfigPo.setIsClose(isClose);
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
        String decryptStr;
        try {
            decryptStr = AESUtil.aesDecrypt(activateParam,environment.getProperty("eas.key2"));
        }catch (Exception e){
            throw LogicException.le(CommonEnum.ReturnCode.SystemCode.sys_err_exception.getValue(),
                    "非法信息交换");
        }
        BoxExchangePo boxExchangePo = JSON.parseObject(decryptStr,BoxExchangePo.class);
        if (Strings.isNullOrEmpty(boxExchangePo.getKey1()))
            throw LogicException.le(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue(),
                    "订单号未提供");
        if (Strings.isNullOrEmpty(boxExchangePo.getKey2()))
            throw LogicException.le(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue(),
                    "机顶盒的ID未提供");
        //截取出key2中的sn号和时间戳
        String key2 = boxExchangePo.getKey2();
        String chipSn = key2.substring(0,18);
        String dateStr = key2.substring(18,31);
        String result = orderConfigService.checkActivate(boxExchangePo.getKey1(),chipSn,dateStr);
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
        PageInfo<ActivatePo> activatePoPageInfo = orderDataService.listActivateMachine(activateMachineQuery);
        String result = JSON.toJSONString(activatePoPageInfo);
        return result;
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
    @GetMapping("v1/listOrderCount")
    public String listOrderCount(@RequestParam(value = "companyId", required = false) String companyId,
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
}
