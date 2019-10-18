package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.model.BookData;

import java.util.ArrayList;
import java.util.List;

public class FakeRepository implements BookRepositoryContract {
    @Override
    public void getAuthorNameList(@NonNull GetAuthorNameCallback callback) {
        MutableLiveData<List<String>> mapMutableLiveData = new MutableLiveData<>();
        List<String> stringStringMap = new ArrayList<>();
        stringStringMap.add("Author1");
        stringStringMap.add("Author2");
        stringStringMap.add("Author3");
        mapMutableLiveData.setValue(stringStringMap);
        callback.onAuthorNameLoaded(mapMutableLiveData);
    }

    @Override
    public void getBookList(String authorName, @NonNull LoadBookListCallback callback) {
        MutableLiveData<List<BookData>> mapMutableLiveData = new MutableLiveData<>();

        List<BookData> bookdataList = new ArrayList<>();
        if(authorName.equalsIgnoreCase("Author1")){
            BookData bookData = new BookData();
            bookData.setBookId("1");
            bookData.setBookName("Book1");
            bookData.setBookPrice((double) 400);
            bookData.setBookId("1");
            bookdataList.add(bookData);
        }else if (authorName.equalsIgnoreCase("Author2")){
            BookData bookData = new BookData();
            bookData.setBookId("2");
            bookData.setBookName("Book2");
            bookData.setBookPrice((double) 500);
            bookData.setBookId("2");
            bookdataList.add(bookData);
        }else if(authorName.equalsIgnoreCase("Author3")){
            BookData bookData = new BookData();
            bookData.setBookId("3");
            bookData.setBookName("Book3");
            bookData.setBookPrice((double) 600);
            bookData.setBookId("3");
            bookdataList.add(bookData);
        }else if(authorName.equalsIgnoreCase("Author4")){
            BookData bookData = new BookData();
            bookData.setBookId("4");
            bookData.setBookName("Book4");
            bookData.setBookPrice((double) 700);
            bookData.setBookId("4");
            bookdataList.add(bookData);
        }

        mapMutableLiveData.setValue(bookdataList);
        callback.onBookListLoaded(mapMutableLiveData);
    }
}
