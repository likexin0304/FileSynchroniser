package com.example.mobile_client;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
         String[] data ={" file 1"," file 2"," file 3"," file 4"," file 5"," file 6"," file 7"," file 8",};
        // Inflate the layout for this fragment

        ListView listView = (ListView)view.findViewById(R.id.home_list);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                data
        );
        listView.setAdapter(listViewAdapter);
        return view;
    }

}
