package com.example.myapplication;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.example.myapplication.datasource.BookDataSource;
import com.example.myapplication.model.BookData;
import com.example.myapplication.repository.BookRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.verify;

public class SalesRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private BookRepository mTasksRepository;

    @Mock
    private BookDataSource mTasksLocalDataSource;

    @Mock
    private BookDataSource.LoadBookListCallback mLoadFlightCallBack;

    @Mock
    private BookDataSource.GetAuthorNameCallback mSuccessfullSalesReport;

    @Captor
    private ArgumentCaptor<BookDataSource.LoadBookListCallback> mLoadFlightCallBackCaptor;

    @Captor
    private ArgumentCaptor<BookDataSource.GetAuthorNameCallback> mSuccessfullSalesReportCaptor;

    @Before
    public void setupTasksRepository() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        mTasksRepository = BookRepository.getInstance(mTasksLocalDataSource);
    }

    @After
    public void destroyRepositoryInstance() {
    }

    @Test
    public void getTasks_repositoryCachesAfterFirstApiCall() {
        // Given a setup Captor to capture callbacks
        // When two calls are issued to the tasks repository
        MutableLiveData<List<String>> mapMutableLiveData = new MutableLiveData<>();
        List<String> stringStringMap = new ArrayList<>();
        stringStringMap.add("Author1");
        mapMutableLiveData.setValue(stringStringMap);
        mTasksRepository.getAuthorNameList(mSuccessfullSalesReport);
        verify(mTasksLocalDataSource).getAuthorNameList(mSuccessfullSalesReportCaptor.capture());
        mSuccessfullSalesReportCaptor.getValue().onAuthorNameLoaded(mapMutableLiveData);
        verify(mTasksLocalDataSource).getAuthorNameList(ArgumentMatchers.any(BookDataSource.GetAuthorNameCallback.class));
    }

    @Test
    public void getTasks_repositoryCachesAfterFirstApiCall1() {
        MutableLiveData<List<BookData>> mapMutableLiveData = new MutableLiveData<>();
        List<BookData> bookdataList = new ArrayList<>();
        BookData bookData = new BookData();
        bookData.setBookId("1");
        bookData.setBookName("Book1");
        bookData.setBookPrice((double) 400);
        bookData.setBookId("1");
        bookdataList.add(bookData);
        mapMutableLiveData.setValue(bookdataList);
        mTasksRepository.getBookList("author1", mLoadFlightCallBack);
        verify(mTasksLocalDataSource).getBookList(ArgumentMatchers.eq("author1"), mLoadFlightCallBackCaptor.capture());
        mLoadFlightCallBackCaptor.getValue().onBookListLoaded(mapMutableLiveData);
        verify(mTasksLocalDataSource).getBookList(ArgumentMatchers.eq("author1"), ArgumentMatchers.any(BookRepositoryContract.LoadBookListCallback.class));
    }
}
