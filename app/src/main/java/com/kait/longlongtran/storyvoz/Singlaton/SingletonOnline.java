package com.kait.longlongtran.storyvoz.Singlaton;

import com.kait.longlongtran.storyvoz.Model.StoryModelOnline;

/**
 * Created by LongLongTran on 2/11/2018.
 */

public class SingletonOnline {
    private static SingletonOnline instance;

    public synchronized static SingletonOnline get() {
        if (instance == null) {
            instance = new SingletonOnline();
        }
        return instance;
    }

    private int sync = 0;

    private StoryModelOnline largeData;

    public int setLargeData(StoryModelOnline largeData) {
        this.largeData = largeData;
        return ++sync;
    }

    public StoryModelOnline getLargeData(int request) {
        return (request == sync) ? largeData : null;
    }
}
