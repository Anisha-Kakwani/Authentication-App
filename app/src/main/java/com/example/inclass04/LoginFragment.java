package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    EditText emailText, passwordText;
    String email, password;
    DataServices.Account userLogin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle(getResources().getString(R.string.login_label));
        emailText = view.findViewById(R.id.editText_email);
        passwordText = view.findViewById(R.id.editText_password);

        view.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailText.getText().toString();
                password = passwordText.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getActivity(),getResources().getString(R.string.toast_mandatory), Toast.LENGTH_LONG).show();
                }
                else{
                    userLogin = DataServices.login(email,password);
                    if(userLogin==null){
                        Toast.makeText(getActivity(), getResources().getString(R.string.toast_invalid), Toast.LENGTH_SHORT).show();
                        emailText.setText("");
                        passwordText.setText("");
                    }
                    else{
                        loginListener.getLoginCredentials(userLogin);
                    }

                }

            }
        });
        view.findViewById(R.id.textView_createAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginListener.createNewAccount();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginInterface) {
            loginListener = (LoginInterface)context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IListener");
        }
    }

    LoginInterface loginListener;

    public interface LoginInterface{
        void getLoginCredentials(DataServices.Account userLogin);
        void createNewAccount();
    }
}