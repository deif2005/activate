package com.order.machine.common_const;

/**
 * Created by defi on 2016/11/15.
 */
public enum CommonEnum {
    test(DBData.DataStatus.class);

    private DBData[] values;

    private CommonEnum(Class<? extends DBData> kind) {
        values = kind.getEnumConstants();
    }

    public interface DBData {//数据相关状态
        enum DataStatus implements DBData {
            normal("1"), delete("2"), other("3") {
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private DataStatus(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public boolean isRest() {
                return false;
            }
        }
    }

    /**
     * 题目类型编号
     */
    public interface QuestionType {
        enum UsiType implements QuestionType{
            typeNone("0"),typeSelect("1"),typeJudge("2"),typeSubjectivity("100"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;
            private UsiType(String value) {
                this.value = value;
            }
            public String getValue() {
                return value;
            }
            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }
            public boolean isRest() {
                return false;
            }
        }
        enum LabelType implements QuestionType{
            typeNone("0"), //未定义
            typeConcept("1"), //概念测试题
            typeExercises("2"){//练习题
                @Override
                public boolean isRest(){return true;}
            };
            private String value;
            LabelType(String value) {this.value = value;}
            public String getValue() {
                return value;
            }
            public Integer getIntegerVal(){return Integer.parseInt(value);}
            public boolean isRest(){return false;}
        }
        enum Status implements QuestionType{
            //重复导包后给status状态赋值为3  
            repeatDelete("3"),delete("2"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;
            Status(String value) { this.value = value;}
            public String getValue() { return value; }
            public Integer getIntegerVal(){return Integer.parseInt(value);}
            public boolean isRest() {
                return false;
            }
        }
    }

    //业务状态值
    public interface Status {
        enum importStatus implements Status {
            notImport("0"), imported("1") {
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private importStatus(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public boolean isRest() {
                return false;
            }
        }

        //审核状态
        enum reviewStatus implements Status {
            noReview("0"), allowed("1"), notAllowed("2") {
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private reviewStatus(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }

    public interface ReturnCode {
        /***********************系统级code枚举类型定义****************/
        //系统相关错误CODE
        enum SystemCode implements ReturnCode {
            //消息代码说明:
            sys_ok("0"), //0,操作成功;
            sys_err_exception("0999"), //0999,操作失败(未知异常)
            sys_err_noauth("0001"),//0001,您没有该项操作的权利;
            sys_err_tokeninvalid("0002"), //0002,token信息已失效
            sys_err_paramerror("0003"), //0003,参数错误
            sys_err_argumenttype("0004"),  //参数类型错误
            sys_err_uploadAudio("0005"),  //音频上传失败
            sys_err_uploadFile("0006"),   //文件上传失败
            sys_err_noaudiofile("0007"),  //未发现音频文件
            sys_err_noimagefile("0008"),  //未发现图片文件
            sys_err_businessException("0009") //业务异常统一代号
                    {
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private SystemCode(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }

        /***********************业务code枚举类型定义*****************/
        //用户登录反馈信息
        enum UserLoginCode implements ReturnCode {
            //该用户未注册
            user_login_UserNotExists("1001"),
            //验证码错误
            user_login_sms_code_error("1002"),
            //用户登录信息已过期,请重新登录
            user_login_overdue_error("1003"),
            //修改手机号码失败
            user_err_updateMobile("1004"),
            //验证码错误
            user_err_code("1005"),
            //此手机已被使用
            user_err_data("1006"),
            //密码错误
            user_password_error("1007"),
            //用户已存在
            user_already_exists("1008"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private UserLoginCode(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }

    public interface ReturnMsg {
        /***********************系统级msg枚举类型定义****************/
        //系统相关错误msg
        enum SystemMsg implements ReturnMsg {
            //消息代码说明:
            //0,操作成功;
            //0001,您没有该项操作的权利;
            //0002,token信息已失效
            //0003,参数错误
            //0999,操作失败
            sys_ok("操作成功"),
            sys_err_exception("操作失败"),
            sys_err_noauth("您没有该项操作的权利"),
            sys_err_tokeninvalid("用户未登录"),
            sys_err_paramerror("参数错误"),
            sys_err_rpcserviceerror("rpc服务出错"),
            sys_err_uploadAudio("上传音频失败"),
            sys_err_uploadFile("上传文件失败"),
            sys_err_noaudiofile("请上传音频"),
            sys_err_noimagefile("请上传图片"),
            sys_err_businessException("业务异常"){
                @Override
                public boolean isRest() {
                    return true;
                }
            };
            private String value;

            private SystemMsg(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }

            public Integer getIntegerVal() {
                return Integer.parseInt(value);
            }

            public boolean isRest() {
                return false;
            }
        }
    }
}
