package com.btetop.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/27.
 */

public class UserLoginByPwdBean {
    private LoginData data;

    public static class LoginData implements Serializable {
        private String reset;
        private String token;
        private String tel;
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getReset() {
            return reset;
        }

        public void setReset(String reset) {
            this.reset = reset;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
