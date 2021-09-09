package cn.lwf.framework.train.vo;

import lombok.Data;

/**
 * @ClassNmae lc
 * @Description
 * @Author lucheng
 * @Date 2021/9/3 16:34
 * @Version 1.0
 **/
@Data
public class ParamRequestBean {
    //出发点
    String startStationName;

    //目的地
    String endStationName;

    //查询的日期
    String queryDateTime;

    //车次
    String trainCode;
}
