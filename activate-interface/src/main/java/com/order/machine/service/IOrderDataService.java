package com.order.machine.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.order.machine.dto.ActivateCount;
import com.order.machine.dto.ActivateCountTotal;
import com.order.machine.dto.OrderStatistics;
import com.order.machine.dto.OrderTimesCount;
import com.order.machine.po.ActivatePo;
import com.order.machine.po.CompanyPo;
import com.order.machine.po.OrderConfigPo;
import com.order.machine.query.ActivateMachineQuery;
import com.order.machine.query.OrderConfigQuery;
import com.order.machine.query.OrderStatisticsQuery;

import java.util.List;

/**
 * @author miou
 * @date 2019-05-21
 */
public interface IOrderDataService {

    PageInfo<OrderConfigPo> listOrderConfig(OrderConfigQuery orderConfigQuery);

    PageInfo<ActivatePo> listActivateMachine(ActivateMachineQuery activateMachineQuery);

    PageInfo<OrderStatistics> listOrderCount(OrderStatisticsQuery orderStatisticsQuery);

    Integer getMaxOrderSn(String companyId);

    void addCompanyInfo(CompanyPo companyPo);

    void updateCompanyInfo(CompanyPo companyPo);

    PageInfo<CompanyPo> listCompany(Page page);

    List<OrderTimesCount> getOrderCount(String orderId,String companyId,Integer activateTimes);

    PageInfo<ActivateCountTotal> listActivateCountTotal(String orderId, String companyId,
                                                        com.order.machine.base.Page page);

    PageInfo<ActivateCount> listActivateByCount(String orderId, String companyId, String chipSn,
                                                Integer activateTimes,com.order.machine.base.Page page);

    OrderConfigPo getOrderConfigByOrderId(String orderId);
}
