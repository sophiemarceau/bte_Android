package com.btetop.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/11.
 */

public class MyQrCodeBean {

    public static class MyQrCodeData implements Serializable {
        private String base64;
        private String tel;
        private String url;
        private String imageUrl;
        private String inviteCode;

        public String getBase64() {
            return base64;
        }

        public void setBase64(String base64) {
            this.base64 = base64;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }
    }
}
