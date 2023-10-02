package com.tdtu.mywallet.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tdtu.mywallet.model.Activity;

import java.util.ArrayList;
import java.util.List;

public class TransactionListViewModel extends ViewModel {
    private MutableLiveData<List<Activity>> transactionListLiveData = new MutableLiveData<>();
    private List<Activity> transactionList = new ArrayList<Activity>();

    public void addTransaction(Activity activity){
        transactionList.add(activity);
        transactionListLiveData.setValue(transactionList);
    }

    public LiveData<List<Activity>> getTransactionListLiveData(){
        return transactionListLiveData;
    }
}
