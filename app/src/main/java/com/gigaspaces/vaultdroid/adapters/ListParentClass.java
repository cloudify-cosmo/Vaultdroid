package com.gigaspaces.vaultdroid.adapters;

import java.util.ArrayList;


public class ListParentClass {

    private ListParentClass mParent;
    private ArrayList<ListParentClass> mChildren;
    private String mTitle;
    private boolean isFolder;


    public ListParentClass(String title) {
        mTitle = title;
        isFolder = false;
    }

    public ListParentClass getParent() {
        return mParent;
    }

    public void setParent(ListParentClass mParent) {
        this.mParent = mParent;
    }

    public ArrayList<ListParentClass> getChildren() {
        return mChildren;
    }

    public void setChildren(ArrayList<ListParentClass> mChildren) {
        this.mChildren = mChildren;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String constructSecretPath() {
        if (mParent != null) {
            return mParent.constructSecretPath() + "/" + mTitle;
        } else {
            return mTitle;
        }
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setIsFolder(boolean folder) {
        isFolder = folder;
    }

    public int getTreeItemsAmount() {
        int treeItems = 1;

        if (mChildren != null) {
            for (int i = 0; i < mChildren.size(); i++) {
                if (mChildren.get(i) != null) {
                    treeItems += mChildren.get(i).getTreeItemsAmount();
                }
            }
        }

        return treeItems;
    }
}