package com.btetop.bean;

public class V2DogsBean {

    /**
     * code : 0000
     * message : 处理成功
     * userToken : null
     * data : [{"name":"撸庄狗","point":5,"status":0},{"name":"波段狗","point":5,"status":0},{"name":"合约狗","point":5,"status":0}]
     */

        /**
         * name : 撸庄狗
         * point : 5
         * status : 0
         */

        private String name;
        private int point;
        private int status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
}
