package com.nihal.visitormanagement;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.Task;


public class AddEditActivityFragment extends Fragment {
    private static final String TAG = "AddEditActivityFragment";

    public enum FragmentEditMode{ EDIT, ADD}

    private FragmentEditMode mMode;

    private EditText mNameTextView;
    private EditText mPhoneTextView;
    private EditText mAddressTextView;
    private EditText mCityTextView;
    private EditText mSortOrderTextView;
    private OnSaveClicked mSaveListener = null;

    interface OnSaveClicked{
        void onSaveClicked();
    }

    public AddEditActivityFragment(){
        Log.d(TAG, "AddEditActivityFragment: constructor called");

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: constructor called");

        View view = inflater.inflate(R.layout.fragment_add_edit,container,false);

        mNameTextView = view.findViewById(R.id.addedit_name);
        mPhoneTextView= view.findViewById(R.id.addeditPhone);
        mAddressTextView= view.findViewById(R.id.addeditAddress);
        mCityTextView= view.findViewById(R.id.addeditCity);
        mSortOrderTextView = view.findViewById(R.id.addedit_sortorder);
        Button mSaveButton = view.findViewById(R.id.addedit_save);

        Bundle arguments = getActivity().getIntent().getExtras();

        final Visitor visitor;
        if(arguments != null) {
            Log.d(TAG,"onCreateView: retrieving visitor details");
            visitor = (Visitor) arguments.getSerializable(Task.class.getSimpleName());
            if(visitor!=null) {
                Log.d(TAG,"onCreateView: Visitor details found,editing...");
                mNameTextView.setText(visitor.getName());
                mPhoneTextView.setText(visitor.getPhone());
                mAddressTextView.setText(visitor.getAddress());
                mCityTextView.setText(visitor.getCity());
                mSortOrderTextView.setText(Integer.toString(visitor.getSortOrder()));


                mMode = FragmentEditMode.EDIT;

            }else {
                //No visitor, so we must be adding a new visitor, and not editing an existing one
                mMode = FragmentEditMode.ADD;
            }
        }else {
            visitor =null;
            Log.d(TAG,"onCreateView:No arguments, adding new record");
            mMode = FragmentEditMode.ADD;
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ///update the database if at least one field has changed.
                //  - There's no need to hit the database unless this has happened.
                int so; // to save repeated conversions to int.

                if(mSortOrderTextView.length()>0){
                    so = Integer.parseInt(mSortOrderTextView.getText().toString());
                }else{
                    so  = 0;
                }

                ContentResolver contentResolver = getActivity().getContentResolver();
                ContentValues values = new ContentValues();

                switch (mMode) {
                    case EDIT:
                        if (visitor == null) {
                            //remove lint warnings, will never execute
                            break;
                        }
                        if (!mNameTextView.getText().toString().equals(visitor.getName())){
                            values.put(VisitorContract.Columns.VISITOR_NAME,mNameTextView.getText().toString());
                        }
                        if (mPhoneTextView.getText().toString().equals(visitor.getPhone())){
                            values.put(VisitorContract.Columns.VISITOR_PHONE,mPhoneTextView.getText().toString());
                        }
                        if(!mAddressTextView.getText().toString().equals(visitor.getAddress())){
                            values.put(VisitorContract.Columns.VISITOR_ADDRESS,mAddressTextView.getText().toString());
                        }
                        if(!mCityTextView.getText().toString().equals(visitor.getCity())){
                            values.put(VisitorContract.Columns.VISITOR_ADDRESS,mCityTextView.getText().toString());
                        }
                        if (so != visitor.getSortOrder()){
                            values.put(VisitorContract.Columns.VISITOR_SORTORDER,so);
                        }
                        if(values.size() !=0){
                            Log.d(TAG,"onClick:updating task");
                            contentResolver.update(VisitorContract.buildVisitorUri(visitor.getId()),values,null,null);

                        }
                        break;
                    case ADD:
                        if(mNameTextView.length()>0){
                            Log.d(TAG,"onClick: adding new visitor");
                            values.put(VisitorContract.Columns.VISITOR_NAME,mNameTextView.getText().toString());
                            values.put(VisitorContract.Columns.VISITOR_PHONE,mPhoneTextView.getText().toString());
                            values.put(VisitorContract.Columns.VISITOR_ADDRESS,mAddressTextView.getText().toString());
                            values.put(VisitorContract.Columns.VISITOR_CITY,mCityTextView.getText().toString());
                            values.put(VisitorContract.Columns.VISITOR_SORTORDER,mSortOrderTextView.getText().toString());
                            values.put(VisitorContract.Columns.VISITOR_STATUS,"1");

                            contentResolver.insert(VisitorContract.CONTENT_URI,values);
                        }
                        break;
                }
                Log.d(TAG,"onClick : Done editing");

                if(mSaveListener != null){
                    mSaveListener.onSaveClicked();
                }

            }
        });
        Log.d(TAG, "onCreateView: Exiting...");

    return view;


    }
}