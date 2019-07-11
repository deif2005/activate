package com.order.machine.service;

import com.github.pagehelper.PageInfo;
import com.order.machine.dto.OrderConfigDto;
import com.order.machine.po.ActivatePo;
import com.order.machine.po.BoxExchangePo;
import com.order.machine.po.OrderConfigPo;
import com.order.machine.query.ActivateMachineQuery;
import com.order.machine.query.OrderConfigQuery;

/**
 * @author miou
 * @date 2019-04-17
 */
public interface IOrderConfigService {

    void addOrderConfigInfo(String orderId,String companyId,Integer licenceCount,String dateStr,String key1);

    OrderConfigDto getOrderConfig(String id);

    void modifyOrderConfig(OrderConfigPo orderConfigPo);

    String checkActivate(String orderId,String chipSn,String dateStr,String sIv);

    String checkActivate1(String orderId,String chipSn,String dateStr);

}
