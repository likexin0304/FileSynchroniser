package com.example.mobile_client;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment{

    View v;
    private Button SignOut;
    private TextView Username;
    private TextView Password;
    private ImageButton EditButton;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        SignOut =(Button)view.findViewById(R.id.signout);
        EditButton =(ImageButton)view.findViewById(R.id.editButton);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });

        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditPage();

            }
        });
        return view;
    }



    private void goToLogin()
    {
        Intent intent = new Intent(getActivity(), Login_Page.class);
        getActivity().startActivity(intent);
    }
    private void goToEditPage()
    {
        Intent intent = new Intent(getActivity(),EditProfilePage.class);
        getActivity().startActivity(intent);
    }
}




