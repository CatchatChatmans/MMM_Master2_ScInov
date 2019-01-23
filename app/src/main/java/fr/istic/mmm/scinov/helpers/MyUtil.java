package fr.istic.mmm.scinov.helpers;

import android.support.v7.widget.SearchView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MyUtil {
    public static void clickOutsideToUnfocusSearch(View view, SearchView searchView) {

        if(!(view instanceof SearchView)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (searchView.hasFocus()) {
                        searchView.clearFocus();
                        if(searchView.getQuery().length() == 0){
                            searchView.onActionViewCollapsed();
                        }
                    }
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                clickOutsideToUnfocusSearch(innerView, searchView);
            }
        }
    }
}
