package com.example.myapplication.repository;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.EspressoIdlingResource;
import com.example.myapplication.BookRepositoryContract;
import com.example.myapplication.datasource.BookDataSource;

/*
The Repository layer is the one that performs the logic of data access. Your responsibility is to obtain them and check where they are, deciding where to look at each moment.
 */
public class BookRepository implements BookRepositoryContract {

    private static BookRepository INSTANCE;
    private final BookRepositoryContract bookDatasource;

    public BookRepository(BookRepositoryContract bookDatasource) {
        this.bookDatasource = bookDatasource;
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     *
     * @return the {@link BookRepository} instance
     */
    public static BookRepository getInstance(
             BookDataSource bookDatasource) {
        if (INSTANCE == null) {
            synchronized (BookRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BookRepository(bookDatasource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getAuthorNameList(@NonNull final GetAuthorNameCallback callback) {
        EspressoIdlingResource.increment();
        bookDatasource.getAuthorNameList( new GetAuthorNameCallback() {
            @Override
            public void onAuthorNameLoaded(MutableLiveData authorNameModel) {
                callback.onAuthorNameLoaded(authorNameModel);
                EspressoIdlingResource.decrement();
            }

            @Override
            public void onDataNotAvailable() {
     // Handle case when there is no data available
            }
        });
    }

    @Override
    public void getBookList(String authorName, @NonNull final LoadBookListCallback callback) {
        EspressoIdlingResource.increment();
        bookDatasource.getBookList(authorName,new LoadBookListCallback() {
            @Override
            public void onBookListLoaded(MutableLiveData booksList) {
                callback.onBookListLoaded(booksList);
                EspressoIdlingResource.decrement();
            }

            @Override
            public void onDataNotAvailable() {
               // Handle case when there is no data available
            }
        });
    }
}
