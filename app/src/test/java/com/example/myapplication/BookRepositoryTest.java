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

public class BookRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookDataSource bookDataSource;

    @Mock
    private BookDataSource.LoadBookListCallback loadBookListCallback;

    @Mock
    private BookDataSource.GetAuthorNameCallback mAuthorNameCallback;

    @Captor
    private ArgumentCaptor<BookDataSource.LoadBookListCallback> mLoadBookListCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<BookDataSource.GetAuthorNameCallback> mAuthorNameCallbackCaptor;

    @Before
    public void setupTasksRepository() {
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        bookRepository = BookRepository.getInstance(bookDataSource);
    }

    @After
    public void destroyRepositoryInstance() {
    }

    @Test
    public void testAuthorNamesListCallback() {
        // Given a setup Captor to capture callbacks
        MutableLiveData<List<String>> mapMutableLiveData = new MutableLiveData<>();
        List<String> stringStringMap = new ArrayList<>();
        stringStringMap.add("Author1");
        mapMutableLiveData.setValue(stringStringMap);
        bookRepository.getAuthorNameList(mAuthorNameCallback);
        verify(bookDataSource).getAuthorNameList(mAuthorNameCallbackCaptor.capture());
        mAuthorNameCallbackCaptor.getValue().onAuthorNameLoaded(mapMutableLiveData);
        verify(bookDataSource).getAuthorNameList(ArgumentMatchers.any(BookDataSource.GetAuthorNameCallback.class));
    }

    @Test
    public void testBooksListCallback() {
        MutableLiveData<List<BookData>> mapMutableLiveData = new MutableLiveData<>();
        List<BookData> bookdataList = new ArrayList<>();
        BookData bookData = new BookData();
        bookData.setBookId("1");
        bookData.setBookName("Book1");
        bookData.setBookPrice((double) 400);
        bookData.setBookId("1");
        bookdataList.add(bookData);
        mapMutableLiveData.setValue(bookdataList);
        bookRepository.getBookList("author1", loadBookListCallback);
        verify(bookDataSource).getBookList(ArgumentMatchers.eq("author1"), mLoadBookListCallbackArgumentCaptor.capture());
        mLoadBookListCallbackArgumentCaptor.getValue().onBookListLoaded(mapMutableLiveData);
        verify(bookDataSource).getBookList(ArgumentMatchers.eq("author1"), ArgumentMatchers.any(BookRepositoryContract.LoadBookListCallback.class));
    }
}
