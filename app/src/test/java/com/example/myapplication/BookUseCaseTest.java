package com.example.myapplication;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.datasource.BookDataSource;
import com.example.myapplication.model.BookData;
import com.example.myapplication.usecase.BooksUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class BookUseCaseTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private FakeRepository fakeRepository = new FakeRepository();

    @Mock
    private BookRepositoryContract.LoadBookListCallback mBookListLoadCallBack;

    @Captor
    private ArgumentCaptor<BookDataSource.LoadBookListCallback> mBookListCallBackCaptor;

    private BooksUseCase bookUseCase = spy(new BooksUseCase(fakeRepository));

    @Before
    public void s() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setupTasksRepository() {
        MockitoAnnotations.initMocks(this);
        MutableLiveData<List<BookData>> mapMutableLiveData = new MutableLiveData<>();
        List<BookData> bookdataList = new ArrayList<>();
        BookData bookData = new BookData();
        bookData.setBookId("1");
        bookData.setBookName("Book1");
        bookData.setBookPrice((double) 400);
        bookData.setBookId("1");
        bookdataList.add(bookData);
        mapMutableLiveData.setValue(bookdataList);
        bookUseCase.getBooksNameList("Author1", mBookListLoadCallBack);
        verify(bookUseCase).getBooksNameList(eq("Author1"), mBookListCallBackCaptor.capture());
        mBookListCallBackCaptor.getValue().onBookListLoaded(mapMutableLiveData);
        verify(bookUseCase).getBooksNameList(eq("Author1"), any(BookRepositoryContract.LoadBookListCallback.class));
    }

}
