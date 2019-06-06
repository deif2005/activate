package com.order.machine.mapper;

import com.order.machine.dto.OrderStatistics;
import com.order.machine.dto.OrderTimesCount;
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
                                         @Param("orderId") String orderId,
                                         @Param("orderDateBegin") String orderDateBegin,
                                         @Param("orderDateEnd") String orderDateEnd);

    void addOrderConfigByList(List<OrderConfigPo> orderConfigPos);

    List<OrderTimesCount> getOrderCount(@Param("orderId") String orderId,
                                        @Param("activateTimes") Integer activateTimes);

    List<OrderConfigPo> listOrderConfig(@Param("beginDate") String beginDate,
                                        @Param("endDate") String endDate,
                                        @Param("companyId") String companyId,
                                        @Param("isClose") Integer isClose);

    OrderConfigPo getOrderConfig(@Param("orderId") String orderId);
}
