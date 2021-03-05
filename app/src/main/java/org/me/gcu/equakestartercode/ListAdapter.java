package org.me.gcu.equakestartercode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.me.gcu.equakestartercode.Earthquake;
import org.me.gcu.equakestartercode.R;

import java.util.ArrayList;
import java.util.List;

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
        // Inflating the Layout(Instantiates list_item.xml
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
        holder.title.setText(earthquakes.get(position).getTitle());
        holder.latitude.setText(earthquakes.get(position).getTitle());
        holder.longitude.setText(earthquakes.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        // Returns number of items
        // currently available in Adapter
        return earthquakes.size();
    }

    // Initializing the Views
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView latitude;
        TextView longitude;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textTitle);
            latitude = (TextView) view.findViewById(R.id.textLatitude);
            longitude = (TextView) view.findViewById(R.id.textLongitude);
        }
    }
}


