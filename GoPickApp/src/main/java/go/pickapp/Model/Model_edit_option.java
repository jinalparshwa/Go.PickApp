package go.pickapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 7/15/2017.
 */

public class Model_edit_option {
    @SerializedName("status")
    public String status;
    @SerializedName("msg")
    public String msg;

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

    @SerializedName("data")
    public List<Data> data;

    public static class Productoptiondetail {
        @SerializedName("productoptiondetailid")
        public String productoptiondetailid;
        @SerializedName("optiondetail")
        public String optiondetail;

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

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }

        @SerializedName("rate")

        public String rate;
        @SerializedName("checked")
        public String checked;

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

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }

        public List<Productoptiondetail> getProductoptiondetail() {
            return productoptiondetail;
        }

        public void setProductoptiondetail(List<Productoptiondetail> productoptiondetail) {
            this.productoptiondetail = productoptiondetail;
        }

        @SerializedName("maxval")
        public String maxval;
        @SerializedName("optiontype")
        public String optiontype;
        @SerializedName("checked")
        public String checked;
        @SerializedName("productoptiondetail")
        public List<Productoptiondetail> productoptiondetail;
    }

    public static class Data {
        @SerializedName("restid")
        public String restid;
        @SerializedName("orderdetailid")
        public String orderdetailid;
        @SerializedName("productid")
        public String productid;

        public String getRestid() {
            return restid;
        }

        public void setRestid(String restid) {
            this.restid = restid;
        }

        public String getOrderdetailid() {
            return orderdetailid;
        }

        public void setOrderdetailid(String orderdetailid) {
            this.orderdetailid = orderdetailid;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<Productoption> getProductoption() {
            return productoption;
        }

        public void setProductoption(List<Productoption> productoption) {
            this.productoption = productoption;
        }

        @SerializedName("productname")
        public String productname;
        @SerializedName("ratetotal")
        public String ratetotal;

        public String getRatetotal() {
            return ratetotal;
        }

        public void setRatetotal(String ratetotal) {
            this.ratetotal = ratetotal;
        }

        @SerializedName("shortdesc")
        public String shortdesc;
        @SerializedName("longdesc")
        public String longdesc;
        @SerializedName("rate")
        public String rate;
        @SerializedName("qty")
        public String qty;
        @SerializedName("finalrate")
        public String finalrate;
        @SerializedName("specialinstruction")
        public String specialinstruction;

        public String getSpecialinstruction() {
            return specialinstruction;
        }

        public void setSpecialinstruction(String specialinstruction) {
            this.specialinstruction = specialinstruction;
        }

        public String getFinalrate() {
            return finalrate;
        }

        public void setFinalrate(String finalrate) {
            this.finalrate = finalrate;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        @SerializedName("image")
        public String image;
        @SerializedName("productoption")
        public List<Productoption> productoption;
    }
}
