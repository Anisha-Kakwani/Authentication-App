/*
* Assignment - InClass 04
* File name- MainActivity
* Group - B8
* Group Members
* Anisha Kakwani
* Hiten Changlani */

package com.example.inclass04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginInterface, AccountFragment.AccountInterface, UpdateAccountFragment.UpdateAccountInterface, RegisterAccountFragment.RegisterAccountInterface{
    DataServices.Account loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,new LoginFragment())
                .commit();
    }

    @Override
    public void getLoginCredentials(DataServices.Account user) {
        loggedInUser = user;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,AccountFragment.newInstance(loggedInUser))
                .commit();
    }

    @Override
    public void deleteUser() {
        loggedInUser =null;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new LoginFragment())
                .commit();
    }

    @Override
    public void updateUserInfo() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,UpdateAccountFragment.newInstance(loggedInUser))
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void updateUserProfile(DataServices.Account updatedUSer) {
        loggedInUser = updatedUSer;
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,AccountFragment.newInstance(updatedUSer))
                .commit();
    }

    @Override
    public void cancelUserProfileUpdate() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void addNewlyCreatedUser(DataServices.Account newlyCreatedUser) {
        loggedInUser = newlyCreatedUser;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, AccountFragment.newInstance(loggedInUser))
                .commit();
    }

    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new RegisterAccountFragment())
                .commit();
    }

    @Override
    public void cancelRegisterUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,new LoginFragment())
                .commit();
    }
}