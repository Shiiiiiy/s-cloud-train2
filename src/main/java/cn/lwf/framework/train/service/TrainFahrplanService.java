package cn.lwf.framework.train.service;


import cn.lwf.framework.train.common.ResponseResult;
import cn.lwf.framework.train.constant.Constants;
import cn.lwf.framework.train.dao.TrainDao;
import cn.lwf.framework.train.dao.TrainFahrplanDao;
import cn.lwf.framework.train.dao.TrainStationDao;
import cn.lwf.framework.train.exception.BizException;
import cn.lwf.framework.train.gateway.TrainSyncGateway;
import cn.lwf.framework.train.model.Train;
import cn.lwf.framework.train.model.TrainFahrplan;
import cn.lwf.framework.train.util.HttpClientUtils;
import cn.lwf.framework.train.vo.ParamRequestBean;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
public class TrainFahrplanService {

    @Autowired
    TrainFahrplanDao trainFahrplanDao;

    @Autowired
    TrainStationDao trainStationDao;

    @Autowired
    TrainDao trainDao;

    @Autowired
    TrainSyncGateway trainSyncGateway;

    /**
     * 自动同步车次时刻<由定时任务调取>
     */
    @Transactional
    public void autoSyncTrainFahrplan() {
        try{
            //所有车次数据遍历
            /**
            List<Train> list =  trainDao.findTrainList();
            for (Train train : list) {
                trainSyncGateway.syncTrainFahrplan(train.getTrainCode());
            }
            **/
            //所有车次数据遍历（过滤冻结的车次）
            List<TrainFahrplan> list =  trainFahrplanDao.findFahrplanList();
            for (TrainFahrplan fahrplan : list) {
                TrainFahrplan trainFahrplan = trainSyncGateway.syncTrainFahrplan(fahrplan.getTrainCode());
                if(trainFahrplan!=null && ObjectUtils.isNotEmpty(trainFahrplan.getTrainInfoNew())){
                    fahrplan.setTrainInfoNew(trainFahrplan.getTrainInfoNew());
                    trainFahrplanDao.updateFahrplan(fahrplan);
                }else{
                    //删除没有车次信息的相关数据
                    Train train = trainDao.findTrainByCode(fahrplan.getTrainCode());
                    if(train != null){
                        trainDao.deleteTrain(train.getId());
                    }
                    trainFahrplanDao.delteTrainFahrplan(fahrplan.getId());
                }
            }
        }catch (Exception e){
            log.error("自动同步车次时刻信息数据异常：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 手动同步车次时刻<可通过前端请求接口到方法>
     */
    public void manualSyncTrainFahrplan(String trainCode) {
        try{
            trainSyncGateway.syncTrainFahrplan(trainCode);
        }catch (Exception e){
            log.error("自动同步车次时刻信息数据异常：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Map<String,String>> queryTrainList(ParamRequestBean paramBean) throws Exception {
        List<Map<String,String>> list = new ArrayList<>();
        List<String> interfaceList = trainDao.getTrainRealTimeInterface();
        String fromCode = trainStationDao.findTelegraphCodeByLikeName(paramBean.getStartStationName());
        String endCode = trainStationDao.findTelegraphCodeByLikeName(paramBean.getEndStationName());
        if(fromCode==null){
            throw new Exception("出发地名称有误，找不到匹配站");
        }
        if(endCode == null){
            throw new Exception("目的地名称有误，找不到匹配站");
        }

        StringBuffer uri = new StringBuffer(Constants.TRAIN_12306_QUERY_REALTIME);
        outer:for (String interfaceName:interfaceList) {
            uri.append(interfaceName).append("?");
            //?leftTicketDTO.train_date=2021-09-13&leftTicketDTO.from_station=SNH&leftTicketDTO.to_station=CQW&purpose_codes=ADULT
            uri.append("leftTicketDTO.train_date=").append(paramBean.getQueryDateTime());
            uri.append("&leftTicketDTO.from_station=").append(fromCode);
            uri.append("&leftTicketDTO.to_station=").append(endCode);
            uri.append("&purpose_codes=ADULT");
            log.info("12306获取实时剩余列车：{}", uri.toString());
            String resultString = HttpClientUtils.httpGet(uri.toString());
            if(StringUtils.isNotBlank(resultString)) {
                JSONObject result = JSON.parseObject(resultString);
                if(!result.isEmpty() && (result.getIntValue("httpstatus") == 200 && result.containsKey("data"))){
                    JSONObject jsonData = result.getJSONObject("data");
                    if(!jsonData.isEmpty() && jsonData.containsKey("result")){
                        JSONObject jsonMap = jsonData.getJSONObject("map");
                        JSONArray resultArray = jsonData.getJSONArray("result");
                        for(int index = 0;index < resultArray.size(); index++){
                            String data = resultArray.get(index).toString();
                            if(data.contains("|预订|") && data.contains("|Y|")){
                                Map<String,String> map = new HashMap<>();
                                int locationIndex2 = StringUtils.ordinalIndexOf(data, "|", 2);
                                int locationIndex3 = StringUtils.ordinalIndexOf(data, "|", 3);
                                int locationIndex4 = StringUtils.ordinalIndexOf(data, "|", 4);
                                int locationIndex5 = StringUtils.ordinalIndexOf(data, "|", 5);
                                int locationIndex6 = StringUtils.ordinalIndexOf(data, "|", 6);
                                int locationIndex7 = StringUtils.ordinalIndexOf(data, "|", 7);
                                int locationIndex8 = StringUtils.ordinalIndexOf(data, "|", 8);
                                int locationIndex9 = StringUtils.ordinalIndexOf(data, "|", 9);
                                int locationIndex10 = StringUtils.ordinalIndexOf(data, "|", 10);
                                int locationIndex11 = StringUtils.ordinalIndexOf(data, "|", 11);

                                String trainNo = data.substring(locationIndex2 + 1, locationIndex3); //车次编码
                                String trainCode = data.substring(locationIndex3 + 1, locationIndex4); //车次
                                String startStation = data.substring(locationIndex4 + 1, locationIndex5); //始发站
                                String endStation = data.substring(locationIndex5 + 1, locationIndex6); //终点站
                                String beginStation = data.substring(locationIndex6 + 1, locationIndex7); //出发地
                                String arriveStation = data.substring(locationIndex7 + 1, locationIndex8); //目的地
                                String beginTime = data.substring(locationIndex8 + 1, locationIndex9); //开始时间
                                String endTime = data.substring(locationIndex9 + 1, locationIndex10); //结束时间
                                String overTime = data.substring(locationIndex10 + 1, locationIndex11); //时长
                                map.put("trainNo",trainNo);
                                map.put("trainCode",trainCode);
                                map.put("startStation",startStation);
                                map.put("endStation",endStation);
                                map.put("beginStation",beginStation);
                                map.put("arriveStation",arriveStation);
                                map.put("beginTime",beginTime);
                                map.put("endTime",endTime);
                                map.put("overTime",overTime);
                                list.add(map);
                            }
                        }
                        break outer;
                    }
                }
            }
        }
        return list;
    }

    public String queryTrainTime(ParamRequestBean paramBean) throws Exception {
        String trainCode = paramBean.getTrainCode();
        TrainFahrplan fahrplanByCode = trainFahrplanDao.findFahrplanByCode(trainCode);
        if(fahrplanByCode==null || ObjectUtils.isEmpty(fahrplanByCode.getTrainInfoNew())){
            throw new Exception("找不到车次对应时刻数据，请联系开发人员");
        }
        return fahrplanByCode.getTrainInfoNew();
    }
}
