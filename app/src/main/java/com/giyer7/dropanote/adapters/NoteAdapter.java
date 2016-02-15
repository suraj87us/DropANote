package com.giyer7.dropanote.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.giyer7.dropanote.OnItemClickListener;
import com.giyer7.dropanote.R;
import com.giyer7.dropanote.ShowDialogListener;
import com.giyer7.dropanote.SwipeListener;
import com.giyer7.dropanote.beans.Note;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by giyer7 on 2/14/16.
 */
public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {
    private LayoutInflater layoutInflater;
    private RealmResults<Note> mItems;
    public static final int ITEM = 0;
    public static final int FOOTER = 1;
    private ShowDialogListener mListener;
    private OnItemClickListener mItemClickListener;
    private Realm mRealm;

    public NoteAdapter(Context context, RealmResults<Note> items) {
        layoutInflater = LayoutInflater.from(context);
        updateData(items);
    }

    public NoteAdapter(Context context, Realm realm, RealmResults<Note> items, ShowDialogListener listener, OnItemClickListener onItemClickListener) {
        layoutInflater = LayoutInflater.from(context);
        mRealm = realm;
        updateData(items);
        mListener = listener;
        mItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = layoutInflater.inflate(R.layout.row_note, parent, false);
            return new Holder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.footer, parent, false);
            return new FooterHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Holder) {
            Holder noteHolder = (Holder) holder;
            Note note = mItems.get(position);
            noteHolder.tv_note.setText(note.getNote());
            noteHolder.tv_date.setText(String.valueOf(note.getDueTime()));
        }
    }

    @Override
    public int getItemCount() {
        if (mItems == null || mItems.isEmpty()) {
            return 0;
        } else {
            return mItems.size() + 1; // For the footer
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mItems == null || position < mItems.size()) {
            return ITEM;
        } else {
            return FOOTER;
        }
    }

    public void updateData(RealmResults<Note> realmResults) {
        mItems = realmResults;
        notifyDataSetChanged();
    }

    @Override
    public void swipe(int position, RecyclerView rv) {
        if (position < mItems.size()) {
            mRealm.beginTransaction();
            mItems.get(position).removeFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(position);
            Snackbar.make(rv, "Note Deleted!", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void markComplete(int position) {
        if (position < mItems.size()) {
            mRealm.beginTransaction();
            mItems.get(position).setComplete(true);
            mRealm.commitTransaction();
            notifyItemChanged(position);
        }
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_note, tv_date;

        public Holder(View itemView) {
            super(itemView);
            tv_note = (TextView) itemView.findViewById(R.id.row_textview_note);
            tv_date = (TextView) itemView.findViewById(R.id.row_textview_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClicked(getAdapterPosition());
        }
    }

        public class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btnFooter;

        public FooterHolder(View itemView) {
            super(itemView);
            btnFooter = (Button) itemView.findViewById(R.id.btn_footer);
            btnFooter.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.add();
        }
    }
}
