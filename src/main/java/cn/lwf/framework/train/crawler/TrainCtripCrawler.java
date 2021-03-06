package cn.lwf.framework.train.crawler;


import cn.lwf.framework.train.constant.Constants;
import cn.lwf.framework.train.model.TrainFahrplan;
import cn.lwf.framework.train.vo.Node;
import cn.lwf.framework.train.vo.TrainInfoNew;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *  携程官网获取车次时刻表信息
 */
@Slf4j
@Component
public class TrainCtripCrawler {

    public TrainFahrplan getCtripTrainInfo(TrainFahrplan fahrplan, String trainNum){
        Document doc;
        try {
            String uri = Constants.TRAIN_CTRIP_QUERY_TSEATDETAIL + trainNum;
            log.info("携程官网爬取时刻表请求地址：{}", uri);
            doc = Jsoup.connect(uri).get();
            //获取div > ctl00_MainContentPlaceHolder_pnlResult
            Elements result = doc.select("div#ctl00_MainContentPlaceHolder_pnlResult");
            //获取div中车次信息
            Elements trainTables =  result.select("div.s_bd > table.tb_result");//tb_result
            TrainInfoNew infoNew = new TrainInfoNew();
            //检测列对应值是否正确
            Elements trainHeadArray = trainTables.get(1).select("thead").select("tr");
            Elements ths = trainHeadArray.get(0).select("th");
            String arlabl = ths.get(3).text().trim();//进站时间
            String stlabl = ths.get(4).text().trim();//发车时间
            Elements trainDataArray = trainTables.get(1).select("tbody").select("tr");
            Elements trElement = trainTables.get(0).select("tbody").select("tr");
            Elements tainInfo = trElement.get(0).select("td");
            String startStationName = tainInfo.get(1).text().trim();
            String endStationName = tainInfo.get(2).text().trim();
            infoNew.setStartStationName(startStationName);
            infoNew.setEndStationName(endStationName);
            infoNew.setTrainCode(trainNum);
            infoNew.setTrainLength(String.valueOf(trainDataArray.size()));
            ArrayList<Node> nodeList=new ArrayList<Node>();
            infoNew.setItems(nodeList);
            for(int i = 0;i<trainDataArray.size();i++){
                Elements tds = trainDataArray.get(i).select("td");
                Node node = new Node();
                int num = (i + 1);
                String serialNo  = num >= 10 ? String.valueOf(num) : "0"+num;
                node.setStationNo(serialNo);
                for(int j = 0;j<tds.size();j++){
                    String text = tds.get(j).text().trim();
                    switch (String.valueOf(j)) {
                        case "2":
                            //站名
                            node.setStationName(text);
                            break;
                        case "3":
                            //进站时间，防止进站与发车调换情况
                            if(arlabl.equals("发车时间")){
                                node.setStartTime(text);
                            }else{
                                node.setArriveTime(text);
                            }
                            break;
                        case "4":
                            //发车时间，防止进站与发车调换情况
                            if(stlabl.equals("进站时间")){
                                node.setArriveTime(text);
                            }else{
                                node.setStartTime(text);
                            }
                            break;
                        case "5":
                            //停留时间
                            if("----".equals(text)){
                                node.setOverTime(text);
                            }else{
                                node.setOverTime(text+"分钟");
                            }
                            break;
                        default:
                            break;
                    }
                }
                nodeList.add(node);
            }
            fahrplan.setTrainInfoNew(JSONObject.toJSONString(infoNew));
            log.info("携程 - 根据车次编码获取时刻表信息 --> infoNew：{}",JSONObject.toJSONString(infoNew));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("携程 - 解析错误或无法爬取到时刻表数据");
            e.printStackTrace();
        }
        return fahrplan;

    }

    //GC-高铁/城际D-动车Z-直达T-特快K-快速 其他
    private String getTrainTypeName(String trainNum){
        String prefixKey = trainNum.substring(0, 1);
        String trainClassName = "";
        if("GC".contains(prefixKey)){
            trainClassName = "高铁/城际";
        }else if("D".contains(prefixKey)){
            trainClassName = "动车";
        }else if("Z".contains(prefixKey)){
            trainClassName = "直达";
        }else if("T".contains(prefixKey)){
            trainClassName = "特快";
        }else if("K".contains(prefixKey)){
            trainClassName = "快速";
        }else {
            trainClassName = "其他";
        }
        return trainClassName;
    }
}
