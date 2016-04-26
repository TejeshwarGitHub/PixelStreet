package pixelstreet.com.pixelstreet;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ProfileDetailsActivity extends AppCompatActivity {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setTitle("Harshit Agarwal");
        setTitle("");

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");


        final int[] picLinks = {R.drawable.sample_0,
                R.drawable.sample_1,
                R.drawable.sample_2,
                R.drawable.sample_3,
                R.drawable.sample_4,
                R.drawable.sample_5,
                R.drawable.sample_6,
                R.drawable.sample_7,
                R.drawable.sample_0,
                R.drawable.sample_1,
                R.drawable.sample_2,
                R.drawable.sample_3,
                R.drawable.sample_4,
                R.drawable.sample_5,
                R.drawable.sample_6,
                R.drawable.sample_7,
                R.drawable.sample_0,
                R.drawable.sample_1,
                R.drawable.sample_2,
                R.drawable.sample_3,
        };


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.imageContainer);
        recyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                if (viewType == TYPE_ITEM)
                return new MyViewHolder(LayoutInflater.from(ProfileDetailsActivity.this)
                        .inflate(R.layout.image_in_details, parent, false));

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//                if (holder instanceof MyViewHolder) {
                MyViewHolder holder1 = (MyViewHolder) holder;
                Picasso.with(ProfileDetailsActivity.this).load(picLinks[position])
                        .resize(200, 200).centerCrop()
                        .into(holder1.imageView);
//                }
//                holder.imageView.reD
            }


            @Override
            public int getItemCount() {
                return picLinks.length;
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);


        recyclerView.setLayoutManager(gridLayoutManager);

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    class MyHeaderViewHolder extends RecyclerView.ViewHolder {


        public MyHeaderViewHolder(View itemView) {
            super(itemView);
//            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }


}