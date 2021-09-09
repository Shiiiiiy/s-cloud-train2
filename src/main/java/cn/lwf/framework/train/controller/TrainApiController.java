package cn.lwf.framework.train.controller;

import cn.lwf.framework.train.common.ResponseResult;
import cn.lwf.framework.train.dao.TrainFahrplanDao;
import cn.lwf.framework.train.service.TrainFahrplanService;
import cn.lwf.framework.train.vo.ParamRequestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassNmae lc
 * @Description
 * @Author lucheng
 * @Date 2021/9/3 16:26
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/trainApi")
public class TrainApiController {
    @Autowired
    TrainFahrplanService trainFahrplanService;

    @PostMapping("/queryTrainList")
    public ResponseResult queryTrainList(@RequestBody ParamRequestBean paramBean){
        try{
            List<Map<String,String>> list = trainFahrplanService.queryTrainList(paramBean);
            return new ResponseResult().ok(list);
        } catch (Exception e) {
            log.error("12306-无法爬取到实时剩余车次数据");
            e.printStackTrace();
            return new ResponseResult().error("系统异常,请稍候再试!"+e.getMessage());
        }
    }

    @PostMapping("/queryTrainTime")
    public ResponseResult queryTrainTime(@RequestBody ParamRequestBean paramBean){
        try{
            String jsonStr = trainFahrplanService.queryTrainTime(paramBean);
            return new ResponseResult().ok(jsonStr);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return new ResponseResult().error("系统异常,请稍候再试!"+e.getMessage());
        }
    }

}
