package com.btetop.message;

public class MessageUpdateShequ {

    //0 分享 1 评论 2 点赞
    public final int type;
    public final int dataPosition;//在数组的那个位置

    public MessageUpdateShequ(int type, int dataPosition) {
        this.type = type;
        this.dataPosition = dataPosition;
    }

    @Override
    public String toString() {
        return "MessageUpdateShequ{" +
                "type=" + type +
                ", dataPosition=" + dataPosition +
                '}';
    }
}
