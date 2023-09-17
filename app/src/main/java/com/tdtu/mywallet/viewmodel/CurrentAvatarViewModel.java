package com.tdtu.mywallet.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class CurrentAvatarViewModel extends ViewModel {
    private MutableLiveData<String> currentAvatarLiveData = new MutableLiveData<>();

    public LiveData<String> getCurrentAvatarLiveData() {
        return currentAvatarLiveData;
    }

    public void setCurrentAvatarLiveData(String userAvatar) {
        currentAvatarLiveData.setValue(userAvatar);
    }
}
