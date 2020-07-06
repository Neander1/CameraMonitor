package com.example.cameramonitor.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;

import com.example.cameramonitor.ftp.FTPCrawl;

import java.io.IOException;

public class FTPSizeTask extends AsyncTask<FTPCrawl, String, Integer> {
    @SuppressLint("StaticFieldLeak")
    View view;
    public FTPSizeTask(View view) {
        super();
        this.view = view;
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
        //TODO: display max choosable items
    }
}
