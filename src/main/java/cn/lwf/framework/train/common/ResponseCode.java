package cn.lwf.framework.train.common;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * 响应码
 *
 * @author zz@flyzz.net
 * @version $Id: ResponseCode.java v 0.1 2017/8/12 上午12:50 zz@flyzz.net Exp $$
 */
public interface ResponseCode {

    /**
     * 获取码
     *
     * @return
     */
    @Getter
    @Setter
    int getCode();

    /**
     * 获取消息
     *
     * @return
     */
    @Getter
    @Setter
    String getMessage();
}
