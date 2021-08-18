package com.nihal.visitormanagement.Fragments;
/*
* A placeholder fragments containing a simple view.
 */

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

import com.nihal.visitormanagement.CursorRecyclerViewAdapter;
import com.nihal.visitormanagement.R;
import com.nihal.visitormanagement.VisitorContract;

import java.security.InvalidParameterException;

public class CheckInFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
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

        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this);
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
                VisitorContract.Columns.VISITOR_CITY, VisitorContract.Columns.VISITOR_SORTORDER};
        // <order by> Task.SortOrder, Task.Name COLLATE NOCASE
        String sortOrder = VisitorContract.Columns.VISITOR_SORTORDER + "," + VisitorContract.Columns.VISITOR_NAME;
        switch (id) {
            case LOADER_ID:
                return new CursorLoader(getActivity(),
                        VisitorContract.CONTENT_URI,
                        projection,
                        null,
                        null,
                        sortOrder);
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