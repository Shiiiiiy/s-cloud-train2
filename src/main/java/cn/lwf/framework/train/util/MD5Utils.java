/**
 * qjbaoxian.com Inc.
 * Copyright (C) 2016-2017 All Rights Reserved.
 */
package cn.lwf.framework.train.util;

import cn.lwf.framework.train.enums.CommonErrorCodeEnum;
import cn.lwf.framework.train.exception.BizException;
import org.springframework.util.DigestUtils;

/**
 * MD5工具
 *
 * @author summer
 * @version $Id MD5Utils.java, v 0.1 2017-09-14 下午10:10 summer Exp $$
 */
public class MD5Utils {

    /**
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        try {
            return DigestUtils.md5DigestAsHex(str.getBytes());
        } catch (Exception e) {
            throw new BizException(CommonErrorCodeEnum.SYSTEM_ERROR.getCode(), "MD5加密出现错误", e);
        }
    }
}
