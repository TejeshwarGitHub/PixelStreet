package pixelstreet.com.pixelstreet.helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import pixelstreet.com.pixelstreet.R;

/**
 * Created by Tejeshwar Reddy on 6/8/2016.
 */
public class HorizontalImageScrollAdapter extends RecyclerView.Adapter<HorizontalImageScrollAdapter.HorizontalImageScrollViewHolder> implements RecyclerClickListener {

    ArrayList<Integer> drawables;

    public HorizontalImageScrollAdapter(){
        drawables=new ArrayList<>();
    }

    @Override
    public HorizontalImageScrollViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HorizontalImageScrollViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_horizontalimagescrollitem,parent,false));
    }

    @Override
    public void onBindViewHolder(HorizontalImageScrollViewHolder holder, int position) {
        drawables.add(R.drawable.wallpaper1);
        drawables.add(R.drawable.wallpaper2);
        drawables.add(R.drawable.wallpaper3);
        drawables.add(R.drawable.wallpaper4);
        drawables.add(R.drawable.wallpaper5);
        drawables.add(R.drawable.wallpaper6);
        drawables.add(R.drawable.wallpaper7);

        holder.imageView.setImageResource(drawables.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return drawables.size();
    }

    @Override
    public void onClick(View v, int pos) {

    }

    public class HorizontalImageScrollViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public HorizontalImageScrollViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.custom_imagescrollview);
        }
    }
}
