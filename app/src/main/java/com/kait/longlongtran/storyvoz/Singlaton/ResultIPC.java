package com.kait.longlongtran.storyvoz.Singlaton;

import com.kait.longlongtran.storyvoz.Model.StoryModel;

/**
 * Created by LongLongTran on 1/27/2018.
 */

public class ResultIPC {
    private static ResultIPC instance;

    public synchronized static ResultIPC get() {
        if (instance == null) {
            instance = new ResultIPC();
        }
        return instance;
    }

    private int sync = 0;

    private StoryModel largeData;

    public int setLargeData(StoryModel largeData) {
        this.largeData = largeData;
        return ++sync;
    }

    public StoryModel getLargeData(int request) {
        return (request == sync) ? largeData : null;
    }
}
