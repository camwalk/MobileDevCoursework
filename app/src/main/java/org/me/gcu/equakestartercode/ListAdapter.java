package org.me.gcu.equakestartercode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        String fullTitle = earthquakes.get(position).getTitle();
        String fullDescription = earthquakes.get(position).getDescription();
        String date = earthquakes.get(position).getDate();
        String[] splitTitle = fullTitle.split(" ");
        String[] splitDescription = fullDescription.split(";");
        holder.location.setText(splitDescription[1]);
        holder.magnitude.setText("Magnitude: " + splitTitle[6]);
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
        }
    }
}


