package com.btetop.bean;

import java.util.List;

public class MiningRuleBean {

    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : ["挖矿时间为2018年10月15日至2018年11月15日","比特易将每天放入3.3BTC放入奖励池","每天8点和20:00分别释放一次挖矿奖励，用户可在此时间后手动领取挖矿奖励，领取后进入下一时段挖矿","用户每时段获得的奖励由参与该时段挖矿的时间及算力共同决定",
     * "奖励发放形式：挖矿结束后，用户获得的挖矿奖励将放入比特易区块链策略投资基金中，三个月后用户可提现收益部分(往期最高三个月最高收益60%)","比特易拥有对挖矿活动的最终解释权"]
     */

    private String code;
    private String message;
    private Object userToken;
    private List<String> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getUserToken() {
        return userToken;
    }

    public void setUserToken(Object userToken) {
        this.userToken = userToken;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
