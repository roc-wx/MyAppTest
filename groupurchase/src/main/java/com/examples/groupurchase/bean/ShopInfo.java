package com.examples.groupurchase.bean;

import android.graphics.Bitmap;

import java.util.List;

public class ShopInfo {

    /**
     * status : 1
     * data : [{"id":1,"name":"牛排","img":"http://www.imooc.com/data/shopping/img/niu.png","count":44,"price":"39.9","description":"超值单人套餐，10分钟极速配送","action":"新用户一元购"},{"id":2,"name":"地三鲜","img":"http://www.imooc.com/data/shopping/img/san.png","count":20,"price":"99.9","description":"营养搭配，科学膳食组合","action":"新用户一元购"},{"id":3,"name":"松仁大虾","img":"http://www.imooc.com/data/shopping/img/xia.png","count":99,"price":"12","description":"在这里可以尝尽各种美味","action":"新用户一元购"},{"id":4,"name":"冷饮","img":"http://www.imooc.com/data/shopping/img/lengying.png","count":20,"price":"9.9","description":"体验冷热酸甜想吃就吃的感觉","action":"新用户一元购"},{"id":5,"name":"牛排","img":"http://www.imooc.com/data/shopping/img/niu.png","count":44,"price":"39.9","description":"超值单人套餐，10分钟极速配送","action":"新用户一元购"},{"id":6,"name":"地三鲜","img":"http://www.imooc.com/data/shopping/img/san.png","count":20,"price":"99.9","description":"营养搭配，科学膳食组合","action":"新用户一元购"}]
     * msg : 成功
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 牛排
         * img : http://www.imooc.com/data/shopping/img/niu.png
         * count : 44
         * price : 39.9
         * description : 超值单人套餐，10分钟极速配送
         * action : 新用户一元购
         */

        private int id;
        private String name;
        private String img;
        private Bitmap imgDownload;
        private int count;
        private String price;
        private String description;
        private String action;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Bitmap getImgDownload() {
            return imgDownload;
        }

        public void setImgDownload(Bitmap imgDownload) {
            this.imgDownload = imgDownload;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
}
