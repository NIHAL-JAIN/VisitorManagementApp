package com.nihal.visitormanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.nihal.visitormanagement.Adapters.FragmentsAdapter;
import com.nihal.visitormanagement.Fragments.CheckInFragment;
import com.nihal.visitormanagement.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements CursorRecyclerViewAdapter.OnVisitorClickListener,
                                  AddEditActivityFragment.OnSaveClicked{

    ActivityMainBinding binding;
    public static final String TAG = "MainActivity";

    private static final String ADD_EDIT_FRAGMENT = "AddEditFragment";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewPage.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        binding.fab.show();
                        break;
                    case 1:
                        binding.fab.hide();

                        break;
                    default:
                        binding.fab.show();
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        String[] projection = {
//                VisitorContract.Columns._ID,
//                VisitorContract.Columns.VISITOR_NAME,
//                VisitorContract.Columns.VISITOR_PHONE,
//                VisitorContract.Columns.VISITOR_ADDRESS,
//                VisitorContract.Columns.VISITOR_CITY,
//                VisitorContract.Columns.VISITOR_SORTORDER};
//
//        ContentResolver contentResolver = getContentResolver();
//        ContentValues values = new ContentValues();

        // Edit record
//        values.put(VisitorContract.Columns.VISITOR_NAME,"Raju singh");
//        values.put(VisitorContract.Columns.VISITOR_PHONE,"8871151724");
//        int count = contentResolver.update(VisitorContract.buildVisitorUri(3),values,null,null);
//        Log.d(TAG, "onCreate: " + count + "record(s) updated");


//        insert record

//        values.put(VisitorContract.Columns.VISITOR_NAME,"Rajnesh singh");
//        values.put(VisitorContract.Columns.VISITOR_PHONE,"8871151724");
//        values.put(VisitorContract.Columns.VISITOR_ADDRESS,"Katra bazar");
//        values.put(VisitorContract.Columns.VISITOR_CITY,"Pune");
//        values.put(VisitorContract.Columns.VISITOR_SORTORDER, 2);
//        Uri uri = contentResolver.insert(VisitorContract.CONTENT_URI,values);

//        Cursor cursor = contentResolver.query(VisitorContract.CONTENT_URI,
//        Cursor cursor = contentResolver.query(VisitorContract.buildVisitorUri(2),
//                projection,
//                null,
//                null,
//                VisitorContract.Columns.VISITOR_SORTORDER);
//
//        if(cursor!=null) {
//            Log.d(TAG,"onCreate: number of rows: " + cursor.getCount());
//            while (cursor.moveToNext()){
//                for(int i = 0; i<cursor.getColumnCount();i++){
//                    Log.d(TAG,"onCreate:" + cursor.getColumnName(i) + ":" + cursor.getString(i));
//                }
//                Log.d(TAG,"onCreate: ========================================");
//
//            }
//            cursor.close();
//        }

//        AppDatabase appDatabase = AppDatabase.getInstance(this);
//        final SQLiteDatabase db = appDatabase.getReadableDatabase();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               visitorEditRequest(null);
            }
        });
        binding.tabLayout.setupWithViewPager(binding.viewPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,PhoneNumberActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDeleteClick(@NonNull Visitor visitor) {

        getContentResolver().delete(VisitorContract.buildVisitorUri(visitor.getId()),null,null);

    }


    private void visitorEditRequest(Visitor visitor){
        Log.d(TAG, "visitorEditRequest: starts");

        Intent detailIntent = new Intent(this,AddEditActivity.class);
        if(visitor != null ){
            detailIntent.putExtra(Visitor.class.getSimpleName(),visitor);
            startActivity(detailIntent);
        }else {
            startActivity(detailIntent);
        }


    }

    @Override
    public void onSaveClicked() {
        finish();

    }

}