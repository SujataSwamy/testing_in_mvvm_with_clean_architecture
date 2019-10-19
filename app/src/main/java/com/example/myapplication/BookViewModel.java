package com.example.myapplication;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.BookData;
import com.example.myapplication.usecase.AuthorNameUseCase;
import com.example.myapplication.usecase.BooksUseCase;

import java.util.List;
import io.reactivex.annotations.NonNull;

public class BookViewModel extends AndroidViewModel {
    private final MutableLiveData<List<BookData>> bookListLiveData;
    private  MutableLiveData<List<String>> authorNameListLiveData;
    private AuthorNameUseCase authorNameUseCase;
    private BooksUseCase booksUseCase;

    public MutableLiveData<List<String>> getAuthorNameListLiveData() {
        return authorNameListLiveData;
    }

    /**
     * Get Book List and observe in UI
     * @return
     */
    public MutableLiveData<List<BookData>> getBookListLiveData() {
        return bookListLiveData;
    }

    /**
     * Initialize BookViewModel
     *
     * @param application
     * @param
     */
    public BookViewModel(@NonNull Application application, AuthorNameUseCase authorNameUseCase, BooksUseCase booksUseCase) {
        super(application);
        bookListLiveData = new MutableLiveData<>();
        authorNameListLiveData = new MutableLiveData<>();
        this.booksUseCase = booksUseCase;
        this.authorNameUseCase = authorNameUseCase;
        getAuthorNames();
    }

    /**
     * Call for getting Book List From Use Case
     */
    public void getBookNamesList(String authorName) {
        booksUseCase.getBooksNameList(authorName,new BookRepositoryContract.LoadBookListCallback<List<BookData>>() {

            @Override
            public void onBookListLoaded(MutableLiveData booksList) {
                bookListLiveData.setValue((List<BookData>) booksList.getValue());
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    /**
     * Call for gettting Author Names From Use Case
     *
     * @param
     */
    public void getAuthorNames() {
        authorNameUseCase.getAuthorNamesList(new BookRepositoryContract.GetAuthorNameCallback<List<String>>() {
            @Override
            public void onAuthorNameLoaded(MutableLiveData authorNameModel) {
                authorNameListLiveData.setValue((List<String>) authorNameModel.getValue());
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

}
