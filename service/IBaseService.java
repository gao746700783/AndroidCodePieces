//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.accs.base;

import android.content.Intent;
import android.os.IBinder;

public interface IBaseService {
    void onCreate();

    int onStartCommand(Intent var1, int var2, int var3);

    IBinder onBind(Intent var1);

    boolean onUnbind(Intent var1);

    void onDestroy();
}
