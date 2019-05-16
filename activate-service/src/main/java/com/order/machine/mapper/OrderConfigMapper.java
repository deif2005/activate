package com.order.machine.mapper;

import com.order.machine.po.OrderConfigPo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author miou
 * @date 2019-04-17
 */
public interface OrderConfigMapper extends Mapper<OrderConfigPo> {

    void updateActivateCount(@Param("orderId") String orderId);

    String testSelect();
}
