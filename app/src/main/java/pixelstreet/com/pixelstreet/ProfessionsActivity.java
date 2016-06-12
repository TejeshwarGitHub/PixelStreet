package pixelstreet.com.pixelstreet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

import anim.FeedItemAnimator;
import helper.GridSpaceItemDecoration;
import helper.RecyclerClickListener;

public class ProfessionsActivity extends AppCompatActivity implements RecyclerClickListener {

    RecyclerView recyclerView;
    JSONArray recommended;

    SliderLayout mSliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_professions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerView = (RecyclerView) findViewById(R.id.profession_chooser_recycler);
        EventChooserAdapter mAdapter = new EventChooserAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter.setClickListener(this);
        recyclerView.addItemDecoration(new GridSpaceItemDecoration(30));
        recyclerView.setNestedScrollingEnabled(false);


        mSliderLayout = (SliderLayout) findViewById(R.id.slider);
        for (int i = 0; i < 5; i++) {

            BaseSliderView baseSliderView = new MyBaseSliderView(this, i);
            mSliderLayout.addSlider(baseSliderView);
        }
//        mSliderLayout.setDuration(4000);
        mSliderLayout.startAutoCycle(6000,4000,true);


    }


    @Override
    public void onClick(View v, int pos) {
        startActivity(new Intent(ProfessionsActivity.this, LandingActivity.class));
    }

    @Override
    public void onBackPressed() {


        super.onBackPressed();

    }


    class MyBaseSliderView extends BaseSliderView {

        Context context;
        LayoutInflater inflater;
        int position;

        //todo accept a PhotographerProfile object instead
        protected MyBaseSliderView(Context context, int position) {
            super(context);
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.position = position;
        }

        /**
         * the extended class have to implement getView(), which is called by the adapter,
         * every extended class response to render their own view.
         *
         * @return
         */
        //todo actually bind a Photographer profile object
        @Override
        public View getView() {
            View v = inflater.inflate(R.layout.fragment_blank, null);
            ImageView image = (ImageView) v.findViewById(R.id.imageView);
            switch (position) {
                case 0:
                    Picasso.with(context).load(R.drawable.wallpaper1).resize(400, 300).centerCrop().into(image);
                    break;
                case 1:
                    Picasso.with(context).load(R.drawable.wallpaper2).resize(400, 300).centerCrop().into(image);
                    break;
                case 2:
                    Picasso.with(context).load(R.drawable.wallpaper3).resize(400, 300).centerCrop().into(image);
                    break;
                case 3:
                    Picasso.with(context).load(R.drawable.wallpaper4).resize(400, 300).centerCrop().into(image);
                    break;
                default:
                    Picasso.with(context).load(R.drawable.wallpaper5).resize(400, 300).centerCrop().into(image);
                    break;

            }
            return v;
        }

    }

    private class EventChooserAdapter extends RecyclerView.Adapter<EventChooserAdapter.MyViewHolder> {

        Context context;
        LayoutInflater inflater;
        ArrayList<EventChooserSet> professions;
        RecyclerClickListener clickListener;
        FeedItemAnimator feedItemAnimator;

        public EventChooserAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            professions = new ArrayList<>();
            feedItemAnimator = new FeedItemAnimator();
            professions = new ArrayList<>();
            professions.add(new EventChooserSet("Photographer", R.drawable.camera));
            professions.add(new EventChooserSet("Videographer", R.drawable.video));
            professions.add(new EventChooserSet("Cinematographer", R.drawable.movie));
            professions.add(new EventChooserSet("Studios", R.drawable.microphone_variant));
            professions.add(new EventChooserSet("Photo Booths", R.drawable.photo_front));
            professions.add(new EventChooserSet("Photo Albums", R.drawable.image_album));
        }

        public void setClickListener(RecyclerClickListener clickListener) {
            this.clickListener = clickListener;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.custom_profession, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            feedItemAnimator.animateAdd(holder);
            holder.name.setText(professions.get(position).getName());
            holder.imageView.setImageResource(professions.get(position).getImg_resource());

        }

        @Override
        public void onViewDetachedFromWindow(MyViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            holder.clearAnimation();
        }

        @Override
        public int getItemCount() {
            return professions.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView name;

            public MyViewHolder(View itemView) {
                super(itemView);

                imageView = (ImageView) itemView.findViewById(R.id.profession_chooser_image);
                name = (TextView) itemView.findViewById(R.id.profession_chooser_name);
                if (clickListener != null) {
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickListener.onClick(v, getAdapterPosition());
                        }
                    });
                }
            }

            public void clearAnimation() {
                itemView.clearAnimation();
            }
        }
    }

    class EventChooserSet {
        private String name;
        private int Img_resource;

        public EventChooserSet(String name, int img_resource) {
            this.name = name;
            Img_resource = img_resource;
        }

        public String getName() {
            return name;
        }

        public int getImg_resource() {
            return Img_resource;
        }
    }


}
