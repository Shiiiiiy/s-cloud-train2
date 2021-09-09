package cn.lwf.framework.train.dao;

import cn.lwf.framework.train.mapper.TrainMapper;
import cn.lwf.framework.train.model.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrainDao {

    @Autowired
    TrainMapper trainMapper;

    public List<Train> findTrainList() {
        return trainMapper.findTrainList();
    }

    public Train findTrainByCode(String trainCode) {
        return trainMapper.findTrainByCode(trainCode);
    }

    public int addTrain(Train train) {
        return trainMapper.insert(train);
    }

    public int updateTrain(Train train) {
        return trainMapper.update(train);
    }

    public void deleteTrain(Long trainId) {
        trainMapper.deleteTrain(trainId);
    }

    /**
     * @Description: 查询所有实时查询车次的可能接口
     **/
    public List<String> getTrainRealTimeInterface() {
        return trainMapper.getTrainRealTimeInterface();
    }
}
