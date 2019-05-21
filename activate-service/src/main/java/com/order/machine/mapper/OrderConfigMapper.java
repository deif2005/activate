package com.order.machine.mapper;

import com.order.machine.dto.OrderStatistics;
import com.order.machine.po.OrderConfigPo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author miou
 * @date 2019-04-17
 */
public interface OrderConfigMapper extends Mapper<OrderConfigPo> {

    void updateActivateCount(@Param("orderId") String orderId);

    List<OrderStatistics> listOrderCount(@Param("companyId") String companyId,
                                         @Param("orderDateBegin") String orderDateBegin,
                                         @Param("orderDateEnd") String orderDateEnd);
}
