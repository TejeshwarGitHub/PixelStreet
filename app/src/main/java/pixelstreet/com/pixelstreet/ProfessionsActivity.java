package pixelstreet.com.pixelstreet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;

import anim.FeedItemAnimator;
import pixelstreet.com.pixelstreet.helper.RecyclerClickListener;

public class ProfessionsActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener,RecyclerClickListener,NavigationView.OnNavigationItemSelectedListener{

    RecyclerView recyclerView;
    SliderLayout mDemoSlider;
    ArrayList tabs;
    NavigationView navigationView;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout_profession);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view_profession);
        navigationView.setNavigationItemSelectedListener(this);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        //can also put url as second parameter
        file_maps.put("Slide 1",R.drawable.ic_slide1);
        file_maps.put("Slide 2",R.drawable.ic_slide2);
        file_maps.put("Slide 3",R.drawable.ic_slide3);
        file_maps.put("Slide 4", R.drawable.ic_slide4);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.profession_chooser_recycler);
        EventChooserAdapter mAdapter = new EventChooserAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tabs = new ArrayList<>();
        tabs.add(new EventChooserSet("Photographer", R.drawable.whirl));
        tabs.add(new EventChooserSet("Videographer", R.drawable.whirl));
        tabs.add(new EventChooserSet("Cinematography", R.drawable.whirl));
        tabs.add(new EventChooserSet("Studios", R.drawable.whirl));
        tabs.add(new EventChooserSet("Photo Booths", R.drawable.whirl));
        tabs.add(new EventChooserSet("Photo Albums",R.drawable.whirl));
        mAdapter.setProfessions(tabs);
        mAdapter.setClickListener(this);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v, int pos) {
        startActivity(new Intent(ProfessionsActivity.this,LandingActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_profession);

        switch (id) {
            case R.id.nav_home:
                /*Fragment fragment= new Open_Land_Fragment();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.add(R.id.landing_container,fragment).commit();*/
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            feedItemAnimator=new FeedItemAnimator();
        }

        public void setClickListener(RecyclerClickListener clickListener) {
            this.clickListener = clickListener;
        }

        public void setProfessions(ArrayList<EventChooserSet> professions) {
            this.professions = professions;
            notifyDataSetChanged();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(inflater.inflate(R.layout.custom_profession, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            feedItemAnimator.animateAdd(holder);

            holder.name.setText(professions.get(position).getName());

            switch(position) {
                case 0:
                    holder.imageView.setImageResource(R.drawable.ic_home);
                    break;
                case 1:
                    holder.imageView.setImageResource(R.drawable.ic_home);
                    break;
                case 2:
                    holder.imageView.setImageResource(R.drawable.ic_home);
                    break;
                case 3:
                    holder.imageView.setImageResource(R.drawable.ic_home);
                    break;
                case 4:
                    holder.imageView.setImageResource(R.drawable.ic_home);
                    break;
                case 5:
                    holder.imageView.setImageResource(R.drawable.ic_home);
                    break;
            }
//            Picasso.with(context).load(professions.get(position).getImg_resource()).into(holder.imageView);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_profession);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
