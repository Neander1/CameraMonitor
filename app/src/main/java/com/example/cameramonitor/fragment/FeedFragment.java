package com.example.cameramonitor.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cameramonitor.R;
import com.example.cameramonitor.ftp.FTPCrawl;
import com.example.cameramonitor.task.FTPCrawlTask;
import com.example.cameramonitor.task.FTPSizeTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {
    ListView listView;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    NumberPicker numberPicker;
    FloatingActionButton button;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);


        listView = (ListView) view.findViewById(R.id.list_view_feed);
        numberPicker = (NumberPicker) view.findViewById(R.id.number_feed);
        button = (FloatingActionButton) view.findViewById(R.id.btn_feed);

        FTPSizeTask sizeTask = new FTPSizeTask(view, listView);
        sizeTask.execute((FTPCrawl) new FTPCrawl("kamera", 1024));

        int initial = 10;
        numberPicker.setMinValue(0);
        numberPicker.setValue(initial);
        loadListItems(initial);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Reloading ...", Toast.LENGTH_SHORT).show();

                int count = numberPicker.getValue();
                loadListItems(count);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = adapterView.getItemAtPosition(i);
                String target = o.toString();
                Toast.makeText(getContext(), target, Toast.LENGTH_SHORT).show();
                loadImage(target);
            }
        });
        return view;

    }

    private void loadImage(String target){
        StringBuilder builder = new StringBuilder("http://kamera:12345/photo_");

        String s1 = "_";
        String s2 = "-";

        String[] dateWhole = target.split(" ");

        String[] date = dateWhole[1].split("\\.");
        String[] time = dateWhole[2].split(":");

        builder.append(date[2]).append(s2).append(date[1])          //don't look :o
                .append(s2).append(date[0]).append(s1).append(time[0])
                .append(s2).append(time[1]).append(s2).append(time[2])
                .append(".jpg");

        Picasso.get().load(builder.toString()).fit().centerInside().into((ImageView) Objects.requireNonNull(getActivity()).findViewById(R.id.image_view_feed));


    }

    private void loadListItems(int count){
        FTPCrawlTask task = new FTPCrawlTask(listView);
        task.execute((FTPCrawl) new FTPCrawl("kamera", 1024, "1234", count));

    }
}