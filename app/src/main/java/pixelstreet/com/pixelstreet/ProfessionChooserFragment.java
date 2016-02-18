package pixelstreet.com.pixelstreet;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pixelstreet.com.pixelstreet.helper.RecyclerClickListener;

public class ProfessionChooserFragment extends Fragment implements RecyclerClickListener {

    RecyclerView recyclerView;

    public ProfessionChooserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profession_chooser, container, false);
    }
    ArrayList<EventChooserSet> tabs;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.profession_chooser_recycler);
        EventChooserAdapter mAdapter = new EventChooserAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
    public void onClick(View v, int pos) {

        startActivity(new Intent(getActivity().getApplicationContext(),LandingActivity.class));
        /*if (pos < 4) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment fragment=new EventListingFragmentHeader();
            transaction.addToBackStack("events");

            Bundle args = new Bundle();
            args.putString("tab", tabs.get(pos).getName());
            fragment.setArguments(args);
            transaction.replace(R.id.container, fragment, "EventHeader");
            transaction.commit();
        }
        else{
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment fragment=new InitiativeFragment();
            transaction.addToBackStack("events");
            transaction.replace(R.id.container, fragment, "initiative");
            transaction.commit();
        }*/
    }

    private class EventChooserAdapter extends RecyclerView.Adapter<EventChooserAdapter.MyViewHolder> {

        Context context;
        LayoutInflater inflater;
        ArrayList<EventChooserSet> professions;
        RecyclerClickListener clickListener;

        public EventChooserAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            professions = new ArrayList<>();
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
            holder.name.setText(professions.get(position).getName());
            Picasso.with(context).load(professions.get(position).getImg_resource()).into(holder.imageView);

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
}
