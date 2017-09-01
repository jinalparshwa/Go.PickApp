package go.pickapp.Controller;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Admin on 12/1/2016.
 */

public class Editext_Numeric extends EditText {

    private final static String CONDENSED_FONT = "fonts/Roboto-Regular.ttf";

    public Editext_Numeric(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Editext_Numeric(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Editext_Numeric(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), CONDENSED_FONT);
        setTypeface(tf);
    }


}