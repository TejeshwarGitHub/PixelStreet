package pixelstreet.com.pixelstreet.anim;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import pixelstreet.com.pixelstreet.R;

/**
 * Created by Tejeshwar Reddy on 10-02-2016.
 */
public class FacebookButtonTransformer implements ViewPager.PageTransformer  {

    Context context;
    View loginWrapper;
    View pageIndicator;

    public FacebookButtonTransformer(Context context, View loginWrapper, View pageIndicator) {
        this.context = context;
        this.loginWrapper=loginWrapper;
        this.pageIndicator=pageIndicator;
    }

    @Override
    public void transformPage(View view, float position) {

        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if(((Integer)view.getTag())==3){

            if(position<0){
                view.setAlpha((position+1)/2);
                view.setTranslationX(-position * pageWidth);
                pageIndicator.setAlpha(0);
            }
            else {
                view.setAlpha(1);
                pageIndicator.setAlpha(1);
            }

        }
        if(((Integer)view.getTag())==4) {

            if(position<1&& position>=0) {
                loginWrapper.setTranslationY(-(1 - position) * (pageHeight / 2));
                ((TextView)loginWrapper.findViewById(R.id.skipLogin)).setText("");
            }
            else if(position>=0.8){
                SpannableString content = new SpannableString("skip intro");
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                ((TextView)loginWrapper.findViewById(R.id.skipLogin)).setText("");
            }

        }



    }
}
