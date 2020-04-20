package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

public class MainClassifyOutModel {
    /**
     * status : 0
     * message : 获取定向分类标题成功！
     * data : {"total_results":8,"results":[{"activity_title":"聚划算","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/meizhuang.png","childItem":[{"child_activity_title":"开团热卖中","child_activity_id":31371,"type":2,"img_url":""},{"child_activity_title":"预热","child_activity_id":31370,"type":2,"img_url":""}]},{"activity_title":"高佣榜","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/meishi.png","childItem":[{"child_activity_title":"综合","child_activity_id":13366,"type":2,"img_url":""},{"child_activity_title":"女装","child_activity_id":13367,"type":2,"img_url":""},{"child_activity_title":"家居家装","child_activity_id":13368,"type":2,"img_url":""},{"child_activity_title":"数码家电","child_activity_id":13369,"type":2,"img_url":""},{"child_activity_title":"鞋包配饰","child_activity_id":13370,"type":2,"img_url":""},{"child_activity_title":"美妆个护","child_activity_id":13371,"type":2,"img_url":""},{"child_activity_title":"男装","child_activity_id":13372,"type":2,"img_url":""},{"child_activity_title":"内衣","child_activity_id":13373,"type":2,"img_url":""},{"child_activity_title":"母婴","child_activity_id":13374,"type":2,"img_url":""},{"child_activity_title":"食品","child_activity_id":13375,"type":2,"img_url":""},{"child_activity_title":"运动户外","child_activity_id":13376,"type":2,"img_url":""}]},{"activity_title":"母婴主题","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/muyin.png","childItem":[{"child_activity_title":"母婴_备孕","child_activity_id":4040,"type":2,"img_url":""},{"child_activity_title":"母婴_0至6个月","child_activity_id":4041,"type":2,"img_url":""},{"child_activity_title":"母婴_7至12个月","child_activity_id":4042,"type":2,"img_url":""},{"child_activity_title":"母婴_1至3岁","child_activity_id":4043,"type":2,"img_url":""},{"child_activity_title":"母婴_4至6岁","child_activity_id":4044,"type":2,"img_url":""},{"child_activity_title":"母婴_7至12岁","child_activity_id":4045,"type":2,"img_url":""}]},{"activity_title":"有好货","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/nvzhuan.png","childItem":[{"child_activity_title":"品质好货","child_activity_id":4092,"type":2,"img_url":""}]},{"activity_title":"潮流范","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/nanzhuang.png","childItem":[{"child_activity_title":"潮流范","child_activity_id":4093,"type":2,"img_url":""}]},{"activity_title":"特惠","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/qita.png","childItem":[{"child_activity_title":"优质特惠宝贝","child_activity_id":4094,"type":2,"img_url":""}]},{"activity_title":"猫超爆款","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/qita.png","childItem":[{"child_activity_title":"猫超1元购凑单","child_activity_id":27162,"type":2,"img_url":""},{"child_activity_title":"猫超第二件0元","child_activity_id":27161,"type":2,"img_url":""},{"child_activity_title":"猫超单件满减包邮","child_activity_id":27160,"type":2,"img_url":""}]},{"activity_title":"天天特卖","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/qita.png","childItem":[{"child_activity_title":"低价好货","child_activity_id":31362,"type":2,"img_url":""}]}]}
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
         * total_results : 8
         * results : [{"activity_title":"聚划算","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/meizhuang.png","childItem":[{"child_activity_title":"开团热卖中","child_activity_id":31371,"type":2,"img_url":""},{"child_activity_title":"预热","child_activity_id":31370,"type":2,"img_url":""}]},{"activity_title":"高佣榜","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/meishi.png","childItem":[{"child_activity_title":"综合","child_activity_id":13366,"type":2,"img_url":""},{"child_activity_title":"女装","child_activity_id":13367,"type":2,"img_url":""},{"child_activity_title":"家居家装","child_activity_id":13368,"type":2,"img_url":""},{"child_activity_title":"数码家电","child_activity_id":13369,"type":2,"img_url":""},{"child_activity_title":"鞋包配饰","child_activity_id":13370,"type":2,"img_url":""},{"child_activity_title":"美妆个护","child_activity_id":13371,"type":2,"img_url":""},{"child_activity_title":"男装","child_activity_id":13372,"type":2,"img_url":""},{"child_activity_title":"内衣","child_activity_id":13373,"type":2,"img_url":""},{"child_activity_title":"母婴","child_activity_id":13374,"type":2,"img_url":""},{"child_activity_title":"食品","child_activity_id":13375,"type":2,"img_url":""},{"child_activity_title":"运动户外","child_activity_id":13376,"type":2,"img_url":""}]},{"activity_title":"母婴主题","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/muyin.png","childItem":[{"child_activity_title":"母婴_备孕","child_activity_id":4040,"type":2,"img_url":""},{"child_activity_title":"母婴_0至6个月","child_activity_id":4041,"type":2,"img_url":""},{"child_activity_title":"母婴_7至12个月","child_activity_id":4042,"type":2,"img_url":""},{"child_activity_title":"母婴_1至3岁","child_activity_id":4043,"type":2,"img_url":""},{"child_activity_title":"母婴_4至6岁","child_activity_id":4044,"type":2,"img_url":""},{"child_activity_title":"母婴_7至12岁","child_activity_id":4045,"type":2,"img_url":""}]},{"activity_title":"有好货","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/nvzhuan.png","childItem":[{"child_activity_title":"品质好货","child_activity_id":4092,"type":2,"img_url":""}]},{"activity_title":"潮流范","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/nanzhuang.png","childItem":[{"child_activity_title":"潮流范","child_activity_id":4093,"type":2,"img_url":""}]},{"activity_title":"特惠","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/qita.png","childItem":[{"child_activity_title":"优质特惠宝贝","child_activity_id":4094,"type":2,"img_url":""}]},{"activity_title":"猫超爆款","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/qita.png","childItem":[{"child_activity_title":"猫超1元购凑单","child_activity_id":27162,"type":2,"img_url":""},{"child_activity_title":"猫超第二件0元","child_activity_id":27161,"type":2,"img_url":""},{"child_activity_title":"猫超单件满减包邮","child_activity_id":27160,"type":2,"img_url":""}]},{"activity_title":"天天特卖","type":2,"img_url":"http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/qita.png","childItem":[{"child_activity_title":"低价好货","child_activity_id":31362,"type":2,"img_url":""}]}]
         */

        private long total_results;
        private List<ResultsBean> results;

        public long getTotal_results() {
            return total_results;
        }

        public void setTotal_results(long total_results) {
            this.total_results = total_results;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class ResultsBean {
            /**
             * activity_title : 聚划算
             * type : 2
             * img_url : http://test-pic-666.oss-cn-hongkong.aliyuncs.com/YouShopping/Classify/meizhuang.png
             * childItem : [{"child_activity_title":"开团热卖中","child_activity_id":31371,"type":2,"img_url":""},{"child_activity_title":"预热","child_activity_id":31370,"type":2,"img_url":""}]
             */

            private String activity_title;
            private int type;
            private String img_url;
            private List<ChildItemBean> childItem;

            public String getActivity_title() {
                return activity_title;
            }

            public void setActivity_title(String activity_title) {
                this.activity_title = activity_title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public List<ChildItemBean> getChildItem() {
                return childItem;
            }

            public void setChildItem(List<ChildItemBean> childItem) {
                this.childItem = childItem;
            }

            public static class ChildItemBean {
                /**
                 * child_activity_title : 开团热卖中
                 * child_activity_id : 31371
                 * type : 2
                 * img_url :
                 */

                private String child_activity_title;
                private long child_activity_id;
                private int type;
                private String img_url;

                public String getChild_activity_title() {
                    return child_activity_title;
                }

                public void setChild_activity_title(String child_activity_title) {
                    this.child_activity_title = child_activity_title;
                }

                public long getChild_activity_id() {
                    return child_activity_id;
                }

                public void setChild_activity_id(long child_activity_id) {
                    this.child_activity_id = child_activity_id;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getImg_url() {
                    return img_url;
                }

                public void setImg_url(String img_url) {
                    this.img_url = img_url;
                }
            }
        }
    }
}
