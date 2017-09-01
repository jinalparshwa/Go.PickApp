package go.pickapp.Model;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Admin on 5/29/2017.
 */

public class Model_pass implements Serializable {
    String product_id;
    int qty;
    String rate;
    String total_rate;
    String payment_id;
    String product_name;
    String paymant_name;

    public String getPaymant_name() {
        return paymant_name;
    }

    public void setPaymant_name(String paymant_name) {
        this.paymant_name = paymant_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotal_rate() {
        return total_rate;
    }

    public void setTotal_rate(String total_rate) {
        this.total_rate = total_rate;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }
}
