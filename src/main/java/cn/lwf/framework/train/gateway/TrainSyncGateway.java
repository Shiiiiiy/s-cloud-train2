package cn.lwf.framework.train.gateway;

import cn.lwf.framework.train.crawler.Train12306Crawler;
import cn.lwf.framework.train.crawler.TrainCtripCrawler;
import cn.lwf.framework.train.crawler.TrainQunaerCrawler;
import cn.lwf.framework.train.mapper.TrainMapper;
import cn.lwf.framework.train.mapper.TrainStationMapper;
import cn.lwf.framework.train.model.Train;
import cn.lwf.framework.train.model.TrainFahrplan;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class TrainSyncGateway {

    @Autowired
    TrainMapper trainMapper;
    @Autowired
    TrainStationMapper trainStationMapper;
    @Autowired
    Train12306Crawler train12306Crawler;
    @Autowired
    TrainCtripCrawler trainCtripCrawler;
    @Autowired
    TrainQunaerCrawler trainQunaerCrawler;

    /**
     *  优先爬携程，再12306，其次去哪儿
     * @param trainCode
     * @return
     */
    public TrainFahrplan syncTrainFahrplan(String trainCode){
        Train train = trainMapper.findTrainByCode(trainCode);
        TrainFahrplan trainFahrplan = new TrainFahrplan();
        if(train == null){
            //如果没有更新到车次表，直接爬取携程数据
//            trainCtripCrawler.getCtripTrainInfo(trainFahrplan, trainCode);
            //没有爬取到数据或解析异常直接返回
            return trainFahrplan;
        }
        String startStationName = train.getStartStationName();
        String endStationName = train.getEndStationName();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fromCode = trainStationMapper.findTelegraphCodeByName(startStationName);
        if(fromCode == null){
            fromCode = "";
        }
        String endCode = trainStationMapper.findTelegraphCodeByName(endStationName);
        if(endCode == null){
            endCode = "";
        }
        String rundate = train.getRunDate();
        if(StringUtils.isNotBlank(rundate)){
            rundate = format.format(cal.getTime());
        }
        //优先去携程网站抓取
        trainCtripCrawler.getCtripTrainInfo(trainFahrplan, trainCode);
        if(!validateData(trainFahrplan)){
            //没有抓到直接爬取12306数据
            trainFahrplan = new TrainFahrplan();
            train12306Crawler.get12306TrainInfo(trainFahrplan, trainCode, train.getTrainNo(), fromCode, endCode, rundate);
//            if(validateData(trainFahrplan)){
//                //没有抓到直接爬取去哪儿数据（已经废弃）
//                trainFahrplan = new TrainFahrplan();
//                trainQunaerCrawler.getQunarTrainInfo(trainFahrplan, trainCode, startStationName, endStationName, rundate);
//            }
        }
        return trainFahrplan;
    }

    private Boolean validateData(TrainFahrplan trainFahrplan) {
        String trainInfoNew = trainFahrplan.getTrainInfoNew();
        Boolean isNull = false;
        if(StringUtils.isNotBlank(trainInfoNew)){
            JSONObject jsonObject = JSONObject.parseObject(trainInfoNew);
            String length = jsonObject.getString("trainLength");
            if(Integer.parseInt(length) == 0){
                isNull = true;
            }
        }
        if(StringUtils.isBlank(trainInfoNew)||isNull){
            return  false;
        }
        return  true;
    }
}
