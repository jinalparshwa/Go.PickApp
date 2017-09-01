package go.pickapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 5/15/2017.
 */

public class Model_child {

    @SerializedName("status")
    public String status;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    public List<Data> data;
    String product_id;
    String product_name;
    String longdesc;
    String shortdesc;
    String rate;
    String thumb;
    String img;



    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static class Menu {
        @SerializedName("productid")
        public String productid;
        @SerializedName("productname")
        public String productname;
        @SerializedName("shortdesc")
        public String shortdesc;
        @SerializedName("longdesc")
        public String longdesc;
        @SerializedName("rate")
        public String rate;
        @SerializedName("thumb")
        public String thumb;
        @SerializedName("image")
        public String image;
    }

    public static class Product {
        @SerializedName("categoryid")
        public String categoryid;
        @SerializedName("category")
        public String category;
        @SerializedName("menu")
        public List<Menu> menu;
    }

    public static class Data {
        @SerializedName("restid")
        public String restid;
        @SerializedName("name")
        public String name;
        @SerializedName("cuisine")
        public String cuisine;
        @SerializedName("logo")
        public String logo;
        @SerializedName("rating")
        public String rating;
        @SerializedName("preparationtime")
        public String preparationtime;
        @SerializedName("product")
        public List<Product> product;
    }
}
