package com.btetop.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/30.
 */

public class UserInfoBean {

    public static class UserInfoData implements Serializable {
        private String tel;
        private String name;
        private String avator;
        private int reset;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public int getReset() {
            return reset;
        }

        public void setReset(int reset) {
            this.reset = reset;
        }
    }
}
