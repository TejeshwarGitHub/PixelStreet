package pixelstreet.com.pixelstreet;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import de.hdodenhof.circleimageview.CircleImageView;

class CircularImageBehaviour extends CoordinatorLayout.Behavior<CircleImageView> {
    /**
     * Default constructor for inflating Behaviors from layout. The Behavior will have
     * the opportunity to parse specially defined layout parameters. These parameters will
     * appear on the child view tag.
     *
     * @param context
     * @param attrs
     */
    public CircularImageBehaviour(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Default constructor for instantiating Behaviors.
     */
    public CircularImageBehaviour() {
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }


    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView child, View dependency) {
        Log.i("changes",""+dependency.getScaleY()+" "+dependency.getY() + " " + dependency.getMinimumHeight());
//        child.setTranslationX(dependency.getY());
        child.setTranslationY(dependency.getY());
        parent.getWidth();
        child.getX();

        return false;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, CircleImageView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if(dyUnconsumed<0) {
            Log.e("Nested Scroll dx", dxConsumed+"");
            Log.e("Nested Scroll dy", dyConsumed+"");
        }
    }

}