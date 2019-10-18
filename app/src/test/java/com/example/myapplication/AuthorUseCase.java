package com.example.myapplication;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.datasource.BookDataSource;
import com.example.myapplication.model.BookData;
import com.example.myapplication.repository.BookRepository;
import com.example.myapplication.usecase.AuthorNameUseCase;

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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class AuthorUseCase {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private FakeRepository salesRepository = new FakeRepository();

    private AuthorNameUseCase authorNameUseCase = spy(new AuthorNameUseCase(salesRepository));

    @Mock
    private BookRepositoryContract.GetAuthorNameCallback mSuccessfullSalesReport;
    @Captor
    private ArgumentCaptor<BookRepositoryContract.GetAuthorNameCallback> mBookDataSourceCallBackCaptor;

    @Before
    public void s() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void setupTasksRepository() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MutableLiveData<List<String>> mapMutableLiveData = new MutableLiveData<>();
        List<String> bookdataList = new ArrayList<>();
        bookdataList.add("Author1");
        bookdataList.add("Author2");
        mapMutableLiveData.setValue(bookdataList);
        authorNameUseCase.getAuthorNamesList( mSuccessfullSalesReport);
        verify(authorNameUseCase).getAuthorNamesList( mBookDataSourceCallBackCaptor.capture());
        mBookDataSourceCallBackCaptor.getValue().onAuthorNameLoaded(mapMutableLiveData);
        verify(authorNameUseCase).getAuthorNamesList(any(BookRepositoryContract.GetAuthorNameCallback.class));

    }

}
