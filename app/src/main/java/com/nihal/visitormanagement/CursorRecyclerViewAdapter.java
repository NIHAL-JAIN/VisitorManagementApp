package com.nihal.visitormanagement;

import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;


public class CursorRecyclerViewAdapter extends RecyclerView.Adapter<CursorRecyclerViewAdapter.VisitorViewHolder> {
    private static final String TAG = "CursorRecyclerViewAdapt";
    private Cursor mCursor;
    private OnVisitorClickListener mListener;

    public interface OnVisitorClickListener {

        void onEditClick(@NonNull Visitor visitor);
        void onDeleteClick(@NonNull Visitor visitor);


    }

    public CursorRecyclerViewAdapter(Cursor cursor, OnVisitorClickListener listener) {
        Log.d(TAG, "CursorRecyclerViewAdapter: Constructor called");
        mCursor = cursor;
        mListener = listener;
    }

    @NonNull
    @Override
    public VisitorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visitor_list_items, parent, false);
        return new VisitorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: starts");

        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            Log.d(TAG, "onBindViewHolder : providing instructions");
            holder.name.setText(R.string.instructions_heading);
            holder.phone.setText(R.string.instructions);
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
        } else {
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to position" + position);
            }

            final Visitor visitor = new Visitor(
                    mCursor.getLong(mCursor.getColumnIndex(VisitorContract.Columns._ID)),
                    mCursor.getString(mCursor.getColumnIndex(VisitorContract.Columns.VISITOR_NAME)),
                    mCursor.getString(mCursor.getColumnIndex(VisitorContract.Columns.VISITOR_PHONE)),
                    mCursor.getString(mCursor.getColumnIndex(VisitorContract.Columns.VISITOR_ADDRESS)),
                    mCursor.getString(mCursor.getColumnIndex(VisitorContract.Columns.VISITOR_CITY)),
                    mCursor.getInt(mCursor.getColumnIndex(VisitorContract.Columns.VISITOR_SORTORDER))
//                mCursor.getInt(mCursor.getColumnIndex(VisitorContract.Columns.VISITOR_STATUS))
           );


            holder.name.setText(visitor.getName());
            holder.phone.setText(visitor.getPhone());
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);

            View.OnClickListener buttonListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: starts");
                    switch (view.getId()) {
                        case R.id.tli_edit:
                            if (mListener != null) {
                                mListener.onEditClick(visitor);
                            }
                            break;
                        case R.id.tli_delete:
                            if (mListener != null) {
                                mListener.onDeleteClick(visitor);
                            }
                            break;
                        default:
                            Log.d(TAG, "onClick: found unexpected button id");
                    }

                    Log.d(TAG, "onClick: button with id " + view.getId() + "clicked");
                    Log.d(TAG, "onClick: Visitor name" + visitor.getName());
                }
            };

            holder.editButton.setOnClickListener(buttonListener);
            holder.deleteButton.setOnClickListener(buttonListener);
            holder.itemView.setOnClickListener(buttonListener);

        }

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: starts");
        if ((mCursor == null) || (mCursor.getCount() == 0)) {
            return 1; //fib, because we populate a single ViewHolder with instructions
        } else {
            return mCursor.getCount();
        }
    }

    /**
     * Swap in a new Cursor, returning the old Cursor.
     * The returned old Cursor is <em>not</em> closed.
     *
     * @param newCursor The new cursor to be used
     * @return Returns the previously set Cursor, or null if there wasn't one.
     * If the given new Cursor is the same instance as the previously set
     * Cursor, null is also returned.
     */

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if (newCursor != null) {
            //notify the observers about the new cursor
            notifyDataSetChanged();
        } else {
            //notify the observers about the lack of data set
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;

    }

    static class VisitorViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "VisitorViewHolder";

        TextView name;
        TextView phone;
        ImageButton editButton;
        ImageButton deleteButton;
        View itemView;


        public VisitorViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "TaskViewHolder: starts");
            this.name = itemView.findViewById(R.id.tli_name);
            this.phone = itemView.findViewById(R.id.tli_phone);
            this.editButton =  itemView.findViewById(R.id.tli_edit);
            this.deleteButton = itemView.findViewById(R.id.tli_delete);
            this.itemView = itemView;



        }
    }

}
