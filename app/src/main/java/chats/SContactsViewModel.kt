package chats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import data.SContactModel
import androidx.lifecycle.MutableLiveData

class SContactsViewModel(application: Application) : AndroidViewModel(application) {
    val contactList by lazy {
        MutableLiveData<MutableList<SContactModel>>()
    }
}