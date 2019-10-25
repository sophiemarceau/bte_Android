package com.btetop.bean;

import java.io.Serializable;

/**
 * Created by ouyou on 2018/5/18.
 */

public class CheckNewOldUserPwdBean extends BaseBean {
    public static class CheckNewOldUserPwdData implements Serializable {
        private boolean result;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }
}
