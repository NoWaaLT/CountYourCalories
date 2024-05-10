package edu.vvk_pit_21_i_nt.countyourcalories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MenuViewModel extends ViewModel {
    protected UserDb userDb;
    protected HashMap<String, UserHistory> userHistoryHashMap;

    public UserDb getUserDb() {
        Log.d("get userDb", "called");
        return userDb;
    }

    public void setUserDb(UserDb userDb) {
        this.userDb = userDb;
    }

    public void setUserHistoryHashMap(HashMap<String, UserHistory> userHistoryHashMap) {
        this.userHistoryHashMap = userHistoryHashMap;
    }

    public HashMap<String, UserHistory> getUserHistoryHashMap() {
        Log.d("UserHistoryHashmap", "called");
        return userHistoryHashMap;
    }

}