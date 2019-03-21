package com.example.mobile_client;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.content.Context;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment{

    // initial all variables
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
        // connect to the xml
        SignOut =(Button)view.findViewById(R.id.signout);
        EditButton =(ImageButton)view.findViewById(R.id.editButton);


        Username = (TextView)view.findViewById(R.id.Username);

        String username = (String) MySharedPreferences.getuserName(getActivity());
        //String password = (String) MySharedPreferences.getPswd(getActivity());
        Username.setText("Username：" + username);

        // logout function
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Log out")
                        .setMessage("Are you sure you want to Sign Out?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                goToLogin();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.presence_busy)
                        .show();
               // goToLogin();
            }
        });

        //change password function
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditPage();

            }
        });
        return view;
    }


    // jump to login page
    private void goToLogin()
    {
        Intent intent = new Intent(getActivity(), Login_Page.class);
        getActivity().startActivity(intent);
    }
    //jump to edit page
    private void goToEditPage()
    {
        Intent intent = new Intent(getActivity(),EditProfilePage.class);
        getActivity().startActivity(intent);
    }
}




