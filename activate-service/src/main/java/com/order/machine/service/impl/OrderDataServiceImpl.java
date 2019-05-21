package com.order.machine.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.order.machine.dto.OrderStatistics;
import com.order.machine.mapper.ActivateMapper;
import com.order.machine.mapper.OrderConfigMapper;
import com.order.machine.po.ActivatePo;
import com.order.machine.po.OrderConfigPo;
import com.order.machine.query.ActivateMachineQuery;
import com.order.machine.query.OrderConfigQuery;
import com.order.machine.query.OrderStatisticsQuery;
import com.order.machine.service.IOrderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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

    /**
     * 订单配置信息列表
     * @param orderConfigQuery
     * @return
     */
    @Override
    public PageInfo<OrderConfigPo> listOrderConfig(OrderConfigQuery orderConfigQuery){
        Example example = new Example(OrderConfigPo.class);
        Example.Criteria criteria = example.createCriteria();
        //如果是管理员,可以查看所有订单
        criteria.andEqualTo("companyId",orderConfigQuery.getCompanyId());
        criteria.andBetween("createTime",orderConfigQuery.getBeginDate(),orderConfigQuery.getEndDate());
        criteria.andEqualTo("isClose",orderConfigQuery.getIsClose());
        PageInfo<OrderConfigPo> orderConfigPoPageInfo = PageHelper.startPage(orderConfigQuery.getPageNo(),
                orderConfigQuery.getPageSize())
                .doSelectPageInfo(()-> orderConfigMapper.selectByExample(example));
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
                        orderStatisticsQuery.getCompanyId(),orderStatisticsQuery.getOrderDateBegin(),
                orderStatisticsQuery.getOrderDateEnd()));
        return orderStatisticsPageInfo;
    }
}
