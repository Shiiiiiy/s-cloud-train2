package cn.lwf.framework.train.dao;

import cn.lwf.framework.train.mapper.TrainStationMapper;
import cn.lwf.framework.train.model.TrainStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrainStationDao {

    @Autowired
    TrainStationMapper trainStationMapper;

    public List<TrainStation> findStationList() {
        return trainStationMapper.findStationList();
    }

    public TrainStation findStationByName(String stationName) {
        return trainStationMapper.findStationByName(stationName);
    }

    public TrainStation findStationById(Long id) {
        return trainStationMapper.findStationById(id);
    }

    public int addStation(TrainStation station) {
        return trainStationMapper.insert(station);
    }

    public int updateStation(TrainStation station) {
        return trainStationMapper.update(station);
    }

    public String findTelegraphCodeByName(String startStationName) {
        return trainStationMapper.findTelegraphCodeByName(startStationName);
    }

    public String findTelegraphCodeByLikeName(String startStationName) {
        return trainStationMapper.findTelegraphCodeByLikeName(startStationName);
    }
}
