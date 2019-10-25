package com.btetop.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/26.
 */

public class UserWalletAdress {

    public static class DataBean implements Serializable {

        private String address;
        private String base64;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBase64() {
            return base64;
        }

        public void setBase64(String base64) {
            this.base64 = base64;
        }
    }
}
