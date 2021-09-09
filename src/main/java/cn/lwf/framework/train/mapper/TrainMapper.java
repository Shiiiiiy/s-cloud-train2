package cn.lwf.framework.train.mapper;


import cn.lwf.framework.train.model.Train;

import java.util.List;

public interface TrainMapper {

    List<Train> findTrainList();

    Train findTrainByCode(String trainCode);

    int insert(Train train);

    int update(Train train);

    void deleteTrain(Long trainId);

    List<String> getTrainRealTimeInterface();
}
