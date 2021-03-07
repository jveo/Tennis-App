package com.example.tennisapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tennisapp.pojo.racquetTypes;
import java.util.ArrayList;


public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomLocationViewHolder>{

    private ArrayList<racquetTypes> racquets;
    private Context context;

    public CustomRecyclerViewAdapter(ArrayList<racquetTypes> racquets, Context context){
        this.racquets = racquets;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item, parent, false);
        return new CustomLocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomLocationViewHolder holder, int position) {
        racquetTypes racquet = racquets.get(position);

        holder.name.setText(racquet.getName());
        holder.description.setText(racquet.getDescription());

        holder.edit.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(CreateUpdateFragment.ACTION_TYPE,
                    CreateUpdateFragment.UPDATE);
            bundle.putParcelable(CreateUpdateFragment.RACQUET,
                    racquets.get(position));
            Navigation.findNavController(view).
                    navigate(R.id.createUpdateFragment, bundle);
        });


        holder.shop.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(racquet.getWebsite()));
            if(intent.resolveActivity(context.getPackageManager()) != null){
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return racquets.size();
    }

    class CustomLocationViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{

        protected TextView name;
        protected TextView description;
        protected ImageView edit;
        protected ImageView shop;

        public CustomLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.description = itemView.findViewById(R.id.itemDescription);
            this.edit = itemView.findViewById(R.id.edit);
            this.shop = itemView.findViewById(R.id.shop);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete " +
                            racquets.get(getLayoutPosition()).getName() + "?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            /* TODO
                             *  - Add code to remove Racquet from database when SQL is finished
                             */
                            Database db = new Database(context);

                            //Delete record from database
                            db.deleteLocation(racquets.get(getLayoutPosition()).getId());
                            //Delete the record from the ArrayList
                            racquets.remove(getLayoutPosition());
                            //Notify the RecyclerView the item was removed
                            notifyItemRemoved(getAdapterPosition());
                            db.close();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return false;
        }

        @Override
        public void onClick(View v) {
            description.setVisibility(description.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }
    }
}
