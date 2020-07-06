package com.example.cameramonitor.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cameramonitor.R;
import com.example.cameramonitor.ftp.FTPCrawl;
import com.example.cameramonitor.task.FTPCrawlTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FeedFragment extends Fragment {
    ListView listView;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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




        listView = (ListView)view.findViewById(R.id.list_view_feed);

        FTPCrawlTask task = new FTPCrawlTask(listView);
        task.execute((FTPCrawl) new FTPCrawl("kamera", 1024, "1234", 15));
        /*ArrayList<String> list = new ArrayList<>();
        list.add("test");
        list.add("test");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                list);

        listView.setAdapter(adapter);*/

        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.btn_feed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Reloading ...", Toast.LENGTH_SHORT).show();

                FTPCrawlTask task = new FTPCrawlTask(listView);
                task.execute((FTPCrawl) new FTPCrawl("kamera", 1024, "1234", 10));


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = adapterView.getItemAtPosition(i);
                String string = o.toString();
                Toast.makeText(getContext(),string,Toast.LENGTH_SHORT).show();


                /*StringBuilder builder = new StringBuilder("http://kamera:12345/photo_");
                String item = o.toString();

                String s1 = "_";
                String s2 = "-";

                String[] dateWhole = item.split(" ");

                String[] date = dateWhole[1].split(".");
                String[] time = dateWhole[2].split(":");

                builder.append(date[0]).append(s2).append(date[1])          //don't look :o
                        .append(s2).append(date[2]).append(s1).append(time[0])
                        .append(s2).append(time[1]).append(s2).append(time[2])
                        .append(".jpg");*/




            }
        });
        return view;

    }
}