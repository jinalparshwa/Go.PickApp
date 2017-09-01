package go.pickapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import go.pickapp.Activity.MainActivity;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.CustomFilter;
import go.pickapp.Controller.Custom_area_filter;
import go.pickapp.Controller.Textview;
import go.pickapp.Model.Model_city;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

/**
 * Created by Admin on 6/9/2017.
 */

public class Expandable_cityAdapter extends BaseExpandableListAdapter {

    Context _context;
    public ArrayList<Model_city.Data> _listDataHeader; // header titles
    ArrayList<Model_city.Data> filterList = new ArrayList<>();
    Pref_Master pref;
    Custom_area_filter filter;

    public Expandable_cityAdapter(Context context, ArrayList<Model_city.Data> listDataHeader, Pref_Master pref) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.filterList = listDataHeader;
        this.pref = pref;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return _listDataHeader.get(groupPosition).getArealist().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Model_city.Arealist child = _listDataHeader.get(groupPosition).getArealist().get(childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_area_list, null);
        }

        Textview exp_area = (Textview) convertView.findViewById(R.id.exp_area);
        exp_area.setText(child.area);

        exp_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(_context, MainActivity.class);
                i.putExtra("fragmentcode", Config.Fragment_ID.res_list);
                pref.setCity_id(child.areaid);
                pref.setCity_name(child.area);
                _context.startActivity(i);
            }
        });
        return convertView;
    }


    //    @Override
//    public Filter getFilter() {
//        if (filter == null) {
//            filter = new Custom_area_filter(filterList, this);
//        }
//        return filter;
//    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return _listDataHeader.get(groupPosition).getArealist().size();
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final Model_city.Data model = _listDataHeader.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_city_list, null);
        }

        Textview exp_city = (Textview) convertView.findViewById(R.id.exp_city);
        String city = "";
        city = model.getCity();
        exp_city.setText(city);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
