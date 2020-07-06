package com.example.cameramonitor.task;

import android.os.AsyncTask;
import android.view.View;

import com.example.cameramonitor.ftp.FTPCrawl;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FTPDownload extends AsyncTask<String, String, OutputStream> {
    View view;

    public FTPDownload(View view) {
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(OutputStream stream){
        super.onPostExecute(stream);
    }

    @Override
    protected OutputStream doInBackground(String... strings) {
        String server = "kamera";
        String password = "1234";
        int port = 1024;
        FTPCrawl crawl = new FTPCrawl(server, port, password, 0);
        crawl.connect();
        FTPClient connection = crawl.getClient();
        try {
            File file = new File(view.getContext().getCacheDir(), strings[0]);
            connection.retrieveFile(strings[0], null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
