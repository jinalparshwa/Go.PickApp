package go.pickapp.Controller;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;

import go.pickapp.Adapter.Restaurant_adapter;
import go.pickapp.Model.Model_restaurant;

/**
 * Created by Admin on 7/3/2017.
 */

public class CustomFilter extends Filter {
    private Restaurant_adapter mAdapter;
    ArrayList<Model_restaurant> filterList;


    public CustomFilter(ArrayList<Model_restaurant> filterList, Restaurant_adapter mAdapter) {
        super();
        this.mAdapter = mAdapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        // filterList.clear();
        final FilterResults results = new FilterResults();
        if (constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<Model_restaurant> filteredPlayers = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                //CHECK
                if (filterList.get(i).getRes_name().toUpperCase().contains(constraint)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = filterList.size();
            results.values = filterList;
            Log.e("filter_list", ":" + filterList.size());
            Log.e("results", ":" + results);
        }
        return results;

    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        Log.e("Count Number 2 ", ":" + ((ArrayList<Model_restaurant>) results.values).size());
        mAdapter.arrayList = (ArrayList<Model_restaurant>) results.values;
        mAdapter.notifyDataSetChanged();
    }
}
