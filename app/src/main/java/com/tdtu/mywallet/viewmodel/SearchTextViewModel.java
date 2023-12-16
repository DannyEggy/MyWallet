package com.tdtu.mywallet.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchTextViewModel extends ViewModel {
    private MutableLiveData<String> titleQuery= new MutableLiveData<String>();

    public MutableLiveData<String> getTitleQuery() {
        return titleQuery;
    }

    public void setTitleQuery(String query) {
        titleQuery.setValue(query);
    }
}
