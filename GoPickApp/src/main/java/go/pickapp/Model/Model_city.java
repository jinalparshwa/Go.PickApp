package go.pickapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 5/10/2017.
 */

public class Model_city {


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

    public static class Arealist extends Data {
        @SerializedName("areaid")
        public String areaid;

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        @SerializedName("area")
        public String area;
    }

    public static class Data {
        @SerializedName("id")
        public String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<Arealist> getArealist() {
            return arealist;
        }

        public void setArealist(List<Arealist> arealist) {
            this.arealist = arealist;
        }

        @SerializedName("city")
        public String city;
        @SerializedName("arealist")
        public List<Arealist> arealist;
    }
}
