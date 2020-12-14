package com.example.ridhdhish.sut;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ridhdhish on 15-02-2019.
 */

public class HomeAnnouncementAdapter extends RecyclerView.Adapter<HomeAnnouncementAdapter.MyViewHolder> {

    private ArrayList<HomeAnnouncementDataModel> list;
    private Context mcontext;

    public HomeAnnouncementAdapter(ArrayList<HomeAnnouncementDataModel> Data, Context _context) {
        list = Data;
        mcontext = _context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_home_announcement, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAnnouncementAdapter.MyViewHolder holder, int position) {
        holder.txtHomeAnnouncementText.setText(list.get(position).getTxtHomeAnnouncementText());

        if (list.get(position).getImp().toString().equals("1")) {
            holder.cardviewHomeAnnouncement.setCardBackgroundColor(mcontext.getResources().getColor(R.color.cardbackColor));
        }
        // holder.cardviewHomeAnnouncement.setCardBackgroundColor();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtHomeAnnouncementText;
        android.support.v7.widget.CardView cardviewHomeAnnouncement;
        char imp;

        public MyViewHolder(View v) {
            super(v);
            txtHomeAnnouncementText = (TextView) v.findViewById(R.id.txtHomeAnnouncementText);
            cardviewHomeAnnouncement = (android.support.v7.widget.CardView) v.findViewById(R.id.cardviewHomeAnnouncement);
            /*likeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int)likeImageView.getTag();
                    if( id == R.drawable.ic_like){

                        likeImageView.setTag(R.drawable.ic_liked);
                        likeImageView.setImageResource(R.drawable.ic_liked);

                        Toast.makeText(getActivity(),titleTextView.getText()+" added to favourites",Toast.LENGTH_SHORT).show();

                    }else{
                        likeImageView.setTag(R.drawable.ic_like);
                        likeImageView.setImageResource(R.drawable.ic_like);
                        Toast.makeText(getActivity(),titleTextView.getText()+" removed from favourites",Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
    }
}
