package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.BR;
import com.example.myapplication.BookViewModel;
import com.example.myapplication.R;
import com.example.myapplication.model.BookData;
import java.util.List;

/**
 * This class provides a bridge between {@link BookData} & the book recyclerview
 * @author sujata
 */

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.ViewHolder>{
    private BookViewModel salesViewModel;
    private List<BookData> bookList;

    public BookRecyclerViewAdapter(BookViewModel salesViewModel) {
        super();
        this.salesViewModel = salesViewModel;
    }

    @Override
    public BookRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.book_row, parent, false);
        return new BookRecyclerViewAdapter.ViewHolder(binding);
    }

    public BookData getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public void onBindViewHolder(BookRecyclerViewAdapter.ViewHolder holder, int position) {
        final BookData productData = getItem(position);
        holder.bind(salesViewModel, productData);
    }

    @Override
    public int getItemCount() {
        return null == bookList ? 0 : bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private  ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(BookViewModel viewModel, BookData productData) {
            binding.setVariable(com.example.myapplication.BR.viewModel, viewModel);
            binding.setVariable(BR.productItem, productData);
            binding.executePendingBindings();
        }
    }

    public void setBookList(List<BookData> bookList){
        this.bookList = bookList;
    }
}

