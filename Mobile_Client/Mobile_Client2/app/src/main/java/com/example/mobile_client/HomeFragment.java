package com.example.mobile_client;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
    private List<File_icon> fileList = new ArrayList<File_icon>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        initFile();
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FileAdapter adapter = new FileAdapter(getActivity(),R.layout.file_item,fileList);

        ListView listView = (ListView)view.findViewById(R.id.home_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        Intent intent = new Intent(getActivity(),Item_detail_Page.class);
                        startActivity(intent);
                }
            }
        });



        return view;
    }


    private void initFile() {
        fileList.clear();
        for (int i = 0; i < 1; i++) {
            File_icon file1 = new File_icon("file 1", R.drawable.ic_folder_black_24dp);
            fileList.add(file1);
            File_icon file2 = new File_icon("file 2", R.drawable.ic_folder_black_24dp);
            fileList.add(file2);
            //File_icon file3 = new File_icon("file 3", R.drawable.ic_folder_black_24dp);

        }
    }





//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.toolbar_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

}
