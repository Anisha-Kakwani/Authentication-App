package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AccountFragment extends Fragment {

    private static final String ARG_PARAM1 = "User Name";
    private String name_param;

    public AccountFragment() {
        // Required empty public constructor
    }
    public static AccountFragment newInstance(DataServices.Account user) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, user.getName());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.LoginInterface) {
            accountListener = (AccountFragment.AccountInterface)context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name_param = getArguments().getString(ARG_PARAM1);
        }
    }
    TextView name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        getActivity().setTitle(getResources().getString(R.string.account_label));
        name = view.findViewById(R.id.textView_name);
        name.setText(name_param);
        view.findViewById(R.id.button_logOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountListener.deleteUser();
            }
        });
        view.findViewById(R.id.button_editProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountListener.updateUserInfo();
            }
        });
        return view;
    }
    AccountInterface accountListener;
    public interface AccountInterface{
        void deleteUser();
        void updateUserInfo();
    }
}