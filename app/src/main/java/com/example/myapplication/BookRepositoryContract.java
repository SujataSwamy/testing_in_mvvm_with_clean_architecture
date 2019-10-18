package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public interface BookRepositoryContract {

    interface GetAuthorNameCallback<T> {
        void onAuthorNameLoaded(MutableLiveData<T> authorNameModel);
        void onDataNotAvailable();
    }
    void getAuthorNameList(@NonNull GetAuthorNameCallback callback);


    interface LoadBookListCallback<T> {
        void onBookListLoaded(MutableLiveData<T> booksList);
        void onDataNotAvailable();
    }
    void getBookList(String authorName, @NonNull LoadBookListCallback callback);
}
