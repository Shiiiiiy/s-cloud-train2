package cn.lwf.framework.train.mapper;


import cn.lwf.framework.train.model.TrainStation;

import java.util.List;

public interface TrainStationMapper {

    List<TrainStation> findStationList();

    TrainStation findStationByName(String stationName);

    TrainStation findStationById(Long id);

    String findTelegraphCodeByName(String stationName);

    int insert(TrainStation station);

    int update(TrainStation station);

    String findTelegraphCodeByLikeName(String startStationName);

}
