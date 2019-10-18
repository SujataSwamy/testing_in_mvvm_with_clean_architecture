package com.example.myapplication;

import androidx.test.espresso.IdlingResource;


public class EspressoIdlingResourceDemo {



    private static final String RESOURCE="GLOBAL";

    private static SimpleCountingIdlingResource mCountingIdlingResource=
            new SimpleCountingIdlingResource(RESOURCE);

    public static void increment(){
        mCountingIdlingResource.increment();
    }

    public static void decrement(){
        mCountingIdlingResource.decrement();
    }

    public static IdlingResource getIdlingResource(){
        return mCountingIdlingResource;
    }
}