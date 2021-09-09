package cn.lwf.framework.train.crawler;


import cn.lwf.framework.train.constant.Constants;
import cn.lwf.framework.train.model.TrainFahrplan;
import cn.lwf.framework.train.util.HttpClientUtils;
import cn.lwf.framework.train.vo.Node;
import cn.lwf.framework.train.vo.TrainInfoNew;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *  去哪儿官网获取车次时刻表信息
 */
@Slf4j
@Component
public class TrainQunaerCrawler {

    public TrainFahrplan getQunarTrainInfo(TrainFahrplan trainFahrplan, String trainNum, String departure, String arrive, String date){
        try{
            StringBuffer uri = new StringBuffer(Constants.TRAIN_QUNAR_QUERY_TSEATDETAIL);
            uri.append("?dptStation=").append(departure);
            uri.append("&arrStation=").append(arrive);
            uri.append("&date=").append(date);
            uri.append("&trainNo=").append(trainNum);
            uri.append("&user=").append("neibu");
            uri.append("&source=").append("www");
            uri.append("&needTimeDetail=").append("true");
            log.info("去哪儿官网获取时刻表请求地址：{}", uri.toString());
            String jsonStr = "{}";
            //根据车次编码获取时刻表信息
            String resultString = HttpClientUtils.httpPost(uri.toString(),jsonStr);
            if(StringUtils.isNotBlank(resultString)) {
                JSONObject result = JSON.parseObject(resultString);
                if (!result.isEmpty() && (result.getIntValue("httpstatus") == 200 && result.containsKey("data"))) {
                    JSONObject dataJsonObject = result.getJSONObject("data");
                    if(dataJsonObject.containsKey("stationItemList")){
                        String typeName = dataJsonObject.getString("typeName");
                        JSONArray array = dataJsonObject.getJSONArray("stationItemList");
                        int size = array.size();
                        TrainInfoNew infoNew = new TrainInfoNew();
                        infoNew.setTrainCode(trainNum);
                        infoNew.setTrainLength(String.valueOf(size));
                        ArrayList<Node> nodeList = new ArrayList<Node>();
                        infoNew.setItems(nodeList);
                        String stationName = "";
                        int index=1;
                        Iterator iterator = array.iterator();
                        while (iterator.hasNext()) {
                            JSONObject jsonObject = (JSONObject) iterator.next();
                            stationName = jsonObject.getString("stationName");
                            String startTime = jsonObject.getString("startTime");
                            String arriveTime = jsonObject.getString("arriveTime");
                            Integer overTime = jsonObject.getInteger("overTime");
                            Node node = new Node();
                            if(index == 1){
                                infoNew.setStartStationName(stationName);
                                node.setArriveTime("----");
                                node.setOverTime("----");
                                node.setStartTime(startTime);
                            }else{
                                //处理arriveTime
                                node.setArriveTime(arriveTime);
                                node.setStartTime(startTime);
                                node.setOverTime(overTime+"分钟");
                            }
                            if(index < 10){
                                node.setStationNo("0"+index);
                            }else{
                                node.setStationNo(String.valueOf(index));
                            }
                            node.setStationName(stationName);
                            nodeList.add(node);
                            if(index == size){
                                node.setOverTime("----");
                                node.setStartTime(arriveTime);
                                infoNew.setEndStationName(stationName);
                            }
                            index++;
                        }
                        trainFahrplan.setTrainInfoNew(JSONObject.toJSONString(infoNew));
                        log.info("qunaer - 根据车次编码获取时刻表信息 --> infoNew：{}",JSONObject.toJSONString(infoNew));
                    }
                }
            }
        }catch(Exception ex){
            log.error("解析错误或无法爬取到数据");
            ex.printStackTrace();
        }

        return trainFahrplan;
    }
}
