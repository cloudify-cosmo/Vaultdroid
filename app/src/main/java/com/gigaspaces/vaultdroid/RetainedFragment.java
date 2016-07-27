package com.gigaspaces.vaultdroid;


import android.app.Fragment;
import android.os.Bundle;

import com.gigaspaces.vaultdroid.adapters.ListParentClass;

import java.util.ArrayList;

public class RetainedFragment extends Fragment {

    // data object we want to retain
    private ListParentClass mCurrentBreadcrumbItem;
    private ArrayList<ListParentClass> mListItems;

    // this method is only called once for this fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    public ListParentClass getCurrentBreadcrumbItem() {
        return mCurrentBreadcrumbItem;
    }

    public void setCurrentBreadcrumbItem(ListParentClass mCurrentBreadcrumbItem) {
        this.mCurrentBreadcrumbItem = mCurrentBreadcrumbItem;
    }

    public ArrayList<ListParentClass> getListItems() {
        return mListItems;
    }

    public void setListItems(ArrayList<ListParentClass> mListItems) {
        this.mListItems = mListItems;
    }
}
