package com.example.cameramonitor.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FTPCrawl {
    FTPClient client;
    String password, server;
    int port, count;

    public FTPCrawl(String server, int port, String password, int count) {
        this.password = password;
        this.server = server;
        this.port = port;
        this.count = count;
        this.client = new FTPClient();


    }

    public FTPCrawl(String server, int port, String password) {
        this.password = password;
        this.server = server;
        this.port = port;
        this.client = new FTPClient();
    }

    public FTPCrawl(String server, int port){
        this.server = server;
        this.port = port;
        this.client = new FTPClient();
    }

    public void connect() {
        try {
            int reply;
            client.connect(this.server, this.port);
            client.login(this.server, this.password);
            System.out.println("Connected to " + server + ".");
            System.out.print(client.getReplyString());

            // After connection attempt, you should check the reply code to verify
            // success.
            reply = client.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() throws IOException {
        client.logout();
        if(client.isConnected()) {
            try {
                client.disconnect();
            } catch(IOException ioe) {
                // do nothing
            }
        }
    }

    public List<String> getList() {
        try {
            int reply;
            client.connect(this.server, this.port);
            client.login(this.server, this.password);
            //client.login("", "");
            System.out.println("Connected to " + server + ".");
            System.out.print(client.getReplyString());

            // After connection attempt, you should check the reply code to verify
            // success.
            reply = client.getReplyCode();

            if(!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }

            List<String> list = parseIntel();
            //String[] data = list.toArray(new String[0]);
            client.logout();
            client.disconnect();
            return list;

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(client.isConnected()) {
                try {
                    client.disconnect();
                } catch(IOException ioe) {
                    // do nothing
                }
            }
            //System.exit(error ? 1 : 0);

        }
        return null;
    }

    private List<String> parseIntel() throws IOException {
        List<String> list = new ArrayList<>();
        FTPFile[] target = this.client.listFiles();
        int i = target.length - this.count;                                                                // return #count latest entries
        if (!(i > 0 & i <= target.length)) i = 0;                                                              // make sure count is not bigger than the actual amount of the files

        for (FTPFile file : target) //iterate through all files
            if (i == 0) {

                try {
                    String name = file.getName();
                    name = name.replace(".jpg", "")
                            .replace("photo_", "");                                         //remove unnecessary information
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
                    LocalDateTime dateTime = LocalDateTime.parse(name, formatter);                           //split file name and create LocalDateTime
                    list.add(dateTime.format(DateTimeFormatter.ofPattern("eeee dd.MM.yyyy HH:mm:ss")));      //add formatted DateTime to ArrayList

                } catch (Exception ignored) {

                }
            } else {
                i -= 1;
            }
        return list;
    }

    public int getSize() throws IOException {
        connect();
        int size = this.client.listFiles().length;
        disconnect();
        return size;

    }

    public FTPClient getClient() {
        return client;
    }
}
