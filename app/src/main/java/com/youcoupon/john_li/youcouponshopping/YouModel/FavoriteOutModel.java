package com.youcoupon.john_li.youcouponshopping.YouModel;

import java.util.List;

/**
 * Created by John_Li on 2/4/2019.
 */

public class FavoriteOutModel {

    /**
     * status : 0
     * message :
     * data : {"total_results":2,"results":[{"favorites_title":"數碼家電","favorites_id":17487441,"type":1},{"favorites_title":"女裝","favorites_id":17481407,"type":1}]}
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
         * total_results : 2
         * results : [{"favorites_title":"數碼家電","favorites_id":17487441,"type":1},{"favorites_title":"女裝","favorites_id":17481407,"type":1}]
         */

        private int total_results;
        private List<Favorites> results;

        public int getTotal_results() {
            return total_results;
        }

        public void setTotal_results(int total_results) {
            this.total_results = total_results;
        }

        public List<Favorites> getResults() {
            return results;
        }

        public void setResults(List<Favorites> results) {
            this.results = results;
        }

        public static class Favorites {
            /**
             * favorites_title : 數碼家電
             * favorites_id : 17487441
             * type : 1
             */

            private String favorites_title;
            private int favorites_id;
            private int type;

            public String getFavorites_title() {
                return favorites_title;
            }

            public void setFavorites_title(String favorites_title) {
                this.favorites_title = favorites_title;
            }

            public int getFavorites_id() {
                return favorites_id;
            }

            public void setFavorites_id(int favorites_id) {
                this.favorites_id = favorites_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
