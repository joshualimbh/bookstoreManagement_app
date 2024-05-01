package com.example.fit2081_labtask_week2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder> {
    ArrayList<Data> week6List = new ArrayList<Data>();

    public recyclerViewAdapter(ArrayList<Data> week6List) {
        this.week6List = week6List;

    }

    @NonNull
    @Override
    //to inflate the layout making use of the card view returning the instance of view holder which will be used directly on "onbindviewholder"
    public recyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapter.ViewHolder holder, int position) {
        Data data = week6List.get(position);
        holder.cardViewID.setText("ID: "+ data.getId());
        holder.cardViewAuthor.setText("Author: "+data.getAuthor());
        holder.cardViewDesc.setText("DESC: "+data.getDesc());
        holder.cardViewTitle.setText("Title: "+data.getTitle());
        holder.cardViewISBN.setText("ISBN: "+data.getIsbn());
        holder.cardViewPrice.setText("Price: "+data.getPrice());
        holder.count.setText(""+data.getCount());





    }

    @Override
    public int getItemCount() {
        return week6List.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cardViewID;
        public TextView cardViewTitle;
        public TextView cardViewAuthor;
        public TextView cardViewDesc;
        public TextView cardViewISBN;
        public TextView cardViewPrice;
        public TextView count;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            cardViewID = itemView.findViewById(R.id.cardViewID);
            cardViewTitle = itemView.findViewById(R.id.cardViewTitle);
            cardViewAuthor = itemView.findViewById(R.id.cardViewAuthor);
            cardViewDesc = itemView.findViewById(R.id.cardViewDesc);
            cardViewISBN = itemView.findViewById(R.id.cardViewISBN);
            cardViewPrice = itemView.findViewById(R.id.cardViewPrice);
            count = itemView.findViewById(R.id.cardViewCount);
        }

    }
}
