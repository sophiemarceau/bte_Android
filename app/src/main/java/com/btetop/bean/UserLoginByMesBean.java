package com.btetop.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/2/27.
 */

public class UserLoginByMesBean {

    public static class LoginData implements Serializable {
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
    }
}
