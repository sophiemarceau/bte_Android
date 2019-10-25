package com.btetop.bean;

import java.io.Serializable;

public class UpdateInfoEntity {


    /**
     * url : http://test.huangjinhezi.com/android/app-release.apk
     * message : 小伙伴强力，版本能上线了
     * version : V1.0.1
     * is_update : 0
     * is_open : 0
     */

    public static class UpdateInfoData implements Serializable {
        private String currentVersion;//当前版本号
        private String desc;//0：当前版本号与服务器版本号一致 1：服务器版本号高 -1：客服端版本号高
        private String force;//0:强制升级 1：非强制升级
        private String name;//升级标题
        private String update;//升级标签 多用“|”分割
        private String url;//升级地址
        private boolean trade;//充值按钮开关

        public boolean getTrade() {
            return trade;
        }

        public void setTrade(boolean trade) {
            this.trade = trade;
        }

        public String getCurrentVersion() {
            return currentVersion;
        }

        public void setCurrentVersion(String currentVersion) {
            this.currentVersion = currentVersion;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getForce() {
            return force;
        }

        public void setForce(String force) {
            this.force = force;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}