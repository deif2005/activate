package com.order.machine.ResultHandle;

import java.lang.annotation.*;

/**
 * @author miou
 * @date 2019-05-23
 * 不需转换的标准返回值的注解
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRestReturn {
}
