package com.example.cameramonitor.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cameramonitor.ftp.FTPCrawl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FTPCrawlTask extends AsyncTask<FTPCrawl, String, List<String>> {
    @SuppressLint("StaticFieldLeak")
    ListView listView;
    FTPCrawl crawl;

    public FTPCrawlTask(ListView listView) {
        super();
        this.listView = listView;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        int size = strings.size();
        StringBuilder builder = new StringBuilder("finished loading");
        if (size == 0){
            builder.append(", no entries available");
        } else if (size == 1){
            builder.append(" 1 entry");
        } else {
            builder.append(" ").append(size).append(" entries");
        }
        Toast.makeText(listView.getContext(), builder.toString(), Toast.LENGTH_SHORT).show();
        listView.setAdapter(new ArrayAdapter<>(listView.getContext(), android.R.layout.simple_list_item_1, strings));
    }

    @Override
    protected void onProgressUpdate(String... values) {
        String message = values[0];
        //Toast.makeText()
    }

    @Override
    protected List<String> doInBackground(FTPCrawl... ftpCrawls) {
        crawl = ftpCrawls[0];
        List<String> payLoad = crawl.getList();
        if (payLoad == null){
            List<String> patchList = new ArrayList<>();
            patchList.add("something went wrong there");
            return patchList;
        }
        Collections.reverse(payLoad);

        return payLoad;
    }
}
