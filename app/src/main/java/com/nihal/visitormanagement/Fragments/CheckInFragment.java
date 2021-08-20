package com.nihal.visitormanagement.Fragments;
/*
* A placeholder fragments containing a simple view.
 */

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.Task;
import com.nihal.visitormanagement.CursorRecyclerViewAdapter;
import com.nihal.visitormanagement.R;
import com.nihal.visitormanagement.Visitor;
import com.nihal.visitormanagement.VisitorContract;

import java.security.InvalidParameterException;

public class CheckInFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        CursorRecyclerViewAdapter.OnVisitorClickListener{
    private static final String TAG = "CheckInFragment";

    public static final int LOADER_ID = 0;

    private CursorRecyclerViewAdapter mAdapter; // add adapter reference

    public CheckInFragment() {
        // Required empty public constructor
        Log.d(TAG, "CheckInFragment: start");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: starts");
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();
        if (!(activity instanceof CursorRecyclerViewAdapter.OnVisitorClickListener)) {
            assert activity != null;
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + " must implement CursorRecyclerViewAdapter.OnTaskClickListener interface");
        }
        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onEditClick(@NonNull Visitor visitor) {
        Log.d(TAG, "onEditClick: called");
        CursorRecyclerViewAdapter.OnVisitorClickListener listener = (CursorRecyclerViewAdapter.OnVisitorClickListener) getActivity();
        if (listener != null) {
            listener.onEditClick(visitor);
        }
    }

    @Override
    public void onDeleteClick(@NonNull Visitor visitor) {
        Log.d(TAG, "onDeleteClick: called");
        CursorRecyclerViewAdapter.OnVisitorClickListener listener = (CursorRecyclerViewAdapter.OnVisitorClickListener) getActivity();
        if (listener != null) {
            listener.onDeleteClick(visitor);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: starts ");
        View view = inflater.inflate(R.layout.fragment_check_in,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.visitor_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new CursorRecyclerViewAdapter(null, (CursorRecyclerViewAdapter.OnVisitorClickListener) getActivity());
        recyclerView.setAdapter(mAdapter);

        Log.d(TAG, "onCreateView: returning");
        return view;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader: start with id " + id);
        String[] projection = {VisitorContract.Columns._ID, VisitorContract.Columns.VISITOR_NAME,
                VisitorContract.Columns.VISITOR_PHONE, VisitorContract.Columns.VISITOR_ADDRESS,
                VisitorContract.Columns.VISITOR_CITY, VisitorContract.Columns.VISITOR_SORTORDER,VisitorContract.Columns.VISITOR_STATUS};
        // <order by> Task.SortOrder, Task.Name COLLATE NOCASE
        String sortOrder = VisitorContract.Columns.VISITOR_SORTORDER + "," + VisitorContract.Columns.VISITOR_NAME;
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getActivity(),
                        VisitorContract.CONTENT_URI,
                        projection,
                        null  ,
                        null,
                        sortOrder
                        );
            default:
                throw new InvalidParameterException(TAG + ".onCreateLoader called with invalid loader id" + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "Entering onLoadFinished");
        mAdapter.swapCursor(data);
        int count = mAdapter.getItemCount();

        Log.d(TAG, "onLoadFinished: count is" + count);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: starts");
        mAdapter.swapCursor(null);

    }


}