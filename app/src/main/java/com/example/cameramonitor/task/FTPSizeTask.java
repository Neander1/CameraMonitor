package com.example.cameramonitor.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.example.cameramonitor.R;
import com.example.cameramonitor.ftp.FTPCrawl;

import java.io.IOException;

public class FTPSizeTask extends AsyncTask<FTPCrawl, String, Integer> {
    @SuppressLint("StaticFieldLeak")
    View view;
    @SuppressLint("StaticFieldLeak")
    ListView listView;
    public FTPSizeTask(View view, ListView listView) {
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
        super.onPostExecute(integer);
        NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.number_feed);

        numberPicker.setMaxValue(integer);
        numberPicker.setMinValue(0);


    }
}
