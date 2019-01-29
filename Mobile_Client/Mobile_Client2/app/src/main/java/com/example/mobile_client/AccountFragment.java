package com.example.mobile_client;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener{

    View v;
    private Button SignOut;
    private TextView Username;
    private TextView Password;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_account, container, false); //inflater.inflate(R.layout.fragment_account, container, false);
        SignOut= (Button) v.findViewById(R.id.SignOut);
       // SignOut.setOnClickListener(this);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick(v);
            }
        });
        return v;
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction fr =getFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_home, new HomeFragment());
        fr.commit();
    }
}
