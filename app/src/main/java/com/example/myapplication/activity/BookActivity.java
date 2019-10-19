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
 * Demo class for showing author names and book names based on author names
 *
 * @author sujata
 */
public class BookActivity extends AppCompatActivity {
    private BookRecyclerViewAdapter bookRecyclerViewAdapter;
    private BookViewModel bookViewModel;
    private ActivityMainBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        BookViewModelFactory factory = BookViewModelFactory.getInstance(getApplication());

        bookViewModel = ViewModelProviders.of(this, factory).get(BookViewModel.class);
        dataBinding.setModel(bookViewModel);
        dataBinding.setLifecycleOwner(this);
        bookViewModel.getAuthorNameListLiveData().observe(this, authorNameListObserver);
        bookViewModel.getBookListLiveData().observe(this,bookListObserver);
        setRecyclerViewAdapter();
        dataBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = dataBinding.spinner.getSelectedItem().toString();
                bookViewModel.getBookNamesList(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * Setup RecyclerView
     */
    private void setRecyclerViewAdapter() {
        dataBinding.recyclerview.setHasFixedSize(true);
        bookRecyclerViewAdapter = new BookRecyclerViewAdapter(bookViewModel);
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
            bookRecyclerViewAdapter.setBookList(stringStringMap);
            bookRecyclerViewAdapter.notifyDataSetChanged();
        }
    };

}
