package com.hacktiv8.crudsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        CountryAdapter.EditItemListener,
        CountryAdapter.DeleteItemListener {

    RecyclerView recyclerView;
    Button addButton;

    DatabaseHelper db;
    List<Country> countryList;
    CountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.country_list_rv);
        addButton = (Button)  findViewById(R.id.add_country_btn);
        addButton.setOnClickListener(this);

        loadDataCountry();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_country_btn){
            //Add Data

            popupFormCountry("TAMBAH DATA NEGARA", null, null, true);
        }
    }

    void popupFormCountry(String title, String countryName, String population, boolean isAddNew){

        AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.form_country, null);

        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(title);

        EditText countryNameInput = (EditText) view.findViewById(R.id.country_name_input);
        EditText countryPopulationInput = (EditText) view.findViewById(R.id.country_population_input);
        Button saveButton = (Button) view.findViewById(R.id.save_button);

        if(!isAddNew){
            countryNameInput.setText(countryName);
            countryPopulationInput.setText(population);
        }

        popupBuilder.setView(view);

        AlertDialog popupForm = popupBuilder.create();
        popupForm.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insert Data

                String countryName = countryNameInput.getText().toString();
                String countryPopulation = countryPopulationInput.getText().toString();

                Country country = new Country(countryName, Long.parseLong(countryPopulation));


                if(isAddNew){
                    db.addCountry(country);
                }else{
                    //db.edit();
                }

                loadDataCountry();

                Toast.makeText(MainActivity.this, "Tambah Data "+countryName+" Berhasil", Toast.LENGTH_SHORT).show();

                popupForm.dismiss();
            }
        });

    }

    private void loadDataCountry(){
        countryList = db.getAllCountries();
        countryAdapter = new CountryAdapter(this, countryList, this, this);
        recyclerView.setAdapter(countryAdapter);

    }

    @Override
    public void onEditItemClick(int position) {
        Country country = countryList.get(position);
        Toast.makeText(MainActivity.this, "EDIT Data "+country.getCountryName(), Toast.LENGTH_SHORT).show();

        popupFormCountry("EDIT DATA NEGARA", country.getCountryName(), String.valueOf(country.getPopulation()), false);

    }

    @Override
    public void onDeleteItemClick(int position) {
        Country country = countryList.get(position);
        Toast.makeText(MainActivity.this, "DELETE Data "+country.getCountryName(), Toast.LENGTH_SHORT).show();
        //db.delete();
        loadDataCountry();
    }
}