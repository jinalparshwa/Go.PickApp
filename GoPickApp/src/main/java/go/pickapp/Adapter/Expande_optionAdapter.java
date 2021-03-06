package go.pickapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import go.pickapp.Activity.Product_detailsActivity;
import go.pickapp.Controller.Textview;
import go.pickapp.Model.Model_option;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 6/1/2017.
 */

public class Expande_optionAdapter extends BaseExpandableListAdapter {

    ArrayList<Model_option.Productoption> _listDataHeader; // header titles
    Pref_Master pref;
    public Context context;
    public static String variable = "";
    Product_detailsActivity product_detailsActivity;
    public static String value_check_uncheck = "0";


    public Expande_optionAdapter(Product_detailsActivity product_detailsActivity, Context context, ArrayList<Model_option.Productoption> listDataHeader, Pref_Master pref) {
        this._listDataHeader = listDataHeader;
        this._listDataHeader = listDataHeader;
        this.pref = pref;
        this.context = context;
        this.product_detailsActivity = product_detailsActivity;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return _listDataHeader.get(groupPosition).getProductoptiondetail().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Model_option.Productoptiondetail child = _listDataHeader.get(groupPosition).getProductoptiondetail().get(childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expande_option_item_list, null);
        }

        final ImageView img_uncheck = (ImageView) convertView.findViewById(R.id.img_uncheck);
        final ImageView img_check = (ImageView) convertView.findViewById(R.id.img_check);
        Textview code = (Textview) convertView.findViewById(R.id.code);
        Textview rate = (Textview) convertView.findViewById(R.id.rate);
        final RelativeLayout ll_item = (RelativeLayout) convertView.findViewById(R.id.ll_item);


        rate.setText(child.getRate());
        code.setText(child.getOptiondetail());
        //checkBox1.setText(child.optiondetail);

//        ll_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("Rate", child.getRate());
//                if (child.selected) {
//                    Log.e("Hii", "Hiii");
//                    child.setSelected(true);
//                    img_uncheck.setVisibility(View.GONE);
//                    img_check.setVisibility(View.VISIBLE);
//
//                } else {
//                    Log.e("Hello", "Hello");
//                    child.setSelected(false);
//                    img_uncheck.setVisibility(View.VISIBLE);
//                    img_check.setVisibility(View.GONE);
//
//                }
//
////                  if (value_check_uncheck.equals("0")) {
////                    child.setSelected(true);
////                    img_uncheck.setVisibility(View.GONE);
////                    img_check.setVisibility(View.VISIBLE);
////                  product_detailsActivity.settotal_count_plus(child.getRate());
////                  notifyDataSetChanged();
////
////                 } else {
////                    child.setSelected(false);
////                    Log.e("Value_UnCheck", ":" + child.isSelected());
////                    img_uncheck.setVisibility(View.VISIBLE);
////                    img_check.setVisibility(View.GONE);
////                   product_detailsActivity.settotal_count_minus(child.getRate());
////                 notifyDataSetChanged();
////                }
//
//            }
//        });

        if (child.selected) {
            child.setSelected(true);
            img_uncheck.setVisibility(View.GONE);
            img_check.setVisibility(View.VISIBLE);
            // product_detailsActivity.settotal_count_plus(add_value);

        } else {
            child.setSelected(false);
            img_uncheck.setVisibility(View.VISIBLE);
            img_check.setVisibility(View.GONE);
            // product_detailsActivity.settotal_count_minus(minus_value);

        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return _listDataHeader.get(groupPosition).getProductoptiondetail().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return _listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View
            convertView, ViewGroup parent) {
        final Model_option.Productoption model = _listDataHeader.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expande_option_group, null);
        }

        Textview lblListHeader = (Textview) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setText(model.getProductoptionname());

        if (isExpanded) {
            lblListHeader.setBackgroundResource(R.color.exp_bg);
        }

//

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
