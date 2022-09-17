package com.hacktiv8.crudsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private Context context;

    private List<Country> countryList = new ArrayList<>();

    public CountryAdapter(Context context, List<Country> countryList, EditItemListener editItemListener, DeleteItemListener deleteItemListener) {
        this.context = context;
        this.countryList = countryList;
        this.editItemListener = editItemListener;
        this.deleteItemListener = deleteItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = countryList.get(position);

        holder.countryIdView.setText(String.valueOf(country.getId()));
        holder.countryNameView.setText(country.getCountryName());
        holder.countryPopulationView.setText(String.valueOf(country.getPopulation()));

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView countryIdView;
        private TextView countryNameView;
        private TextView countryPopulationView;
        private ImageButton editButton;
        private ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            countryIdView = itemView.findViewById(R.id.country_id_view);
            countryNameView = itemView.findViewById(R.id.country_name_view);
            countryPopulationView = itemView.findViewById(R.id.country_population_view);

            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            if(v.getId() == R.id.edit_button){
                editItemListener.onEditItemClick(position);
            }
            else if(v.getId() == R.id.delete_button){
                deleteItemListener.onDeleteItemClick(position);
            }

        }
    }

    private EditItemListener editItemListener;
    private DeleteItemListener deleteItemListener;

    public interface EditItemListener{
        void onEditItemClick(int position);
    }

    public interface DeleteItemListener{
        void onDeleteItemClick(int position);
    }
}
