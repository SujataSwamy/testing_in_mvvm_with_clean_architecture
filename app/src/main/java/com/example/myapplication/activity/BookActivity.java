package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.BookViewModel;
import com.example.myapplication.BookViewModelFactory;
import com.example.myapplication.R;
import com.example.myapplication.adapter.BookRecyclerViewAdapter;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.model.BookData;
import java.util.List;

/**
 * This class displays sales report, per flight leg, per product & per user
 *
 * @author sujata
 */
public class BookActivity extends AppCompatActivity {
    private BookRecyclerViewAdapter bookRecyclerViewAdapter;
    private BookViewModel salesViewModel;
    private ActivityMainBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Instantiate ViewModel
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        BookViewModelFactory factory = BookViewModelFactory.getInstance(getApplication());

        salesViewModel = ViewModelProviders.of(this, factory).get(BookViewModel.class);
        dataBinding.setModel(salesViewModel);
        //Observes changes of Livedata in Data binding
        dataBinding.setLifecycleOwner(this);
        // Observe Flight Leg Data
        salesViewModel.getAuthorNameListLiveData().observe(this, authorNameListObserver);
        salesViewModel.getBookListLiveData().observe(this,bookListObserver);
        // Set Listener for Flight Leg
//        dataBinding.flightLegs.setListener(onFlightsAdded);
        // Set up recyclerview
        setSalesRecyclerViewAdapter();
        dataBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = dataBinding.spinner.getSelectedItem().toString();
                salesViewModel.callFlightLeg(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        salesViewModel.setRecyclerViewAdapter(bookRecyclerViewAdapter);
    }

    /**
     * Setup RecyclerView
     */
    private void setSalesRecyclerViewAdapter() {
        dataBinding.recyclerview.setHasFixedSize(true);
        bookRecyclerViewAdapter = new BookRecyclerViewAdapter(salesViewModel);
        dataBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.recyclerview.setAdapter(bookRecyclerViewAdapter);
    }

    Observer<List<String>> authorNameListObserver = new Observer<List<String>>() {
        @Override
        public void onChanged(@Nullable List<String> stringStringMap) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(BookActivity.this, android.R.layout.simple_spinner_item, stringStringMap);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataBinding.spinner.setAdapter(adapter);

        }
    };

    Observer<List<BookData>> bookListObserver = new Observer<List<BookData>>() {
        @Override
        public void onChanged(@Nullable List<BookData> stringStringMap) {
            bookRecyclerViewAdapter.setProductDataList(stringStringMap);
            bookRecyclerViewAdapter.notifyDataSetChanged();
        }
    };

}
