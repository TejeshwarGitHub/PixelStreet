package pixelstreet.com.pixelstreet.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;

import java.util.ArrayList;
import java.util.List;

import pixelstreet.com.pixelstreet.ProfileDetailsActivity;
import pixelstreet.com.pixelstreet.R;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class MaterialRecyclerViewAdapter extends RecyclerView.Adapter<MaterialRecyclerViewAdapter.PhotographerCardViewHolder> {

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    List<Object> contents;
    Context mContext;
    List<Chip> chips_list;

    public MaterialRecyclerViewAdapter(Context context, List<Object> contents) {
        this.contents = contents;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public PhotographerCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_photogragher_card, parent, false);
        return new PhotographerCardViewHolder(view);


    }

    @Override
    public void onBindViewHolder(PhotographerCardViewHolder holder, int position) {

        chips_list = new ArrayList<>();
        chips_list.add(new Tag("Lorem"));
        chips_list.add(new Tag("Ipsum dolor"));
        chips_list.add(new Tag("Sit amet"));
        chips_list.add(new Tag("Consectetur"));
        chips_list.add(new Tag("adipiscing elit"));

        holder.chipView.setChipList(chips_list);


    }

    public class Tag implements Chip {

        private String mName;
        private int mType = 0;

        public Tag(String name, int type) {
            this(name);
            mType = type;
        }

        public Tag(String name) {
            mName = name;
        }

        public int getType() {
            return mType;
        }

        @Override
        public String getText() {
            return mName;
        }
    }

    public class PhotographerCardViewHolder extends RecyclerView.ViewHolder {

        ChipView chipView;
        RatingBar ratingBar;
        RecyclerView mImageHorizontal;
        HorizontalImageScrollAdapter horizontalImageScrollAdapter;

        public PhotographerCardViewHolder(View itemView) {
            super(itemView);
            chipView = (ChipView) itemView.findViewById(R.id.chipview);
            ratingBar = (RatingBar) itemView.findViewById(R.id.profile_rating);
            mImageHorizontal = (RecyclerView) itemView.findViewById(R.id.horizontal_imagescroll);
            mImageHorizontal.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            horizontalImageScrollAdapter = new HorizontalImageScrollAdapter();
            mImageHorizontal.setAdapter(horizontalImageScrollAdapter);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, ProfileDetailsActivity.class));
                }
            });
        }

    }
}