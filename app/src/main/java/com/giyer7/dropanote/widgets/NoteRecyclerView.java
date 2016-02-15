package com.giyer7.dropanote.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.giyer7.dropanote.extras.Util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by giyer7 on 2/14/16.
 */
public class NoteRecyclerView extends RecyclerView {

    private List<View> mViewsToHide = Collections.EMPTY_LIST;
    private List<View> mEmptyViewsToShow = Collections.EMPTY_LIST;

    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();
        }
    };

    private void toggleViews() {
        if(getAdapter()!=null && !mEmptyViewsToShow.isEmpty() && !mViewsToHide.isEmpty()) {
            if(getAdapter().getItemCount() == 0) {
                // Show the Empty Set View
                Util.showViews(mEmptyViewsToShow);
                // Hide the Recycler View
                setVisibility(GONE);

                // Hide the Non Empty Views
                Util.hideViews(mViewsToHide);
            } else {
                // Hide the Empty Set View
                Util.hideViews(mEmptyViewsToShow);

                // Show the Recycler View
                setVisibility(VISIBLE);

                // Show the Recycler View with items
                Util.showViews(mViewsToHide);
            }
        }
    }

    public NoteRecyclerView(Context context) {
        super(context);
    }

    public NoteRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoteRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter !=null) {
            adapter.registerAdapterDataObserver(mObserver);
        }
        mObserver.onChanged();
    }

    public void hideIfEmpty(View ...viewsToHide) {
        mViewsToHide = Arrays.asList(viewsToHide);
    }

    public void showIfEmpty(View ...viewsToShow) {
        mEmptyViewsToShow = Arrays.asList(viewsToShow);
    }
}
