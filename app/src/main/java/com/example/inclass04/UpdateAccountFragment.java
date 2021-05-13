package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "ACCOUNT";

    // TODO: Rename and change types of parameters
    private DataServices.Account user;

    public UpdateAccountFragment() {
        // Required empty public constructor
    }
    public static UpdateAccountFragment newInstance(DataServices.Account user) {
        UpdateAccountFragment fragment = new UpdateAccountFragment();
        Bundle args = new Bundle();

        args.putSerializable(ARG_PARAM, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UpdateAccountFragment.UpdateAccountInterface) {
            updateAccountListener = (UpdateAccountFragment.UpdateAccountInterface)context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (DataServices.Account)getArguments().getSerializable(ARG_PARAM);
        }
    }
    EditText nameField, pwdField;
    TextView email;
    String updatedName, updatedPassword;
    DataServices.Account updatedUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_account, container, false);
        getActivity().setTitle(getResources().getString(R.string.update_account_label));
        email = view.findViewById(R.id.textView_email);
        email.setText(user.getEmail());
        nameField = view.findViewById(R.id.editText_update_name);
        nameField.setText(user.getName());
        pwdField = view.findViewById(R.id.editText_update_passwprd);
        pwdField.setText(user.getPassword());

        view.findViewById(R.id.button_update_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedName = nameField.getText().toString();
                updatedPassword = pwdField.getText().toString();
                if(updatedName.isEmpty() || updatedPassword.isEmpty()){
                    Toast.makeText(getActivity(),getResources().getString(R.string.toast_mandatory), Toast.LENGTH_LONG).show();
                }
                else{
                    updatedUser = DataServices.update(user,updatedName,updatedPassword);
                    updateAccountListener.updateUserProfile(updatedUser);
                }
            }
        });
        view.findViewById(R.id.textView_update_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccountListener.cancelUserProfileUpdate();
            }
        });
        return view;
    }
    UpdateAccountInterface updateAccountListener;

    public interface UpdateAccountInterface{
        void updateUserProfile(DataServices.Account user);
        void cancelUserProfileUpdate();
    }
}