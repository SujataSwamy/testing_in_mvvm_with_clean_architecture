package com.example.myapplication;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

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

    private FakeRepository fakeAuthorRepository = new FakeRepository();

    private AuthorNameUseCase authorNameUseCase = spy(new AuthorNameUseCase(fakeAuthorRepository));

    @Mock
    private BookRepositoryContract.GetAuthorNameCallback mGetAuthorNameCallBack;
    @Captor
    private ArgumentCaptor<BookRepositoryContract.GetAuthorNameCallback> mAuthorUseCaseCallBackCaptor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAuthorUseCase() {
        MutableLiveData<List<String>> mapMutableLiveData = new MutableLiveData<>();
        List<String> bookdataList = new ArrayList<>();
        bookdataList.add("Author1");
        bookdataList.add("Author2");
        mapMutableLiveData.setValue(bookdataList);
        authorNameUseCase.getAuthorNamesList( mGetAuthorNameCallBack);
        verify(authorNameUseCase).getAuthorNamesList( mAuthorUseCaseCallBackCaptor.capture());
        mAuthorUseCaseCallBackCaptor.getValue().onAuthorNameLoaded(mapMutableLiveData);
        verify(authorNameUseCase).getAuthorNamesList(any(BookRepositoryContract.GetAuthorNameCallback.class));
    }

}
