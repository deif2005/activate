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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

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

    public static String xlsFile = ".*xls\\b";
    public static String xlsxFile = ".*xlsx\\b";

    /**
     * 导入订单配置信息
     * @param filePath
     */
    @Override
    public void importOrderConfigInfo(String filePath){
//        OrderConfigPo orderConfigPo = new OrderConfigPo();
//        orderConfigPo.setId(UUID.randomUUID().toString());
//        orderConfigPo.setCompanyId("1");
//        orderConfigPo.setOrderId("tempOrderId001");
//        orderConfigPo.setLicenceCount("10");
//        orderConfigPo.setSalt(VertifyCodeUtil.getRandromNum());
//        //导入时自动生成该订单批次的授权KEY(订单号+盐值)
//        String md5Str = MD5Utils.getMD5(orderConfigPo.getOrderId()+orderConfigPo.getSalt());
//        orderConfigPo.setLicenceKey(md5Str);
//        orderConfigMapper.insertSelective(orderConfigPo);
        importOrderExcelFile(filePath);
    }

    private void importOrderExcelFile(String filePath){
        File excel = new File(filePath);
        if (!excel.exists()){
            throw LogicException.le(CommonEnum.ReturnCode.SystemCode.sys_err_exception.getValue(),
                    "文件不存在");
        }
        Workbook wb;
        try {
            if (Pattern.matches(xlsFile,excel.getName())){
                FileInputStream fis = new FileInputStream(excel);   //文件流对象
                wb = new HSSFWorkbook(fis);
            }else if (Pattern.matches(xlsxFile,excel.getName())){
                wb = new XSSFWorkbook(excel);
            }else {
                System.out.println("文件类型错误!");
                return;
            }
            Sheet sheet = wb.getSheetAt(0);
            int firstRowIndex = sheet.getFirstRowNum()+1;
            int lastRowIndex = sheet.getLastRowNum();
            List<OrderConfigPo> orderConfigPos = new ArrayList<>();
            for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                Row row = sheet.getRow(rIndex);
                if (row != null) {
                    OrderConfigPo orderConfigPo = new OrderConfigPo(row.getCell(0).toString(),row.getCell(1).toString(),
                            row.getCell(2).toString(),row.getCell(3).toString());
                    orderConfigPo.setId(UUID.randomUUID().toString());
                    orderConfigPo.setOrderDate(orderConfigPo.getOrderId());
                    getOrderCompanyAndDate(orderConfigPo);
                    orderConfigPos.add(orderConfigPo);
                }
            }
            orderConfigMapper.addOrderConfigByList(orderConfigPos);
        }catch (IOException e){
            e.printStackTrace();
        }catch (InvalidFormatException e){
            e.printStackTrace();
        }
    }

    private void getOrderCompanyAndDate(OrderConfigPo orderConfigPo){
        //"12345678_01_1905_0001".split("_");
        String[] strings = orderConfigPo.getOrderId().split("_");
        orderConfigPo.setCompanyId(strings[1]);
        String d ="20"+strings[2].substring(0,2)+"-"+strings[2].substring(2,4)+"-"+"01";
        orderConfigPo.setOrderDate(d);
    }

    /**
     * 获取订单配置信息
     * @param id
     * @return
     */
    @Override
    public OrderConfigPo getOrderConfig(String id){
        OrderConfigPo orderConfigPo = new OrderConfigPo();
        orderConfigPo.setId(id);
        OrderConfigPo result = orderConfigMapper.selectByPrimaryKey(orderConfigPo);
        return result;
    }

    /**
     * 修改订单配置信息
     * @param orderConfigPo
     */
    @Override
    public void modifyOrderConfig(OrderConfigPo orderConfigPo){
        orderConfigMapper.updateByPrimaryKeySelective(orderConfigPo);
    }

    /**
     * 授权
     * @param activatePo
     * @return
     */
    @Transactional
    @Override
    public String checkActivate(ActivatePo activatePo){
        String licenceKey = verifyOrderId(activatePo.getOrderId());
        if (!Strings.isNullOrEmpty(licenceKey)){
            String activateKey = verifyChipSn(activatePo.getOrderId(), activatePo.getChipSn(), licenceKey);
            return activateKey;
        }
        return "";
    }

    /**
     * 验证订单信息
     * @param orderId
     * @return
     */
    private String verifyOrderId(String orderId){
        String result = "";
        Example example = new Example(OrderConfigPo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",orderId);
        criteria.andEqualTo("isClose","1");
        //判断激活总数小于可激活总数
        criteria.andCondition("activate_count < licence_count");
        OrderConfigPo rt = orderConfigMapper.selectOneByExample(example);
        if (null != rt)
            result = rt.getLicenceKey();
        return result;
    }

    /**
     * 验证机器码信息
     * @param orderId
     * @param chipSn
     * @param licenceKey
     * @return
     */
    private String verifyChipSn(String orderId, String chipSn, String licenceKey){
        String aesKey;
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

    public static void main(String[] args) {
        String[] strings = "12345678_01_1905_0001".split("_");// orderConfigPo.getOrderId().split("_");
        System.out.println(strings[1]);
        String d ="20"+strings[2].substring(0,2)+"-"+strings[2].substring(2,4)+"-"+"01";
        System.out.println(d);
    }
}
