package go.pickapp.Model;

/**
 * Created by Admin on 5/16/2017.
 */

public class Model_time_payment {
    String hours;
    String day;
    String payment_id;
    String payment_type;
    String image;
    String image_grey;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_grey() {
        return image_grey;
    }

    public void setImage_grey(String image_grey) {
        this.image_grey = image_grey;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }
}
