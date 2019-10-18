package com.example.myapplication;

import android.app.Application;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import com.example.myapplication.activity.BookActivity;
import com.example.myapplication.datasource.BookDataSource;
import com.example.myapplication.model.BookData;
import com.example.myapplication.usecase.AuthorNameUseCase;
import com.example.myapplication.usecase.BooksUseCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SalesViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private AuthorNameUseCase mAuthorNameUseCase;

    @Mock
    private BooksUseCase mBookUseCase;

    @Mock
    private Application application;

    @Mock
    private BookActivity salesReportActivity;

    @Captor
    private ArgumentCaptor<BookDataSource.LoadBookListCallback> mLoadBookListCaptor;

    @Captor
    private ArgumentCaptor<BookDataSource.GetAuthorNameCallback> mGetAuthorNameArgumentCaptor;

    @Mock
    private BookViewModel bookViewModel;

    @Before
    public void setupAddEditTaskViewModel() {
        MockitoAnnotations.initMocks(this);
        bookViewModel = spy(new BookViewModel(application,mAuthorNameUseCase,mBookUseCase));
    }

    @Test
    public void setMenuValue(){
        MutableLiveData<List<BookData>> mapMutableLiveData = new MutableLiveData<>();
        List<BookData> bookdataList = new ArrayList<>();
            BookData bookData = new BookData();
            bookData.setBookId("1");
            bookData.setBookName("Book1");
            bookData.setBookPrice((double) 400);
            bookData.setBookId("1");
            bookdataList.add(bookData);
        mapMutableLiveData.setValue(bookdataList);
        bookViewModel.callFlightLeg("Author1");
        verify(mBookUseCase).getBooksNameList(ArgumentMatchers.eq("Author1"),mLoadBookListCaptor.capture());
        mLoadBookListCaptor.getValue().onBookListLoaded(mapMutableLiveData);
        assertThat(bookViewModel.getBookListLiveData().getValue(), is(notNullValue()));
    }

    @Test
    public void populateTask_callsRepoAndUpdatesView() {
        MutableLiveData<List<String>> mapMutableLiveData = new MutableLiveData<>();
        List<String> stringStringMap = new ArrayList<>();
        stringStringMap.add("author1");
        mapMutableLiveData.setValue(stringStringMap);
        verify(mAuthorNameUseCase).getAuthorNamesList(mGetAuthorNameArgumentCaptor.capture());
        mGetAuthorNameArgumentCaptor.getValue().onAuthorNameLoaded(mapMutableLiveData);
        assertThat(bookViewModel.getAuthorNameListLiveData().getValue().size(), is(equalTo(1)));
    }
}
