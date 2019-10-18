package com.example.myapplication;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.datasource.BookDataSource;
import com.example.myapplication.model.BookData;
import com.example.myapplication.repository.BookRepository;
import com.example.myapplication.usecase.AuthorNameUseCase;
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

    private FakeRepository salesRepository = new FakeRepository();

    @Mock
    private BookRepositoryContract.LoadBookListCallback mSuccessfullSalesReport;

    @Captor
    private ArgumentCaptor<BookDataSource.LoadBookListCallback> mBookListCallBackCaptor;

    private BooksUseCase salesUseCase = spy(new BooksUseCase(salesRepository));

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
        salesUseCase.getBooksNameList("Author1", mSuccessfullSalesReport);
        verify(salesUseCase).getBooksNameList(eq("Author1"), mBookListCallBackCaptor.capture());
        mBookListCallBackCaptor.getValue().onBookListLoaded(mapMutableLiveData);
        verify(salesUseCase).getBooksNameList(eq("Author1"), any(BookRepositoryContract.LoadBookListCallback.class));

    }

}
