package com.nihal.visitormanagement;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.nihal.visitormanagement.Fragments.CheckInFragment;
import com.nihal.visitormanagement.databinding.ActivityAddEditBinding;

public class AddEditActivity extends AppCompatActivity {

    private static final String TAG = "AddEditActivity";
    private AppBarConfiguration appBarConfiguration;
    private ActivityAddEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_edit);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEditActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_add_edit);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

}