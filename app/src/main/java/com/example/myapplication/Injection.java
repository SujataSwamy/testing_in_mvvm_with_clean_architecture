package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.myapplication.datasource.BookDataSource;
import com.example.myapplication.repository.BookRepository;

import static androidx.core.util.Preconditions.checkNotNull;

public class Injection {

    @SuppressLint("RestrictedApi")
    public static BookRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        return BookRepository.getInstance( BookDataSource.getInstance());
    }
}
