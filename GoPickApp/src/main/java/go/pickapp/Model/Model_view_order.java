package go.pickapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 7/17/2017.
 */

public class Model_view_order {
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

    public static class Paymentarray {
        @SerializedName("paymenttypeid")
        public String paymenttypeid;
        @SerializedName("paymenttype")
        public String paymenttype;

        public String getPaymenttypeid() {
            return paymenttypeid;
        }

        public void setPaymenttypeid(String paymenttypeid) {
            this.paymenttypeid = paymenttypeid;
        }

        public String getPaymenttype() {
            return paymenttype;
        }

        public void setPaymenttype(String paymenttype) {
            this.paymenttype = paymenttype;
        }

        public String getImagenm() {
            return imagenm;
        }

        public void setImagenm(String imagenm) {
            this.imagenm = imagenm;
        }

        public String getImagenmgray() {
            return imagenmgray;
        }

        public void setImagenmgray(String imagenmgray) {
            this.imagenmgray = imagenmgray;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }

        @SerializedName("imagenm")

        public String imagenm;
        @SerializedName("imagenmgray")
        public String imagenmgray;
        @SerializedName("checked")
        public String checked;
    }

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

        public String getOptionrate() {
            return optionrate;
        }

        public void setOptionrate(String optionrate) {
            this.optionrate = optionrate;
        }

        @SerializedName("optionrate")
        public String optionrate;
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
        @SerializedName("productoptiondetail")
        public List<Productoptiondetail> productoptiondetail;
    }

    public static class Product {
        @SerializedName("orderdetailid")
        public String orderdetailid;
        @SerializedName("productid")
        public String productid;
        @SerializedName("image")
        public String image;
        @SerializedName("productname")
        public String productname;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
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

        public double getProducttotal() {
            return producttotal;
        }

        public void setProducttotal(double producttotal) {
            this.producttotal = producttotal;
        }

        public List<Productoption> getProductoption() {
            return productoption;
        }

        public void setProductoption(List<Productoption> productoption) {
            this.productoption = productoption;
        }

        public String getShortdesc() {
            return shortdesc;
        }

        public void setShortdesc(String shortdesc) {
            this.shortdesc = shortdesc;
        }

        @SerializedName("qty")

        public String qty;
        @SerializedName("rate")
        public String rate;
        @SerializedName("producttotal")
        public double producttotal;
        @SerializedName("productoption")
        public List<Productoption> productoption;
        @SerializedName("shortdesc")
        public String shortdesc;
    }

    public static class Data {
        @SerializedName("customerid")
        public String customerid;
        @SerializedName("restid")
        public String restid;
        @SerializedName("orderid")
        public String orderid;
        @SerializedName("orderno")
        public String orderno;

        public String getCustomerid() {
            return customerid;
        }

        public void setCustomerid(String customerid) {
            this.customerid = customerid;
        }

        public String getRestid() {
            return restid;
        }

        public void setRestid(String restid) {
            this.restid = restid;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getOrderno() {
            return orderno;
        }

        public void setOrderno(String orderno) {
            this.orderno = orderno;
        }

        public String getRestname() {
            return restname;
        }

        public void setRestname(String restname) {
            this.restname = restname;
        }

        public String getRestaddress() {
            return restaddress;
        }

        public void setRestaddress(String restaddress) {
            this.restaddress = restaddress;
        }

        public List<Paymentarray> getPaymentarray() {
            return paymentarray;
        }

        public void setPaymentarray(List<Paymentarray> paymentarray) {
            this.paymentarray = paymentarray;
        }

        public String getPaymenttypeid() {
            return paymenttypeid;
        }

        public void setPaymenttypeid(String paymenttypeid) {
            this.paymenttypeid = paymenttypeid;
        }

        public String getPaymenttype() {
            return paymenttype;
        }

        public void setPaymenttype(String paymenttype) {
            this.paymenttype = paymenttype;
        }

        public String getOrderdate() {
            return orderdate;
        }

        public void setOrderdate(String orderdate) {
            this.orderdate = orderdate;
        }

        public String getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(String orderstatus) {
            this.orderstatus = orderstatus;
        }

        public String getOrderstatusnm() {
            return orderstatusnm;
        }

        public void setOrderstatusnm(String orderstatusnm) {
            this.orderstatusnm = orderstatusnm;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
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

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getServicefeeper() {
            return servicefeeper;
        }

        public void setServicefeeper(String servicefeeper) {
            this.servicefeeper = servicefeeper;
        }

        public String getServicefee() {
            return servicefee;
        }

        public void setServicefee(String servicefee) {
            this.servicefee = servicefee;
        }

        public String getFinaltotal() {
            return finaltotal;
        }

        public void setFinaltotal(String finaltotal) {
            this.finaltotal = finaltotal;
        }

        public List<Product> getProduct() {
            return product;
        }

        public void setProduct(List<Product> product) {
            this.product = product;
        }

        @SerializedName("restname")

        public String restname;
        @SerializedName("restaddress")
        public String restaddress;
        @SerializedName("paymentarray")
        public List<Paymentarray> paymentarray;
        @SerializedName("paymenttypeid")
        public String paymenttypeid;
        @SerializedName("paymenttype")
        public String paymenttype;
        @SerializedName("orderdate")
        public String orderdate;
        @SerializedName("orderstatus")
        public String orderstatus;
        @SerializedName("orderstatusnm")
        public String orderstatusnm;
        @SerializedName("total")
        public String total;
        @SerializedName("couponcode")
        public String couponcode;
        @SerializedName("discountper")
        public String discountper;
        @SerializedName("discount")
        public String discount;
        @SerializedName("servicefeeper")
        public String servicefeeper;
        @SerializedName("servicefee")
        public String servicefee;
        @SerializedName("finaltotal")
        public String finaltotal;
        @SerializedName("product")
        public List<Product> product;
    }
}
