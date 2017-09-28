package com.example.danil.androidbindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class FirstBindService extends Service {
    FirstBinder binder = new FirstBinder();

    int result = 0;

    class FirstBinder extends Binder {
        FirstBindService getService() {
            return FirstBindService.this;
        }
    }

    public void onCreate() {
        super.onCreate();
    }

    int addition(int value) {
        result +=value;
        return result;
    }

    int subtraction(int value) {
        result -=value;
        return result;
    }

    public IBinder onBind(Intent arg0) {
        return binder;
    }
}
