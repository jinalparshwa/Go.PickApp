package go.pickapp.Controller;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import java.lang.reflect.Field;

/**
 * Created by Admin on 12/1/2016.
 */

public class Edittext extends EditText {

    private final static String CONDENSED_FONT = "fonts/RobotoCondensed-Regular.ttf";

    public Edittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Edittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Edittext(Context context) {
        super(context);
        init();

    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), CONDENSED_FONT);

        setTypeface(tf);
        Field f = null;
    }


}