package ppl.com.absensy;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

public class PacificoFont extends TextView {

  public PacificoFont(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    this.setTypeface(
        Typeface.createFromAsset(context.getAssets(), "fonts/pacifico.ttf"));
  }

}
