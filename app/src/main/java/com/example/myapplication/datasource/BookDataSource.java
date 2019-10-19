package com.example.myapplication.datasource;


import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.EspressoIdlingResource;
import com.example.myapplication.BookRepositoryContract;
import com.example.myapplication.model.BookData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//Dummy Book Data Source
public class BookDataSource implements BookRepositoryContract {

    private static BookDataSource bookDataSource;
    private GetAuthorNameCallback authorCallBack;
    private LoadBookListCallback bookCallBack;

    // get instance of book datasource
    public static BookDataSource getInstance(){
        if (bookDataSource == null){
            bookDataSource = new BookDataSource();
        }
        return bookDataSource;
    }

    // get authorname list
    public Observable<List<String>> getAuthorNameList(){

        Observable<List<String>> observer = Observable.fromCallable(new Callable<List<String> >() {
            @Override
            public List<String>  call() throws Exception {
                EspressoIdlingResource.increment();
                return getAuthorName();
            }
        });
        observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).
                        subscribeWith(new Observer<List<String> >() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<String>  stringStringMap) {
                                MutableLiveData<List<String> > mapMutableLiveData = new MutableLiveData<>();
                                mapMutableLiveData.setValue(stringStringMap);
                                authorCallBack.onAuthorNameLoaded(mapMutableLiveData);
                                EspressoIdlingResource.decrement();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
        return observer;

    }

    // get authorname
    private List<String> getAuthorName() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Author1");
        stringArrayList.add("Author2");
        stringArrayList.add("Author3");
        stringArrayList.add("Author4");
        return stringArrayList;
    }

    // get author name list
    @Override
    public void getAuthorNameList(@androidx.annotation.NonNull GetAuthorNameCallback callback) {
        this.authorCallBack = callback;
        getAuthorNameList();

    }

    // get book list
    @Override
    public void getBookList(String authorName, @androidx.annotation.NonNull LoadBookListCallback callback) {
        this.bookCallBack = callback;
        getBookNameList(authorName);
    }

    // Get Dummy Book Name List
    public Observable<List<BookData>> getBookNameList(final String authorName) {
        Observable<List<BookData>> observer = Observable.fromCallable(new Callable<List<BookData> >() {
            @Override
            public List<BookData>  call() throws Exception {
                return getBookData(authorName);
            }
        });
        observer.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())


                .subscribeOn(Schedulers.io()).
                subscribeWith(new Observer<List<BookData> >() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<BookData> stringStringMap) {
                        MutableLiveData<List<BookData> > mapMutableLiveData = new MutableLiveData<>();
                        mapMutableLiveData.setValue(stringStringMap);
                        bookCallBack.onBookListLoaded(mapMutableLiveData);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return observer;
    }

    // Get Dummy Book Data
    public List<BookData> getBookData(String authorName) {
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
        return bookdataList;
    }
}
