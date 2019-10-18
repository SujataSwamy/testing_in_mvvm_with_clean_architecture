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

public class LocalDataSourceTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void testSalesData(){
       List<BookData> salesUtilDataModels = new ArrayList<>();
        BookDataSource salesLocalDataSource = Mockito.mock(BookDataSource.class);
        Mockito.doReturn(Observable.just(salesUtilDataModels)).when(salesLocalDataSource).getBookNameList("Author1");

        salesLocalDataSource.getBookNameList("Author1")
                .test()
                .assertValues(salesUtilDataModels)
                .dispose();
    }

    @Test
    public void testFlightLegData(){
        List<String> stringList = new ArrayList<>();
        stringList.add("Author1");

        BookDataSource salesLocalDataSource = Mockito.mock(BookDataSource.class);
        Mockito.doReturn(Observable.just(stringList)).when(salesLocalDataSource).getAuthorNameList();

        salesLocalDataSource.getAuthorNameList()
                .test()
                .assertValues(stringList)
                .dispose();
    }

}
