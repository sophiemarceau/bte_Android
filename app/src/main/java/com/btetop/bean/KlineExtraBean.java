package com.btetop.bean;

import java.util.List;

public class KlineExtraBean {
    private KlineVolumeBean volume;
    private List<AbnormalOrderBean> abnormity;
    private ResistanceBean resistance;

    public KlineVolumeBean getVolume() {
        return volume;
    }

    public void setVolume(KlineVolumeBean volume) {
        this.volume = volume;
    }



    public ResistanceBean getResistance() {
        return resistance;
    }

    public void setResistance(ResistanceBean resistance) {
        this.resistance = resistance;
    }
    public List<AbnormalOrderBean> getAbnormity() {
        return abnormity;
    }

    public void setAbnormity(List<AbnormalOrderBean> abnormity) {
        this.abnormity = abnormity;
    }
}
