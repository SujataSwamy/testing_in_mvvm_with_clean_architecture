package com.example.myapplication;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.myapplication.datasource.BookDataSource;
import com.example.myapplication.model.BookData;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class DataSourceTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void testSalesData(){
        List<BookData> bookDataList = new ArrayList<>();
        BookData bookData = new BookData();
        bookData.setBookId("1");
        bookData.setBookName("Book1");
        bookData.setBookPrice((double) 400);
        bookData.setBookId("1");
        bookDataList.add(bookData);
        BookDataSource bookDataSource = Mockito.mock(BookDataSource.class);
        Mockito.doReturn(Observable.just(bookDataList)).when(bookDataSource).getBookNameList("Author1");

        bookDataSource.getBookNameList("Author1")
                .test()
                .assertValues(bookDataList)
                .dispose();
    }

    @Test
    public void testFlightLegData(){
        List<String> stringList = new ArrayList<>();
        stringList.add("Author1");

        BookDataSource bookDataSource = Mockito.mock(BookDataSource.class);
        Mockito.doReturn(Observable.just(stringList)).when(bookDataSource).getAuthorNameList();

        bookDataSource.getAuthorNameList()
                .test()
                .assertValues(stringList)
                .dispose();
    }

}
