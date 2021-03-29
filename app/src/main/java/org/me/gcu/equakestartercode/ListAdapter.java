package org.me.gcu.equakestartercode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

//Student ID: S1920624
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    ArrayList<Earthquake> earthquakes;
    Context context;

    // Constructor for initialization
    public ListAdapter(Context context, ArrayList earthquakes) {
        this.context = context;
        this.earthquakes = earthquakes;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_row.xml
        // layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        // Passing view to ViewHolder
        ListAdapter.ViewHolder viewHolder = new ListAdapter.ViewHolder(view);
        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        // TypeCast Object to int type
        String fullDesc = earthquakes.get(position).getDescription();
        String[] splitDesc = fullDesc.split(";");
        String magnitude = earthquakes.get(position).getMagntitude();
        String date = earthquakes.get(position).getDate();
        holder.location.setText(splitDesc[1]);
        holder.magnitude.setText("Magnitude: " + magnitude);
        if(Double.parseDouble(magnitude) > 2){
            holder.magnitude.setTextColor(Color.RED);
        }
        else if (Double.parseDouble(magnitude) > 1) {
            holder.magnitude.setTextColor(Color.argb(255,255,165,0));
        }
        else {
            holder.magnitude.setTextColor(Color.argb(255,210,210,0));
        }
        holder.date.setText("Date/Time: " + date);
    }

    @Override
    public int getItemCount() {
        // Returns number of items
        // currently available in Adapter
        return earthquakes.size();
    }

    // Initializing the Views
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView location;
        TextView magnitude;
        TextView date;

        public ViewHolder(View view) {
            super(view);
            location = (TextView) view.findViewById(R.id.textLocation);
            magnitude = (TextView) view.findViewById(R.id.textMagnitude);
            date = (TextView) view.findViewById(R.id.textDate);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = getAdapterPosition();
                    Context context = v.getContext();
                    Intent intent = new Intent(context,
                            DetailedActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("EARTHQUAKE", (Serializable) earthquakes.get(i));
                    intent.putExtra("BUNDLE", args);
                    context.startActivity(intent);
                }
            });
        }
    }
}


