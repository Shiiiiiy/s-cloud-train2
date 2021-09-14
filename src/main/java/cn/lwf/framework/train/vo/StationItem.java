package cn.lwf.framework.train.vo;

import lombok.Data;

/**
 * @author shiyan
 * @date 2021/9/10 16:31
 */
@Data
public class StationItem {

    private String startTime;
    private String arriveTime;
    private String stationName;
    private String stationNo;
    private Integer stationNum;


    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
        this.stationNum = Integer.valueOf(stationNo);
    }
}
