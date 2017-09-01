package go.pickapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 6/1/2017.
 */

public class Model_option_child {
    @SerializedName("status")
    public String status;
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
        @SerializedName("minval")
        public String minval;
        @SerializedName("maxval")
        public String maxval;
        @SerializedName("optiontype")
        public String optiontype;
        @SerializedName("productoptiondetail")
        public List<Productoptiondetail> productoptiondetail;
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
