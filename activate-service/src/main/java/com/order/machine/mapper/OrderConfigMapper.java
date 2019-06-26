package com.order.machine.mapper;

import com.github.pagehelper.PageInfo;
import com.order.machine.dto.*;
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
                                        @Param("companyId") String companyId,
                                        @Param("activateTimes") Integer activateTimes);

    List<OrderConfigDto> listOrderConfig(@Param("beginDate") String beginDate,
                                         @Param("endDate") String endDate,
                                         @Param("companyId") String companyId,
                                         @Param("isClose") Integer isClose);

    OrderConfigDto getOrderConfig(@Param("orderId") String orderId);

    List<ActivateCount> listActivateByCount(@Param("activateTimes") Integer activateTimes,
                                            @Param("companyId") String companyId,
                                            @Param("orderId") String orderId,
                                            @Param("chipSn") String chipSn);

    List<ActivateCountTotal> listActivateCountTotal(@Param("orderId") String orderId,
                                                    @Param("companyId") String companyId);
}
