package cn.lwf.framework.train.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author shiyan
 * @date 2021/9/10 16:34
 */
@Data
public class TrainInfo {

    private String startStationName;
    private String trainCode;
    private Integer trainLength;
    private String endStationName;

    private List<StationItem>  items;




    public static void main(String[] args) {

        String s =  "{\"endStationName\":\"长治东\",\"items\":[{\"arriveTime\":\"16:58\",\"overTime\":\"----\",\"startTime\":\"16:58\",\"stationName\":\"北京西\",\"stationNo\":\"01\"},{\"arriveTime\":\"17:28\",\"overTime\":\"2分钟\",\"startTime\":\"17:30\",\"stationName\":\"高碑店东\",\"stationNo\":\"02\"},{\"arriveTime\":\"18:17\",\"overTime\":\"3分钟\",\"startTime\":\"18:20\",\"stationName\":\"石家庄\",\"stationNo\":\"03\"},{\"arriveTime\":\"19:42\",\"overTime\":\"8分钟\",\"startTime\":\"19:50\",\"stationName\":\"太原南\",\"stationNo\":\"04\"},{\"arriveTime\":\"20:03\",\"overTime\":\"2分钟\",\"startTime\":\"20:05\",\"stationName\":\"晋中\",\"stationNo\":\"05\"},{\"arriveTime\":\"20:19\",\"overTime\":\"2分钟\",\"startTime\":\"20:21\",\"stationName\":\"太谷东\",\"stationNo\":\"06\"},{\"arriveTime\":\"20:38\",\"overTime\":\"2分钟\",\"startTime\":\"20:40\",\"stationName\":\"榆社西\",\"stationNo\":\"07\"},{\"arriveTime\":\"20:54\",\"overTime\":\"2分钟\",\"startTime\":\"20:56\",\"stationName\":\"武乡\",\"stationNo\":\"08\"},{\"arriveTime\":\"21:15\",\"overTime\":\"2分钟\",\"startTime\":\"21:17\",\"stationName\":\"襄垣东\",\"stationNo\":\"09\"},{\"arriveTime\":\"21:33\",\"overTime\":\"----\",\"startTime\":\"21:33\",\"stationName\":\"长治东\",\"stationNo\":\"10\"}],\"startStationName\":\"北京西\",\"trainCode\":\"G681\",\"trainLength\":\"10\"}";

        TrainInfo trainInfos = JSONObject.parseObject(s,TrainInfo.class);




    }

}

