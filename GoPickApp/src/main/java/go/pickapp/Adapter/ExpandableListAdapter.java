package go.pickapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import go.pickapp.Activity.MainActivity;
import go.pickapp.Activity.Product_detailsActivity;
import go.pickapp.Activity.RestaurantActivity;
import go.pickapp.Controller.Config;
import go.pickapp.Controller.DialogBox;
import go.pickapp.Controller.SwipeDetector;
import go.pickapp.Controller.Textview;
import go.pickapp.Controller.Textview_Bold;
import go.pickapp.Model.Model_New_Product;
import go.pickapp.Model.Model_Product;
import go.pickapp.Model.Model_child;
import go.pickapp.R;
import go.pickapp.Shared.Pref_Master;

import static go.pickapp.Activity.RestaurantActivity.availability;

/**
 * Created by Admin on 5/13/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    ArrayList<Model_New_Product.Product> _listDataHeader; // header titles
    Pref_Master pref;
    private SwipeLayout swipeLayout;
    boolean isOpenSwipeLayout;
    RestaurantActivity restaurantActivity;
    SwipeDetector swipe;

    public ExpandableListAdapter(RestaurantActivity restaurantActivity, Context context, ArrayList<Model_New_Product.Product> listDataHeader, Pref_Master pref) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.pref = pref;
        this.restaurantActivity = restaurantActivity;
        swipe = new SwipeDetector();

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return _listDataHeader.get(groupPosition).getMenu().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Model_New_Product.Menu child = _listDataHeader.get(groupPosition).getMenu().get(childPosition);
        Log.e("Child", ":" + child);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_item_list, null);
        }

        Textview_Bold dish_title = (Textview_Bold) convertView.findViewById(R.id.dish_title);
        Textview dish_desc = (Textview) convertView.findViewById(R.id.dish_desc);
        Textview dish_rate = (Textview) convertView.findViewById(R.id.dish_rate);
        ImageView dish_image = (ImageView) convertView.findViewById(R.id.dish_image);
        LinearLayout ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);

        dish_title.setText(child.getProductname());
        dish_desc.setText(Html.fromHtml(child.getShortdesc()));
        dish_rate.setText(child.getRate());
        Glide.with(_context).load(child.getThumb()).placeholder(R.drawable.res_bg_80x80).into(dish_image);

        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, Product_detailsActivity.class);
                //intent.putExtra("fragmentcode", Config.Fragment_ID.Product_detail);
                pref.setProduct_id(child.getProductid());
                Log.e("jinal_product_id", child.getProductid());
                _context.startActivity(intent);
            }
        });
        // ll_item.setOnTouchListener(swipe);

        swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe_layout);
        swipeLayout.isLeftSwipeEnabled();
        swipeLayout.isRightSwipeEnabled();

        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //add drag edge.(If the BottomView has 'layout_gravity' attribute, this line is unnecessary)

        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, convertView.findViewById(R.id.bottom_wrapper));
        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, convertView.findViewById(R.id.bottom_wrapper));
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                isOpenSwipeLayout = true;
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                if (child.getSwipestatus().equals("0")) {
                    if (availability.equals("1")) {
                        restaurantActivity.Add_cart(child.getProductid(), child.getRate(), child.getRate());
                    } else {
                        DialogBox.setsecond_popup(_context, _context.getResources().getString(R.string.Right_now_closed));
                    }
                } else {
                    Intent intent = new Intent(_context, Product_detailsActivity.class);
                    //intent.putExtra("fragmentcode", Config.Fragment_ID.Product_detail);
                    pref.setProduct_id(child.getProductid());
                    Log.e("jinal_product_id", child.getProductid());
                    _context.startActivity(intent);
                }
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
                isOpenSwipeLayout = false;
            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        ViewTreeObserver.OnGlobalLayoutListener swipeGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isOpenSwipeLayout) {
                    // Opens the layout without animation
                    swipeLayout.open(true);
                }
            }
        };

        swipeLayout.getViewTreeObserver().addOnGlobalLayoutListener(swipeGlobalLayoutListener);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return _listDataHeader.get(groupPosition).getMenu().size();
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
        final Model_New_Product.Product model = _listDataHeader.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_group_list, null);
        }

        Textview_Bold lblListHeader = (Textview_Bold) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setText(model.getCategory().toUpperCase());

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
