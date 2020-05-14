package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

public class TeamMemberOutModel {
    /**
     * status : 0
     * message : 获取一级下线成功！
     * data : {"dataModels":[{"mobile":"63778401","nick_name":"妹小妹","head_img":"http://m.imeitou.com/uploads/allimg/2019031709/eoyjh4zwlxd.jpg","sex":0,"relationId":2360417743,"invite_code":"WQKIRV"}],"totalCount":1000}
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
         * dataModels : [{"mobile":"63778401","nick_name":"妹小妹","head_img":"http://m.imeitou.com/uploads/allimg/2019031709/eoyjh4zwlxd.jpg","sex":0,"relationId":2360417743,"invite_code":"WQKIRV"}]
         * totalCount : 1000
         */

        private int totalCount;
        private List<DataModelsBean> dataModels;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<DataModelsBean> getDataModels() {
            return dataModels;
        }

        public void setDataModels(List<DataModelsBean> dataModels) {
            this.dataModels = dataModels;
        }

        public static class DataModelsBean {
            /**
             * mobile : 63778401
             * nick_name : 妹小妹
             * head_img : http://m.imeitou.com/uploads/allimg/2019031709/eoyjh4zwlxd.jpg
             * sex : 0
             * relationId : 2360417743
             * invite_code : WQKIRV
             */

            private String mobile;
            private String nick_name;
            private String head_img;
            private int sex;
            private long relationId;
            private String invite_code;

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public long getRelationId() {
                return relationId;
            }

            public void setRelationId(long relationId) {
                this.relationId = relationId;
            }

            public String getInvite_code() {
                return invite_code;
            }

            public void setInvite_code(String invite_code) {
                this.invite_code = invite_code;
            }
        }
    }
}
