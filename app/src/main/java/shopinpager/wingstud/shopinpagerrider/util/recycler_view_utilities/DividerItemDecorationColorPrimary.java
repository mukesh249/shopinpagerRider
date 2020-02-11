package shopinpager.wingstud.shopinpagerrider.util.recycler_view_utilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import shopinpager.wingstud.shopinpagerrider.R;

/**
 * Created by Admin on 11-12-17.
 */

public class DividerItemDecorationColorPrimary extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public DividerItemDecorationColorPrimary(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.rcy_item_devider_color_primary);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
