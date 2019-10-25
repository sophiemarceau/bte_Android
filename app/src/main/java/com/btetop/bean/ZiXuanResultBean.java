package com.btetop.bean;

public class ZiXuanResultBean {

        /**
         * exchange : binance
         * quote : BTC
         * quoteCn : BTC
         * base : BCD
         * status : false
         */

        private String exchange;
        private String quote;
        private String quoteCn;
        private String base;
        private boolean status;

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }

        public String getQuoteCn() {
            return quoteCn;
        }

        public void setQuoteCn(String quoteCn) {
            this.quoteCn = quoteCn;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZiXuanResultBean)) return false;

        ZiXuanResultBean bean = (ZiXuanResultBean) o;

        if (exchange != null ? !exchange.equals(bean.exchange) : bean.exchange != null)
            return false;
        if (quote != null ? !quote.equals(bean.quote) : bean.quote != null) return false;
        if (quoteCn != null ? !quoteCn.equals(bean.quoteCn) : bean.quoteCn != null) return false;
        return base != null ? base.equals(bean.base) : bean.base == null;
    }

    @Override
    public int hashCode() {
        int result = exchange != null ? exchange.hashCode() : 0;
        result = 31 * result + (quote != null ? quote.hashCode() : 0);
        result = 31 * result + (quoteCn != null ? quoteCn.hashCode() : 0);
        result = 31 * result + (base != null ? base.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        return result;
    }
}
