package com.giyer7.dropanote.extras;

import android.view.View;

import java.util.List;

/**
 * Created by giyer7 on 2/14/16.
 */
public class Util {
    public static void showViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }
}
