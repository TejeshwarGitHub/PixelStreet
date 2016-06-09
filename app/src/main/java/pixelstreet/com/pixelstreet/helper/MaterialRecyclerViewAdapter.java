package pixelstreet.com.pixelstreet.helper;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;

import java.util.ArrayList;
import java.util.List;

import pixelstreet.com.pixelstreet.R;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class MaterialRecyclerViewAdapter extends RecyclerView.Adapter<MaterialRecyclerViewAdapter.PhotographerCardViewHolder> {

    List<Object> contents;

    Context mContext;
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    List<Chip> chips_list ;
    public MaterialRecyclerViewAdapter(Context context,List<Object> contents) {
        this.contents = contents;
        mContext=context;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public PhotographerCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_photogragher_card, parent, false);

                LinearLayout cardHead= (LinearLayout) view.findViewById(R.id.card_header);
                cardHead.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                return new PhotographerCardViewHolder(view) {
                };
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new PhotographerCardViewHolder(view) {
                };
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(PhotographerCardViewHolder holder, int position) {

        chips_list=new ArrayList<>();
        chips_list.add(new Tag("Lorem"));
        chips_list.add(new Tag("Ipsum dolor"));
        chips_list.add(new Tag("Sit amet"));
        chips_list.add(new Tag("Consectetur"));
        chips_list.add(new Tag("adipiscing elit"));

        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                holder.chipView.setChipList(chips_list);

                break;
            case TYPE_CELL:
                break;
        }

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

    public class PhotographerCardViewHolder extends RecyclerView.ViewHolder{

        ChipView chipView;
        RatingBar ratingBar;
        RecyclerView mImageHorizontal;
        HorizontalImageScrollAdapter horizontalImageScrollAdapter;
        LinearLayoutManager linearLayoutManager;
        public PhotographerCardViewHolder(View itemView) {
            super(itemView);
            Context context=itemView.getContext();
            chipView = (ChipView) itemView.findViewById(R.id.chipview);
            ratingBar = (RatingBar) itemView.findViewById(R.id.profile_rating);
            mImageHorizontal = (RecyclerView) itemView.findViewById(R.id.horizontal_imagescroll);
            linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
            mImageHorizontal.setLayoutManager(linearLayoutManager);
            horizontalImageScrollAdapter = new HorizontalImageScrollAdapter();
            mImageHorizontal.setAdapter(horizontalImageScrollAdapter);
        }
    }
}