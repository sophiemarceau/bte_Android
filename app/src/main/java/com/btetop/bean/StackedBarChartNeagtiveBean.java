package com.btetop.bean;

import java.util.List;

public class StackedBarChartNeagtiveBean {


    /**
     * userToken : null
     * data : {"future":{"ranges":["小于9211","9211-9436","9436-9661","9661-9886","9886-10111","10111-10336","10336-10561","10561-10786","10786-11011","大于11011"],"netList":[12530,39704,764520,110458,-1582006,-2577680,40552,0,0,8],"buy":[2364882,10594192,1.548615E7,14817784,8280884,5829140,389876,0,0,8],"sell":[2352352,10554488,1.472163E7,14707326,9862890,8406820,349324,0,0,0],"radioList":[0.0027,0.0019,0.0253,0.0037,-0.0872,-0.1811,0.0549,0,0,1]},"spot":{"ranges":["小于9066","9066-9172","9172-9278","9278-9384","9384-9490","9490-9596","9596-9702","9702-9808","9808-9914","大于9914"],"netList":[-1437.044567935127,-221.35833559470848,-1643.8882283343482,3492.9610347368,4632.273687319554,-2296.3115428078236,3250.5500345128457,4148.9616403026885,3423.4570795370055,4138.517897207104],"buy":[8292.165102279088,25067.96676694823,33084.85465342262,78695.05061900633,28987.100521044697,23540.767263063477,63510.29635675195,37026.69565775079,34369.02543973728,21755.609077137775],"sell":[9729.209670214215,25289.325102542938,34728.74288175697,75202.08958426953,24354.826833725143,25837.0788058713,60259.7463222391,32877.7340174481,30945.568360200272,17617.09117993067],"radioList":[-0.0797,-0.0044,-0.0242,0.0227,0.0868,-0.0465,0.0263,0.0594,0.0524,0.1051]}}
     */
    public static class DataBean {
        /**
         * future : {"ranges":["小于9211","9211-9436","9436-9661","9661-9886","9886-10111","10111-10336","10336-10561","10561-10786","10786-11011","大于11011"],"netList":[12530,39704,764520,110458,-1582006,-2577680,40552,0,0,8],"buy":[2364882,10594192,1.548615E7,14817784,8280884,5829140,389876,0,0,8],"sell":[2352352,10554488,1.472163E7,14707326,9862890,8406820,349324,0,0,0],"radioList":[0.0027,0.0019,0.0253,0.0037,-0.0872,-0.1811,0.0549,0,0,1]}
         * spot : {"ranges":["小于9066","9066-9172","9172-9278","9278-9384","9384-9490","9490-9596","9596-9702","9702-9808","9808-9914","大于9914"],"netList":[-1437.044567935127,-221.35833559470848,-1643.8882283343482,3492.9610347368,4632.273687319554,-2296.3115428078236,3250.5500345128457,4148.9616403026885,3423.4570795370055,4138.517897207104],"buy":[8292.165102279088,25067.96676694823,33084.85465342262,78695.05061900633,28987.100521044697,23540.767263063477,63510.29635675195,37026.69565775079,34369.02543973728,21755.609077137775],"sell":[9729.209670214215,25289.325102542938,34728.74288175697,75202.08958426953,24354.826833725143,25837.0788058713,60259.7463222391,32877.7340174481,30945.568360200272,17617.09117993067],"radioList":[-0.0797,-0.0044,-0.0242,0.0227,0.0868,-0.0465,0.0263,0.0594,0.0524,0.1051]}
         */

        private FutureBean future;
        private SpotBean spot;

        public FutureBean getFuture() {
            return future;
        }

        public void setFuture(FutureBean future) {
            this.future = future;
        }

        public SpotBean getSpot() {
            return spot;
        }

        public void setSpot(SpotBean spot) {
            this.spot = spot;
        }

        public static class FutureBean {
            private List<String> ranges;
            private List<Float> netList;
            private List<Float> buy;
            private List<Float> sell;
            private List<Float> radioList;

            public List<String> getRanges() {
                return ranges;
            }

            public List<Float> getNetList() {
                return netList;
            }

            public List<Float> getBuy() {
                return buy;
            }

            public List<Float> getSell() {
                return sell;
            }

            public List<Float> getRadioList() {
                return radioList;
            }
        }

        public static class SpotBean {
            private String[] ranges;
            private List<Float> netList;
            private List<Float> buy;
            private List<Float> sell;
            private List<Float> radioList;


            public List<Float> getNetList() {
                return netList;
            }

            public List<Float> getBuy() {
                return buy;
            }

            public List<Float> getSell() {
                return sell;
            }

            public List<Float> getRadioList() {
                return radioList;
            }

            public String[] getRanges() {
                return ranges;
            }
        }
    }
}
