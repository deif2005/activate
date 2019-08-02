package com.order.machine.service.impl;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.order.machine.dto.*;
import com.order.machine.mapper.ActivateMapper;
import com.order.machine.mapper.CompanyMapper;
import com.order.machine.mapper.OrderConfigMapper;
import com.order.machine.po.ActivatePo;
import com.order.machine.po.CompanyPo;
import com.order.machine.po.OrderConfigPo;
import com.order.machine.query.ActivateMachineQuery;
import com.order.machine.query.OrderConfigQuery;
import com.order.machine.query.OrderStatisticsQuery;
import com.order.machine.service.IOrderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author miou
 * @date 2019-05-21
 */
@Service
public class OrderDataServiceImpl implements IOrderDataService{

    @Autowired
    OrderConfigMapper orderConfigMapper;
    @Autowired
    ActivateMapper activateMapper;
    @Autowired
    CompanyMapper companyMapper;

    /**
     * 订单配置信息列表
     * @param orderConfigQuery
     * @return
     */
    @Override
    public PageInfo<OrderConfigPo> listOrderConfig(OrderConfigQuery orderConfigQuery){
//        Example example = new Example(OrderConfigPo.class);
//        Example.Criteria criteria = example.createCriteria();
//        //如果是管理员,可以查看所有订单
//        criteria.andEqualTo("companyId",orderConfigQuery.getCompanyId());
//        criteria.andBetween("orderDate",orderConfigQuery.getBeginDate(),orderConfigQuery.getEndDate());
//        criteria.andEqualTo("isClose",orderConfigQuery.getIsClose());
        final Integer isClose = orderConfigQuery.getIsClose()==null?null:Integer.valueOf(orderConfigQuery.getIsClose());// Integer.valueOf();
        ISelect iSelect = new ISelect() {
            @Override
            public void doSelect() {
                orderConfigMapper.listOrderConfig(orderConfigQuery.getBeginDate(),orderConfigQuery.getEndDate(),
                        orderConfigQuery.getCompanyId(), isClose);
            }
        };
        PageInfo<OrderConfigPo> orderConfigPoPageInfo = PageHelper.startPage(orderConfigQuery.getPageNo(),
                orderConfigQuery.getPageSize())
                .doSelectPageInfo(iSelect);
        return orderConfigPoPageInfo;
    }

    /**
     * 查看机器激活信息
     * @param activateMachineQuery
     * @return
     */
    @Override
    public PageInfo<ActivatePo> listActivateMachine(ActivateMachineQuery activateMachineQuery){
        Example example = new Example(ActivatePo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",activateMachineQuery.getOrderId());
        criteria.andEqualTo("activateTimes",activateMachineQuery.getActivateTimes());
        example.setOrderByClause("create_time DESC");
        if (Strings.isNullOrEmpty(activateMachineQuery.getBeginDate()) &&
                Strings.isNullOrEmpty(activateMachineQuery.getEndDate())){
            criteria.andBetween("createTime",activateMachineQuery.getBeginDate(),
                    activateMachineQuery.getEndDate());
        }
        PageInfo<ActivatePo> activatePoPageInfo = PageHelper.startPage(activateMachineQuery.getPageNo(),
                activateMachineQuery.getPageSize()).doSelectPageInfo(()->activateMapper.selectByExample(example));
        return activatePoPageInfo;
    }

    /**
     * 订单激活信息表
     * @param orderStatisticsQuery
     * @return
     */
    @Override
    public PageInfo<OrderStatistics> listOrderCount(OrderStatisticsQuery orderStatisticsQuery){
        PageInfo<OrderStatistics> orderStatisticsPageInfo = PageHelper.startPage(orderStatisticsQuery.getPageNo(),
                orderStatisticsQuery.getPageSize()).doSelectPageInfo(()->orderConfigMapper.listOrderCount(
                        orderStatisticsQuery.getCompanyId(),orderStatisticsQuery.getOrderId(),
                orderStatisticsQuery.getOrderDateBegin(),orderStatisticsQuery.getOrderDateEnd()));
        return orderStatisticsPageInfo;
    }

    /**
     * 获取激活次数统计数据
     * @param orderId
     * @param companyId
     * @param page
     * @return
     */
    @Override
    public PageInfo<ActivateCountTotal> listActivateCountTotal(String orderId, String companyId,
                                                               com.order.machine.base.Page page){
        PageInfo<ActivateCountTotal> activateCountTotalPageInfo = PageHelper.startPage(page.getPageNo(),page.getPageSize()).
                doSelectPageInfo(()->orderConfigMapper.listActivateCountTotal(orderId,companyId));
        return activateCountTotalPageInfo;
    }

    /**
     * 根据激活次数获取激活明细数据
     * @param orderId
     * @param companyId
     * @param activateTimes
     * @param page
     * @return
     */
    @Override
    public PageInfo<ActivateCount> listActivateByCount(String orderId,String companyId, String chipSn,
                                                       Integer activateTimes,com.order.machine.base.Page page){
        PageInfo<ActivateCount> activateCountPageInfo = PageHelper.startPage(page.getPageNo(),page.getPageSize()).
                doSelectPageInfo(()->orderConfigMapper.listActivateByCount(activateTimes,companyId,orderId,chipSn));
        return activateCountPageInfo;
    }

    /**
     * 获取最大订单号
     * @param companyId
     * @return
     */
    @Transactional
    @Override
    public Integer getMaxOrderSn(String companyId){
        OrderConfigPo orderConfigPo = new OrderConfigPo();
        orderConfigPo.setCompanyId(companyId);
        int count = orderConfigMapper.selectCount(orderConfigPo);
        return ++count;
    }

    /**
     * 增加客户信息
     * @param companyPo
     */
    @Override
    public void addCompanyInfo(CompanyPo companyPo){
        companyMapper.insertSelective(companyPo);
    }

    @Override
    public void updateCompanyInfo(CompanyPo companyPo){
        companyMapper.updateByPrimaryKeySelective(companyPo);
    }

    @Override
    public PageInfo<CompanyPo> listCompany(Page page){
        CompanyPo companyPo = new CompanyPo();
        companyPo.setStatus("1");
        PageInfo<CompanyPo> companyPoPageInfo = PageHelper.startPage(page.getPageNum(),page.getPageSize()).
                doSelectPageInfo(()->companyMapper.select(companyPo));
        return companyPoPageInfo;
    }

    @Override
    public List<OrderTimesCount> getOrderCount(String orderId,String companyId,Integer activateTimes){
        return orderConfigMapper.getOrderCount(orderId,companyId,activateTimes);
    }

    /**
     * 根据订单号获取订单信息
     * @param orderId
     * @return
     */
    @Override
    public OrderConfigPo getOrderConfigByOrderId(String orderId){
        OrderConfigPo orderConfigPo = new OrderConfigPo();
        orderConfigPo.setOrderId(orderId);
        return orderConfigMapper.selectOne(orderConfigPo);
    }

}
