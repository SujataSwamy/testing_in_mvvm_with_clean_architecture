package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.repository.BookRepository;
import com.example.myapplication.usecase.AuthorNameUseCase;
import com.example.myapplication.usecase.BooksUseCase;

/**
 *
 * <p>
 * This creator is to showcase how to inject dependencies into ViewModels.
 */
public class BookViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile BookViewModelFactory INSTANCE;
    private static Application application_;

    private final BookRepository mSalesRepository;

    public static BookViewModelFactory getInstance(Application application) {
        application_ = application;
        if (INSTANCE == null) {
            synchronized (BookViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BookViewModelFactory(
                            Injection.provideTasksRepository(application.getApplicationContext()));
                }
            }
        }
        return INSTANCE;
    }

    private BookViewModelFactory(BookRepository repository) {
        mSalesRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BookViewModel.class)) {
            return (T) new BookViewModel(application_, new AuthorNameUseCase(mSalesRepository), new BooksUseCase(mSalesRepository));
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
