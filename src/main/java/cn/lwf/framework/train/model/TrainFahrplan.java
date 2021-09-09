package cn.lwf.framework.train.model;

public class TrainFahrplan {

    private Long id; //编码
    private String trainCode; //车次',
    private Integer isFrozen; //是否冻结',
    private String frozenReason; //冻结原因',
    private Integer lengthType; //车厢长度，1 - 16车厢,2 - 24车厢,3 - 8车厢',
    private Integer isDirect; //是否直连，0 - 否，1 - 是',
    private Integer splitType; //分割类型，0 - 无断隔，1 - 有断隔',
    private String trainInfoNew; //车次时刻信息(树状节点)',
    private String trainInfo; //车次时刻信息(一级节点展示)',
    private Integer trainCompanyId; //归属公司ID',
    private String trainSmall; //小号火车,由大号接管.',
    private String trainNo; //车次编码',
    private String trainInfoUpdateTime; //车次时刻更新时间',
    private String createTime; //创建时间',

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public Integer getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(Integer isFrozen) {
        this.isFrozen = isFrozen;
    }

    public String getFrozenReason() {
        return frozenReason;
    }

    public void setFrozenReason(String frozenReason) {
        this.frozenReason = frozenReason;
    }

    public Integer getLengthType() {
        return lengthType;
    }

    public void setLengthType(Integer lengthType) {
        this.lengthType = lengthType;
    }

    public Integer getIsDirect() {
        return isDirect;
    }

    public void setIsDirect(Integer isDirect) {
        this.isDirect = isDirect;
    }

    public Integer getSplitType() {
        return splitType;
    }

    public void setSplitType(Integer splitType) {
        this.splitType = splitType;
    }

    public String getTrainInfoNew() {
        return trainInfoNew;
    }

    public void setTrainInfoNew(String trainInfoNew) {
        this.trainInfoNew = trainInfoNew;
    }

    public String getTrainInfo() {
        return trainInfo;
    }

    public void setTrainInfo(String trainInfo) {
        this.trainInfo = trainInfo;
    }

    public Integer getTrainCompanyId() {
        return trainCompanyId;
    }

    public void setTrainCompanyId(Integer trainCompanyId) {
        this.trainCompanyId = trainCompanyId;
    }

    public String getTrainSmall() {
        return trainSmall;
    }

    public void setTrainSmall(String trainSmall) {
        this.trainSmall = trainSmall;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getTrainInfoUpdateTime() {
        return trainInfoUpdateTime;
    }

    public void setTrainInfoUpdateTime(String trainInfoUpdateTime) {
        this.trainInfoUpdateTime = trainInfoUpdateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
