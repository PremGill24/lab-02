package com.example.listycity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> dataList;
    private ArrayAdapter<String> adapter;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView cityList = findViewById(R.id.city_list);
        Button addBtn = findViewById(R.id.btn_add_city);
        Button deleteBtn = findViewById(R.id.btn_delete_city);

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                dataList
        );
        cityList.setAdapter(adapter);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            cityList.setItemChecked(position, true);
        });

        addBtn.setOnClickListener(v -> showAddCityDialog());

        deleteBtn.setOnClickListener(v -> {
            if (selectedPosition >= 0 && selectedPosition < dataList.size()) {
                dataList.remove(selectedPosition);
                adapter.notifyDataSetChanged();

                // clear selection after delete
                selectedPosition = -1;
                cityList.clearChoices();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void showAddCityDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.content, null);
        EditText input = dialogView.findViewById(R.id.edit_city_name);

        new AlertDialog.Builder(this)
                .setTitle("Add City")
                .setView(dialogView)
                .setPositiveButton("CONFIRM", (dialog, which) -> {
                    String city = input.getText().toString().trim();
                    if (!TextUtils.isEmpty(city)) {
                        dataList.add(city);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }
}