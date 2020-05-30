package com.youcoupon.john_li.youcouponshopping.YouModel;

public class SysOperationOutModel {
    /**
     * status : 0
     * message : 查询成功
     * data : {"id":4,"fcategory":"1","fdesc":"618大促！超级红包","fvalue":"https://m.tb.cn/h.VOS9L1E"}
     */

    private int status;
    private String message;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 4
         * fcategory : 1
         * fdesc : 618大促！超级红包
         * fvalue : https://m.tb.cn/h.VOS9L1E
         */

        private int id;
        private String fcategory;
        private String fdesc;
        private String fvalue;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFcategory() {
            return fcategory;
        }

        public void setFcategory(String fcategory) {
            this.fcategory = fcategory;
        }

        public String getFdesc() {
            return fdesc;
        }

        public void setFdesc(String fdesc) {
            this.fdesc = fdesc;
        }

        public String getFvalue() {
            return fvalue;
        }

        public void setFvalue(String fvalue) {
            this.fvalue = fvalue;
        }
    }
}
