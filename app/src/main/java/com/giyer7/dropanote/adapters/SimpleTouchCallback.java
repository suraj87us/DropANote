package com.giyer7.dropanote.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.giyer7.dropanote.SwipeListener;

/**
 * Created by giyer7 on 2/14/16.
 */
public class SimpleTouchCallback extends ItemTouchHelper.Callback {

    SwipeListener mListener;
    RecyclerView rv;

    public SimpleTouchCallback(SwipeListener listener, RecyclerView recyclerView) {
        mListener = listener;
        rv = recyclerView;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.END);
    }

    /**
     * Method to indicate if Dragging a view is enabled
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * Method to indicate if Swiping a view is enabled
     *
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * Method is called when a view is dragged.
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * Method is called when a view is swiped.
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mListener.swipe(viewHolder.getLayoutPosition(), rv);
    }
}
