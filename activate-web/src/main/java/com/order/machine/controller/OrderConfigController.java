package com.order.machine.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.order.machine.StringUtils;
import com.order.machine.common_const.CommonEnum;
import com.order.machine.dto.OrderStatistics;
import com.order.machine.exception.LogicException;
import com.order.machine.httputils.HttpApiService;
import com.order.machine.httputils.HttpResult;
import com.order.machine.po.ActivatePo;
import com.order.machine.po.BoxExchangePo;
import com.order.machine.po.OrderConfigPo;
import com.order.machine.query.ActivateMachineQuery;
import com.order.machine.query.OrderConfigQuery;
import com.order.machine.query.OrderStatisticsQuery;
import com.order.machine.service.IOrderConfigService;
import com.order.machine.service.IOrderDataService;
import com.wd.encrypt.AESUtil;
import com.wd.util.DateUtil;
import com.wd.util.UUIDGenerator;
import jdk.nashorn.internal.parser.Token;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author miou
 * @date 2019-04-17
 */
@RestController
@RequestMapping(value = "/order")
public class OrderConfigController {

    @Autowired
    IOrderConfigService orderConfigService;
    @Autowired
    IOrderDataService orderDataService;
    @Autowired
    private Environment environment;
    @Resource
    private HttpApiService httpAPIService;

    /**
     * 添加订单信息
     * @param companyId
     * @param chipSn
     * @param licenceCount
     * @param dateStr
     * @param key1
     * @return
     */
    @PostMapping("/v1/addOrderConfig")
    public void addOrderConfig(@RequestParam("companyId") String companyId,
                               @RequestParam("chipSn") String chipSn,
                               @RequestParam("licenceCount") Integer licenceCount,
                               @RequestParam("dateStr") String dateStr,
                               @RequestParam(value = "key1",required = false) String key1){
        StringBuilder sb = new StringBuilder();
        companyId = StringUtils.completeFixCode(companyId,3);
        sb.append(companyId).append("_").append(chipSn).append("_").append(dateStr).append("_");
        if (Strings.isNullOrEmpty(key1)){
            key1 = UUIDGenerator.getUUID();
        }
        Integer sn = orderDataService.getMaxOrderSn(companyId);
        String s = StringUtils.completeFixCode(String.valueOf(sn),4);
        sb.append(s);
        //上传文档
        orderConfigService.addOrderConfigInfo(sb.toString(),companyId,licenceCount,dateStr,key1);
    }

    /**
     * 更新订单配置
     * @param orderId
     * @param isClose
     * @return
     */
    @PostMapping("v1/updateOrderConfig")
    public void updateOrderConfig(@RequestParam("orderId") String orderId,
                                  @RequestParam(value="licenceCount", required = false) Integer licenceCount,
                                  @RequestParam(value="isClose",required = false) String isClose) throws
            MissingServletRequestParameterException{
        OrderConfigPo orderConfigPo = new OrderConfigPo();
        orderConfigPo.setOrderId(orderId);//.setId(id);
        if (licenceCount == null && Strings.isNullOrEmpty(isClose))
            throw new MissingServletRequestParameterException("[licenceCount,isClose]","[Integer,String]");
        if (licenceCount != null)
            orderConfigPo.setLicenceCount(licenceCount);
        if (!Strings.isNullOrEmpty(isClose))
            orderConfigPo.setIsClose(isClose);
        orderConfigService.modifyOrderConfig(orderConfigPo);
    }

    /**
     * 激活机器
     * @param activateParam
     * @return
     */
    @PostMapping("v1/activateMachine")
    public String activateMachine(@RequestParam("activateParam") String activateParam){
        String decryptStr;
        try {
            decryptStr = AESUtil.aesDecrypt(activateParam,environment.getProperty("eas.key2"));
        }catch (Exception e){
            throw LogicException.le(CommonEnum.ReturnCode.SystemCode.sys_err_exception.getValue(),
                    "非法信息交换");
        }
        BoxExchangePo boxExchangePo = JSON.parseObject(decryptStr,BoxExchangePo.class);
        if (Strings.isNullOrEmpty(boxExchangePo.getKey1()))
            throw LogicException.le(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue(),
                    "订单号未提供");
        if (Strings.isNullOrEmpty(boxExchangePo.getKey2()))
            throw LogicException.le(CommonEnum.ReturnCode.SystemCode.sys_err_paramerror.getValue(),
                    "机顶盒的ID未提供");
        String result = orderConfigService.checkActivate(boxExchangePo.getKey1(),boxExchangePo.getKey2(),
                boxExchangePo.getKey3());
        return result;
    }

//    public static void main(String[] args) {
////        System.out.println(System.currentTimeMillis());
//        try {
//            String result = AESUtil.aesEncrypt("{\"key1\":\"001_11223344_20190603_0001\"," +
//                            "\"key2\":\"12345678AABBCCDD\",\"key3\":\"1559545501619\"}",
//                    "ea87587081ed11e9b0987c7a915348fe");
//            System.out.println(result);
//        } catch (Exception e){
//
//        }
//    }

//    @RequestMapping("httpclient")
//    public String test() throws Exception {
//        String str = httpAPIService.doGet("http://www.baidu.com");
//        System.out.println(str);
//        return "hello";
//    }

//    @RequestMapping("batchTestHttp")
//    public String batchTestHttp(@RequestParam("threadNum") int threadNum,
//                                @RequestParam("execCount") int execCount,
//                                HttpServletRequest request) {
//        final String url = "http://192.168.15.61:8080/order/v1/activateMachine";
//        Map<String,String> headMap = new HashMap<>();
//        headMap.put("token",request.getHeader("token"));
//        headMap.put("userName",request.getHeader("userName"));
//        StringBuilder sb = new StringBuilder();
////                .append("key1").append(":").append("001_11223344_20190603_0001").
////                append("key2").append(":");
//        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
//        try {
//            for (int i=1;i<=execCount;i++){
//                String chipSn = StringUtils.completeFixCode(String.valueOf(i),3);
//                sb.append("{").append("\"key1\"").append(":").append("\"001_12345678AABBCCDDEE_20190529_0001\",").append("\"key2\"").append(":");
//                sb.append("\"12345678AABB").append(chipSn+"\"").append(",\"key3\"").append(":").
//                        append("\""+System.currentTimeMillis()+"\"").append("}");
//                Map<String,Object> hashMap = new HashMap<>();
//                String aesChipSn = AESUtil.aesEncrypt(sb.toString(),"ea87587081ed11e9b0987c7a915348fe");
//                sb.setLength(0);
//                hashMap.put("activateParam",aesChipSn);
//                executorService.execute(()->{
//                    try {
//                        System.out.println(JSON.toJSONString(httpAPIService.doPost(url,hashMap,headMap)));
////                        System.out.println("thread: "+Thread.currentThread().getName()+" chipSn: "+chipSn+" activateParam: "+aesChipSn);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                });
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "";
//    }
}
