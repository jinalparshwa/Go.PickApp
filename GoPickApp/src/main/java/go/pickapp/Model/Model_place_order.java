package go.pickapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 6/27/2017.
 */

public class Model_place_order {


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

        @SerializedName("rate")
        public String rate;
    }

    public static class Productoption {
        @SerializedName("productoptionid")
        public String productoptionid;
        @SerializedName("productoptionname")
        public String productoptionname;

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

        public List<Productoptiondetail> getProductoptiondetail() {
            return productoptiondetail;
        }

        public void setProductoptiondetail(List<Productoptiondetail> productoptiondetail) {
            this.productoptiondetail = productoptiondetail;
        }

        @SerializedName("productoptiondetail")
        public List<Productoptiondetail> productoptiondetail;
    }

    public static class Product {
        @SerializedName("productid")
        public String productid;
        @SerializedName("productname")
        public String productname;
        @SerializedName("shortdesc")
        public String shortdesc;
        @SerializedName("thumb")
        public String thumb;
        @SerializedName("qty")
        public String qty;

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

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public List<Productoption> getProductoption() {
            return productoption;
        }

        public void setProductoption(List<Productoption> productoption) {
            this.productoption = productoption;
        }

        public String getProductoptionstring() {
            return productoptionstring;
        }

        public void setProductoptionstring(String productoptionstring) {
            this.productoptionstring = productoptionstring;
        }

        @SerializedName("rate")
        public String rate;
        @SerializedName("subtotal")
        public String subtotal;
        @SerializedName("productoption")
        public List<Productoption> productoption;
        @SerializedName("productoptionstring")
        public String productoptionstring;
        @SerializedName("specialinstruction")
        public String specialinstruction;

        public String getSpecialinstruction() {
            return specialinstruction;
        }

        public void setSpecialinstruction(String specialinstruction) {
            this.specialinstruction = specialinstruction;
        }
    }

    public static class Data {
        @SerializedName("restid")
        public String restid;
        @SerializedName("cartid")
        public String cartid;
        @SerializedName("userid")
        public String userid;

        public String getRestid() {
            return restid;
        }

        public void setRestid(String restid) {
            this.restid = restid;
        }

        public String getCartid() {
            return cartid;
        }

        public void setCartid(String cartid) {
            this.cartid = cartid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public List<Product> getProduct() {
            return product;
        }

        public void setProduct(List<Product> product) {
            this.product = product;
        }

        public String getGrandTotal() {
            return grandTotal;
        }

        public void setGrandTotal(String grandTotal) {
            this.grandTotal = grandTotal;
        }

        public String getCouponcode() {
            return couponcode;
        }

        public void setCouponcode(String couponcode) {
            this.couponcode = couponcode;
        }

        public String getDiscountper() {
            return discountper;
        }

        public void setDiscountper(String discountper) {
            this.discountper = discountper;
        }

        public String getDiscountamt() {
            return discountamt;
        }

        public void setDiscountamt(String discountamt) {
            this.discountamt = discountamt;
        }

        public String getCartpagetotal() {
            return cartpagetotal;
        }

        public void setCartpagetotal(String cartpagetotal) {
            this.cartpagetotal = cartpagetotal;
        }

        public String getServiceper() {
            return serviceper;
        }

        public void setServiceper(String serviceper) {
            this.serviceper = serviceper;
        }

        public String getServiceamt() {
            return serviceamt;
        }

        public void setServiceamt(String serviceamt) {
            this.serviceamt = serviceamt;
        }

        public String getFinaltotal() {
            return finaltotal;
        }

        public void setFinaltotal(String finaltotal) {
            this.finaltotal = finaltotal;
        }

        @SerializedName("product")
        public List<Product> product;
        @SerializedName("grandTotal")
        public String grandTotal;
        @SerializedName("couponcode")
        public String couponcode;
        @SerializedName("discountper")
        public String discountper;
        @SerializedName("discountamt")
        public String discountamt;
        @SerializedName("cartpagetotal")
        public String cartpagetotal;
        @SerializedName("serviceper")
        public String serviceper;
        @SerializedName("serviceamt")
        public String serviceamt;

        public String getRestname() {
            return restname;
        }

        public void setRestname(String restname) {
            this.restname = restname;
        }

        @SerializedName("finaltotal")

        public String finaltotal;
        @SerializedName("restname")
        public String restname;
    }
}
