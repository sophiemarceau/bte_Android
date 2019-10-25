package com.btetop.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/11.
 */

public class InviteFriendsBean {


    public static class InviteFriendsData implements Serializable {
        private String tel;
        private String date;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}
