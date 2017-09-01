package go.pickapp.Controller;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Nirav on 5/16/2016.
 */
public class Title_Textview extends TextView {

    private final static String CONDENSED_FONT = "fonts/Roboto-Regular.ttf";

    public Title_Textview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Title_Textview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Title_Textview(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), CONDENSED_FONT);
        setTypeface(tf);
    }


}
