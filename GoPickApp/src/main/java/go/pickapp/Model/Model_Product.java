package go.pickapp.Model;

import java.util.ArrayList;

/**
 * Created by Admin on 5/13/2017.
 */

public class Model_Product {

    String cat_id;
    String cat_name;
    ArrayList<Model_child>childlist;

    public ArrayList<Model_child> getChildlist() {
        return childlist;
    }

    public void setChildlist(ArrayList<Model_child> childlist) {
        this.childlist = childlist;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }


}
