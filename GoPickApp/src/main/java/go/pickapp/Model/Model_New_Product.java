package go.pickapp.Model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_New_Product {


    @SerializedName("status")
    private String status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private List<Data> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Menu {
        @SerializedName("productid")
        private String productid;
        @SerializedName("productname")
        private String productname;
        @SerializedName("shortdesc")
        private String shortdesc;
        @SerializedName("longdesc")
        private String longdesc;
        @SerializedName("rate")
        private String rate;
        @SerializedName("thumb")
        private String thumb;
        @SerializedName("image")
        private String image;
        @SerializedName("swipestatus")
        private String swipestatus;

        public String getSwipestatus() {
            return swipestatus;
        }

        public void setSwipestatus(String swipestatus) {
            this.swipestatus = swipestatus;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getShortdesc() {
            return shortdesc;
        }

        public void setShortdesc(String shortdesc) {
            this.shortdesc = shortdesc;
        }

        public String getLongdesc() {
            return longdesc;
        }

        public void setLongdesc(String longdesc) {
            this.longdesc = longdesc;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class Product {
        @SerializedName("categoryid")
        private String categoryid;
        @SerializedName("category")
        private String category;
        @SerializedName("menu")
        private List<Menu> menu;

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<Menu> getMenu() {
            return menu;
        }

        public void setMenu(List<Menu> menu) {
            this.menu = menu;
        }
    }

    public static class Data {
        @SerializedName("restid")
        private String restid;
        @SerializedName("name")
        private String name;
        @SerializedName("availability")
        private String availability;
        @SerializedName("availabilitystr")
        private String availabilitystr;
        @SerializedName("cuisine")
        private String cuisine;
        @SerializedName("currentdatetime")
        private String currentdatetime;
        @SerializedName("availabilitystatus")
        private String availabilitystatus;

        public String getAvailabilitystatus() {
            return availabilitystatus;
        }

        public void setAvailabilitystatus(String availabilitystatus) {
            this.availabilitystatus = availabilitystatus;
        }

        public String getCurrentdatetime() {
            return currentdatetime;
        }

        public void setCurrentdatetime(String currentdatetime) {
            this.currentdatetime = currentdatetime;
        }

        public String getAvailabilitystr() {
            return availabilitystr;
        }

        public void setAvailabilitystr(String availabilitystr) {
            this.availabilitystr = availabilitystr;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        @SerializedName("logo")

        private String logo;
        @SerializedName("rating")
        private String rating;
        @SerializedName("preparationtime")
        private String preparationtime;
        @SerializedName("product")
        private List<Product> product;

        public String getRestid() {
            return restid;
        }

        public void setRestid(String restid) {
            this.restid = restid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCuisine() {
            return cuisine;
        }

        public void setCuisine(String cuisine) {
            this.cuisine = cuisine;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getPreparationtime() {
            return preparationtime;
        }

        public void setPreparationtime(String preparationtime) {
            this.preparationtime = preparationtime;
        }

        public List<Product> getProduct() {
            return product;
        }

        public void setProduct(List<Product> product) {
            this.product = product;
        }
    }
}
