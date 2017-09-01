package go.pickapp.Model;

import java.io.Serializable;

/**
 * Created by Admin on 5/31/2017.
 */

public class Model_order {

    String order_no;
    String total;
    String date;
    String res_name;
    String status;
    String order_id;
    String status_name;
    String msg;
    String review_status;
    String reorder_status;
    String cancel_status;
    String dialogmsg;
    String lat;
    String lang;
    String payment_type;
    String availability;
    String avilbe_status;

    public String getAvilbe_status() {
        return avilbe_status;
    }

    public void setAvilbe_status(String avilbe_status) {
        this.avilbe_status = avilbe_status;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getDialogmsg() {
        return dialogmsg;
    }

    public void setDialogmsg(String dialogmsg) {
        this.dialogmsg = dialogmsg;
    }

    public String getReorder_status() {
        return reorder_status;
    }

    public void setReorder_status(String reorder_status) {
        this.reorder_status = reorder_status;
    }

    public String getCancel_status() {
        return cancel_status;
    }

    public void setCancel_status(String cancel_status) {
        this.cancel_status = cancel_status;
    }

    public String getReview_status() {
        return review_status;
    }

    public void setReview_status(String review_status) {
        this.review_status = review_status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
