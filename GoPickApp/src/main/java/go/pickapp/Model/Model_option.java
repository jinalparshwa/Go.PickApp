package go.pickapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 6/1/2017.
 */

public class Model_option {
    @SerializedName("status")
    public String status;

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

    @SerializedName("msg")

    public String msg;
    @SerializedName("data")
    public List<Data> data;

    public static class Productoptiondetail {
        public String getProductoptiondetailid() {
            return productoptiondetailid;
        }

        public void setProductoptiondetailid(String productoptiondetailid) {
            this.productoptiondetailid = productoptiondetailid;
        }

        public String getOptiondetail() {
            return optiondetail;
        }

        public void setOptiondetail(String optiondetail) {
            this.optiondetail = optiondetail;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        @SerializedName("productoptiondetailid")
        public String productoptiondetailid;
        @SerializedName("optiondetail")
        public String optiondetail;
        @SerializedName("rate")
        public String rate;

        public String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    public static class Productoption {
        @SerializedName("productoptionid")
        public String productoptionid;
        @SerializedName("productoptionname")
        public String productoptionname;
        @SerializedName("minval")
        public String minval;
        @SerializedName("maxval")
        public String maxval;

        public String getProductoptionid() {
            return productoptionid;
        }

        public void setProductoptionid(String productoptionid) {
            this.productoptionid = productoptionid;
        }

        public String getProductoptionname() {
            return productoptionname;
        }

        public void setProductoptionname(String productoptionname) {
            this.productoptionname = productoptionname;
        }

        public String getMinval() {
            return minval;
        }

        public void setMinval(String minval) {
            this.minval = minval;
        }

        public String getMaxval() {
            return maxval;
        }

        public void setMaxval(String maxval) {
            this.maxval = maxval;
        }

        public String getOptiontype() {
            return optiontype;
        }

        public void setOptiontype(String optiontype) {
            this.optiontype = optiontype;
        }

        public List<Productoptiondetail> getProductoptiondetail() {
            return productoptiondetail;
        }

        public void setProductoptiondetail(List<Productoptiondetail> productoptiondetail) {
            this.productoptiondetail = productoptiondetail;
        }

        @SerializedName("optiontype")

        public String optiontype;
        @SerializedName("productoptiondetail")
        public List<Productoptiondetail> productoptiondetail;

        public  boolean group=false;

        public boolean isGroup() {
            return group;
        }

        public void setGroup(boolean group) {
            this.group = group;
        }
    }

    public static class Data {
        @SerializedName("productid")
        public String productid;
        @SerializedName("productname")
        public String productname;
        @SerializedName("shortdesc")
        public String shortdesc;
        @SerializedName("longdesc")
        public String longdesc;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(String orderstatus) {
            this.orderstatus = orderstatus;
        }

        public List<Productoption> getProductoption() {
            return productoption;
        }

        public void setProductoption(List<Productoption> productoption) {
            this.productoption = productoption;
        }

        @SerializedName("rate")
        public String rate;
        @SerializedName("image")
        public String image;
        @SerializedName("orderstatus")
        public String orderstatus;
        @SerializedName("productoption")
        public List<Productoption> productoption;
    }
}
