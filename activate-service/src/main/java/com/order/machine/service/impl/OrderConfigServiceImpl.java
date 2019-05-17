package com.order.machine.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.order.machine.common_const.CommonEnum;
import com.order.machine.exception.LogicException;
import com.order.machine.mapper.ActivateMapper;
import com.order.machine.mapper.OrderConfigMapper;
import com.order.machine.po.ActivatePo;
import com.order.machine.po.OrderConfigPo;
import com.order.machine.query.ActivateMachineQuery;
import com.order.machine.query.OrderConfigQuery;
import com.order.machine.service.IOrderConfigService;
import com.wd.encrypt.AESUtil;
import com.wd.encrypt.MD5Utils;
import com.wd.util.DateUtil;
import com.wd.util.VertifyCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.security.auth.login.LoginException;
import java.util.UUID;

/**
 * @author miou
 * @date 2019-04-17
 */
@Service
public class OrderConfigServiceImpl implements IOrderConfigService {

    @Autowired
    OrderConfigMapper orderConfigMapper;
    @Autowired
    ActivateMapper activateMapper;

    /**
     * 导入订单配置信息
     * @param filePath
     */
    public void importOrderConfigInfo(String filePath){
        OrderConfigPo orderConfigPo = new OrderConfigPo();
        orderConfigPo.setId(UUID.randomUUID().toString());
        orderConfigPo.setOrderId("tempOrderId001");
        orderConfigPo.setLicenceCount("10");
        orderConfigPo.setSalt(VertifyCodeUtil.getRandromNum());
        //导入时自动生成该订单批次的授权KEY(订单号+盐值)
        String md5Str = MD5Utils.getMD5(orderConfigPo.getOrderId()+orderConfigPo.getSalt());
        orderConfigPo.setLicenceKey(md5Str);
        orderConfigMapper.insertSelective(orderConfigPo);
    }

    /**
     * 获取订单配置信息
     * @param id
     * @return
     */
    public OrderConfigPo getOrderConfig(String id){
        OrderConfigPo orderConfigPo = new OrderConfigPo();
        orderConfigPo.setId(id);
        OrderConfigPo result = orderConfigMapper.selectByPrimaryKey(orderConfigPo);
        return result;
    }

    /**
     * 订单配置信息列表
     * @param orderConfigQuery
     * @return
     */
    public PageInfo<OrderConfigPo> listOrderConfig(OrderConfigQuery orderConfigQuery){
        Example example = new Example(OrderConfigPo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andBetween("createTime",orderConfigQuery.getBeginDate(),orderConfigQuery.getEndDate());
        criteria.andEqualTo("isClose",orderConfigQuery.getIsClose());
        PageInfo<OrderConfigPo> orderConfigPoPageInfo = PageHelper.startPage(orderConfigQuery.getPageNo(),
                orderConfigQuery.getPageSize())
                .doSelectPageInfo(()-> orderConfigMapper.selectByExample(example));
        return orderConfigPoPageInfo;
    }

    /**
     * 修改订单配置信息
     * @param orderConfigPo
     */
    public void modifyOrderConfig(OrderConfigPo orderConfigPo){
        orderConfigMapper.updateByPrimaryKeySelective(orderConfigPo);
    }

    /**
     * 授权
     * @param activatePo
     * @return
     */
    @Transactional
    public String checkActivate(ActivatePo activatePo){
        String licenceKey = verifyOrderId(activatePo.getOrderId());
        if (!Strings.isNullOrEmpty(licenceKey)){
            String activateKey = verifyChipSn(activatePo.getOrderId(),activatePo.getChipSn(),licenceKey);
            return activateKey;
        }
        return "";
    }

    /**
     * 验证机器码信息
     * @param orderId
     * @param chipSn
     * @param licenceKey
     * @return
     */
    private String verifyChipSn(String orderId, String chipSn, String licenceKey){
        String aesKey="";
        ActivatePo activatePo = new ActivatePo();
        activatePo.setChipSn(chipSn);
        activatePo.setOrderId(orderId);
        ActivatePo rt = activateMapper.selectOne(activatePo);
        if (null == rt){
            activatePo.setId(UUID.randomUUID().toString());
            //新增
            activateMapper.insertSelective(activatePo);
            orderConfigMapper.updateActivateCount(orderId);
        }else {
            activatePo.setId(rt.getId());
            activatePo.setUpdateTime(DateUtil.getDateTime());
            activateMapper.updateByPrimaryKeySelective(activatePo);
        }
        try {
            //获取授权加密信息
            aesKey = AESUtil.aesEncrypt(activatePo.getChipSn(),licenceKey);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return aesKey;
    }

    /**
     * 验证订单信息
     * @param orderId
     * @return
     */
    private String verifyOrderId(String orderId){
        String result = "";
        OrderConfigPo orderConfigPo = new OrderConfigPo();
        orderConfigPo.setOrderId(orderId);
        orderConfigPo.setIsClose("1");
        //判断激活总数小于可激活总数
        Example example = new Example(OrderConfigPo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        criteria.andEqualTo("isClose","1");
        criteria.andCondition("activate_count < licence_count");
        OrderConfigPo rt = orderConfigMapper.selectOneByExample(example);
//        OrderConfigPo rt = orderConfigMapper.selectOne(orderConfigPo);
        if (null != rt)
            result = rt.getLicenceKey();
        return result;
    }

    /**
     * 查看机器激活信息
     * @param activateMachineQuery
     * @return
     */
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

    public String testSelect(){
        return orderConfigMapper.testSelect();
    }
}
