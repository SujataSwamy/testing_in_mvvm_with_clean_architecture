package com.example.myapplication.usecase;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.EspressoIdlingResource;
import com.example.myapplication.BookRepositoryContract;
import com.example.myapplication.model.BookData;
import java.util.List;

/*
This is the domain layer class where the business logic happens, which is the code that determines what the app does with
the data coming from the repository before it's exposed to the UI for display.
 */
public class AuthorNameUseCase  {

    private static BookRepositoryContract mTaskRepository;

    public AuthorNameUseCase(BookRepositoryContract mTasksRepository) {
        this.mTaskRepository = mTasksRepository;
    }

    public void getAuthorNamesList( @NonNull final BookRepositoryContract.GetAuthorNameCallback callback) {
        EspressoIdlingResource.increment();
        mTaskRepository.getAuthorNameList(new BookRepositoryContract.GetAuthorNameCallback<List<BookData>>() {
            @Override
            public void onAuthorNameLoaded(MutableLiveData authorNameModel) {
                // Processing logic can be written before sending data back to UI. This can be done in real scenario
                callback.onAuthorNameLoaded(authorNameModel);
                EspressoIdlingResource.decrement();
            }

            @Override
            public void onDataNotAvailable() {
          // handle case when there is no data available
            }
        });
    }
}
