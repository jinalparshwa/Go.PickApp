package go.pickapp.Model;

/**
 * Created by Admin on 5/18/2017.
 */

public class Model_Cusine_filter {
    String cusine;
    String color;
    String name;
    String id;
    String filter_id;
    String filter_name;
    String filter_selection;

    public String getFilter_selection() {
        return filter_selection;
    }

    public void setFilter_selection(String filter_selection) {
        this.filter_selection = filter_selection;
    }

    public String getFilter_id() {
        return filter_id;
    }

    public void setFilter_id(String filter_id) {
        this.filter_id = filter_id;
    }

    public String getFilter_name() {
        return filter_name;
    }

    public void setFilter_name(String filter_name) {
        this.filter_name = filter_name;
    }

    public Model_Cusine_filter(String cusine, String color, String name, String id, boolean isSelected) {
        this.cusine = cusine;
        this.color = color;
        this.name = name;
        this.id = id;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean isSelected = false;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Model_Cusine_filter() {
        this.cusine = cusine;
    }

    public String getCusine() {
        return cusine;
    }

    public void setCusine(String cusine) {
        this.cusine = cusine;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
