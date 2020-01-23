package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

public class MainActivityOutModel {
    /**
     * status : 0
     * message : 获取成功！
     * data : {"resultCout":2,"activityList":[{"activityLink":"http://985.so/g3Zr","imgUrl":"https://gw.alicdn.com/tfs/TB1qvewQQvoK1RjSZFNXXcxMVXa-1500-530.png","seq":0,"bannerName":"大额券"},{"activityLink":"http://985.so/g3Z5","imgUrl":"https://gw.alicdn.com/tfs/TB1qvewQQvoK1RjSZFNXXcxMVXa-1500-530.png","seq":0,"bannerName":"品牌券"}]}
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
         * resultCout : 2
         * activityList : [{"activityLink":"http://985.so/g3Zr","imgUrl":"https://gw.alicdn.com/tfs/TB1qvewQQvoK1RjSZFNXXcxMVXa-1500-530.png","seq":0,"bannerName":"大额券"},{"activityLink":"http://985.so/g3Z5","imgUrl":"https://gw.alicdn.com/tfs/TB1qvewQQvoK1RjSZFNXXcxMVXa-1500-530.png","seq":0,"bannerName":"品牌券"}]
         */

        private int resultCout;
        private List<ActivityListBean> activityList;

        public int getResultCout() {
            return resultCout;
        }

        public void setResultCout(int resultCout) {
            this.resultCout = resultCout;
        }

        public List<ActivityListBean> getActivityList() {
            return activityList;
        }

        public void setActivityList(List<ActivityListBean> activityList) {
            this.activityList = activityList;
        }

        public static class ActivityListBean {
            /**
             * activityLink : http://985.so/g3Zr
             * imgUrl : https://gw.alicdn.com/tfs/TB1qvewQQvoK1RjSZFNXXcxMVXa-1500-530.png
             * seq : 0
             * bannerName : 大额券
             */

            private String activityLink;
            private String imgUrl;
            private int seq;
            private String bannerName;

            public String getActivityLink() {
                return activityLink;
            }

            public void setActivityLink(String activityLink) {
                this.activityLink = activityLink;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }

            public String getBannerName() {
                return bannerName;
            }

            public void setBannerName(String bannerName) {
                this.bannerName = bannerName;
            }
        }
    }
}
