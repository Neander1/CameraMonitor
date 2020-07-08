package com.example.cameramonitor.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.example.cameramonitor.R;
import com.example.cameramonitor.ftp.FTPCrawl;

import java.io.IOException;
import java.util.ArrayList;

public class FeedCleanUpTask extends AsyncTask<FTPCrawl, String, Integer> {
    @SuppressLint("StaticFieldLeak")
    View view;
    @SuppressLint("StaticFieldLeak")
    ListView listView;
    public FeedCleanUpTask(View view, ListView listView) {
        super();
        this.view = view;
        this.listView = listView;
    }

    @Override
    protected Integer doInBackground(FTPCrawl... ftpCrawls) {
        FTPCrawl crawl = ftpCrawls[0];
        int size = 0;
        try {
            size = crawl.getSize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        NumberPicker numberPicker = (NumberPicker) view.getRootView().findViewById(R.id.number_feed);

        numberPicker.setMaxValue(integer);
        numberPicker.setMinValue(0);

        numberPicker.setValue(integer);

        FTPCrawlTask task = new FTPCrawlTask(listView);
        task.execute((FTPCrawl) new FTPCrawl("kamera", 1024, "1234", integer));
    }

    private String[] setDisplayedValues(int maxValue){
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <= maxValue; i++) {
            if (i % 5 == 0){
                list.add(String.valueOf(i));
            }
        }
        list.add(String.valueOf(maxValue));
        return list.toArray(new String[0]);
    }
}
