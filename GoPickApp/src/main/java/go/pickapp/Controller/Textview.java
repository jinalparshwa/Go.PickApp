package go.pickapp.Controller;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import go.pickapp.Model.Model_city;

/**
 * Created by Admin on 12/1/2016.
 */

public class Textview extends TextView {

    private final static String CONDENSED_FONT = "fonts/RobotoCondensed-Regular.ttf";

    public Textview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Textview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Textview(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), CONDENSED_FONT);
        setTypeface(tf);
    }

}