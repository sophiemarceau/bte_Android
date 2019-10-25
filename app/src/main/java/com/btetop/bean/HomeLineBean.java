package com.btetop.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/23.
 */

public class HomeLineBean {

    public static class HomeLineData implements Serializable {
        private ArrayList<String> xAxis;
        private ArrayList<String> data;

        public ArrayList<String> getxAxis() {
            return xAxis;
        }

        public void setxAxis(ArrayList<String> xAxis) {
            this.xAxis = xAxis;
        }

        public ArrayList<String> getData() {
            return data;
        }

        public void setData(ArrayList<String> data) {
            this.data = data;
        }
    }
}
