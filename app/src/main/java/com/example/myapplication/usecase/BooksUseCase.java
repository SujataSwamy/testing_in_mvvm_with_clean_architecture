package com.example.myapplication.usecase;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.EspressoIdlingResource;
import com.example.myapplication.repository.BookRepository;
import com.example.myapplication.BookRepositoryContract;

import io.reactivex.annotations.NonNull;

public class BooksUseCase  {

    private static BookRepositoryContract mTaskRepository;

    public BooksUseCase(BookRepositoryContract mTasksRepository) {
        this.mTaskRepository = mTasksRepository;
    }

    public void getBooksNameList(String authorName,@NonNull final BookRepositoryContract.LoadBookListCallback callback) {
        EspressoIdlingResource.increment(); // App is busy until further notice
        mTaskRepository.getBookList(authorName,new BookRepositoryContract.LoadBookListCallback<MutableLiveData>() {
            @Override
            public void onBookListLoaded(MutableLiveData<MutableLiveData> booksList) {
                callback.onBookListLoaded(booksList);
                EspressoIdlingResource.decrement();
            }
            @Override
            public void onDataNotAvailable() {

            }
        });
    }

}
