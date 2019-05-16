package com.order.machine.model;

/**
 * @Description
 * @Author cjy
 * @Date 2019/2/16
 **/
public interface ReturnMsg {

    enum SystemMsg implements ReturnMsg{
        /**
         * 操作成功
         */
        SYS_SUCCESS("操作成功"),
        /**
         * 操作失败(未知异常)
         */
        SYS_ERR_EXCEPTION("操作失败"),
        /**
         * rpc服务器异常
         */
        SYS_ERR_RPCSERVICEERROR("rpc服务出错"),
        /**
         * 业务异常
         */
        SYS_ERR_BUSINESS_EXCEPTION("业务异常");
        private String value;

        public String getValue() {
            return value;
        }

        SystemMsg(String value) {
            this.value = value;
        }
    }
}
