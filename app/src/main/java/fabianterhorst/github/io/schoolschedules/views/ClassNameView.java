package fabianterhorst.github.io.schoolschedules.views;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mikepenz.materialize.util.UIUtils;

public class ClassNameView extends LinearLayout {

    public ClassNameView(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        setOrientation(HORIZONTAL);
        this.addView(getEditText());
        this.addView(getEditText());
        this.addView(getEditText());
    }

    private EditText getEditText() {
        EditText editText = new EditText(getContext());
        editText.setPadding(0, 0, Math.round(UIUtils.convertDpToPixel(10, getContext())), 0);
        return editText;
    }
}
