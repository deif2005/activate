package com.order.machine.model;

public interface ReturnCode {

    enum SystemCode implements ReturnCode{
        /**
         * 操作成功
         */
        SYS_SUCCESS("0"),
        /**
         * 操作失败(未知异常)
         */
        SYS_ERR_EXCEPTION("0999"),
        /**
         * rpc服务器异常
         */
        SYS_ERR_RPCSERVICEERROR("0998"),
        /**
         * 业务异常统一代号
         */
        SYS_ERR_BUSINESS_EXCEPTION("0997");
        private String value;

        public String getValue() {
            return value;
        }

        SystemCode(String value) {
            this.value = value;
        }
    }
}
