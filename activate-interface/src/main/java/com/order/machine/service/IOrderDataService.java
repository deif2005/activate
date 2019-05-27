package com.order.machine.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.order.machine.dto.OrderStatistics;
import com.order.machine.po.ActivatePo;
import com.order.machine.po.CompanyPo;
import com.order.machine.po.OrderConfigPo;
import com.order.machine.query.ActivateMachineQuery;
import com.order.machine.query.OrderConfigQuery;
import com.order.machine.query.OrderStatisticsQuery;

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
}
