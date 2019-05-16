package com.order.machine.service;

import com.github.pagehelper.PageInfo;
import com.order.machine.po.ActivatePo;
import com.order.machine.po.OrderConfigPo;
import com.order.machine.query.ActivateMachineQuery;
import com.order.machine.query.OrderConfigQuery;

/**
 * @author miou
 * @date 2019-04-17
 */
public interface IOrderConfigService {

    void importOrderConfigInfo(String filePath);

    OrderConfigPo getOrderConfig(String id);

    PageInfo<OrderConfigPo> listOrderConfig(OrderConfigQuery orderConfigQuery);

    void modifyOrderConfig(OrderConfigPo orderConfigPo);

    String checkActivate(ActivatePo activatePo);

    PageInfo<ActivatePo> listActivateMachine(ActivateMachineQuery activateMachineQuery);

    String testSelect();
}
