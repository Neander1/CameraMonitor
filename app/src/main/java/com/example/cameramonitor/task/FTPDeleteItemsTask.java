package com.example.cameramonitor.task;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import com.example.cameramonitor.ftp.FTPCrawl;

import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class FTPDeleteItemsTask extends AsyncTask<Void, String, Void> {
    @SuppressLint("StaticFieldLeak")
    View view;
    @SuppressLint("StaticFieldLeak")
    ListView listView;

    public FTPDeleteItemsTask(View view, ListView listView) {
        this.listView = listView;
        this.view = view;
    }

    @Override
    protected Void doInBackground(Void ... voids) {
        FTPCrawl crawl = new FTPCrawl("kamera", 1024, "1234");
        crawl.connect();

        FTPFile[] files = new FTPFile[0];
        try {
            files = crawl.getClient().listFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (FTPFile current : files) {

            Calendar currentCalendar = current.getTimestamp();

            LocalDate currentDate = LocalDateTime.ofInstant(currentCalendar.toInstant(), currentCalendar.getTimeZone().toZoneId()).toLocalDate();

            LocalDate weeksAgo = LocalDate.now().minusWeeks(2);
            if (weeksAgo.isAfter(currentDate)){
                try {
                    String fileName = current.getName();
                    crawl.getClient().deleteFile(fileName);
                    System.out.println("-----------------------DELETED" + fileName + "-----------------------");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        FeedCleanUpTask task = new FeedCleanUpTask(view, listView);
        task.execute(new FTPCrawl("kamera", 1024));
    }

}
