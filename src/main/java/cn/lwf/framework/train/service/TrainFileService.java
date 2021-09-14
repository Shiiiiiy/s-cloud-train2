package cn.lwf.framework.train.service;

import cn.lwf.framework.train.dao.TrainFahrplanDao;
import cn.lwf.framework.train.model.TrainFahrplan;
import cn.lwf.framework.train.util.POIExcelUtil;
import cn.lwf.framework.train.vo.StationItem;
import cn.lwf.framework.train.vo.TrainInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @author shiyan
 * @date 2021/9/13 14:27
 */
@Service
public class TrainFileService {

    @Autowired
    private TrainFahrplanDao trainFahrplanDao;



    public void generateExcel() throws Exception{

        int maxStop = 2;
        List<TrainFahrplan> list = trainFahrplanDao.findList();

        List<Map<String,Object>> data = new ArrayList<>();

        for (TrainFahrplan entity : list) {

            Map<String,Object> map = new HashMap<>();
            TrainInfo trainInfo = entity.getTrainInfoObject();

            //车次
            map.put("trainCode",entity.getTrainCode());
            map.put("first",trainInfo.getStartStationName());
            map.put("last",trainInfo.getEndStationName());

            if(trainInfo.getTrainLength()>maxStop){
                maxStop = trainInfo.getTrainLength();
            }

            for (StationItem item : trainInfo.getItems()) {
                //首发站
                if(item.getStationNum()== 1){
                    map.put("firstTime",item.getStartTime());
                }
                //终点站
                else if(item.getStationNum().equals(trainInfo.getTrainLength()) ){
                    map.put("lastTime",item.getArriveTime());
                }
                //经停站
                else{
                    map.put("stop"+(item.getStationNum()-1),item.getStationName());
                    map.put("arrive"+(item.getStationNum()-1),item.getArriveTime());
                    map.put("start"+(item.getStationNum()-1),item.getStartTime());
                }
            }
            data.add(map);
        }
        System.out.println("最多有"+maxStop+"站.....");

        Map<String, Collection> hashMap1 = new HashMap<>();

        hashMap1.put("items", data);



        Workbook transformToExcel = POIExcelUtil.transformToExcel(
                hashMap1, "templates/trainInfo.xlsx");



        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

        String filePath = "D:\\trainInfo-"+System.currentTimeMillis()+".xlsx";

        FileOutputStream fos = new FileOutputStream(filePath);



        transformToExcel.write(byteOutputStream);

        fos.write(byteOutputStream.toByteArray());

        System.out.println(1111);

    }



}
