//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.accs.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.taobao.accs.d.a;
import com.taobao.accs.utl.ALog;

public class BaseService extends Service {
    private static final String TAG = "BaseService";
    IBaseService mBaseService = null;

    public BaseService() {
    }

    public void onCreate() {
        super.onCreate();

        try {
            this.mBaseService = (IBaseService)a.a().a(this.getApplicationContext()).loadClass("com.taobao.accs.internal.ServiceImpl").getDeclaredConstructor(new Class[]{Service.class}).newInstance(new Object[]{this});
        } catch (Throwable var9) {
            var9.printStackTrace();
        } finally {
            if(this.mBaseService == null) {
                try {
                    this.mBaseService = (IBaseService)Class.forName("com.taobao.accs.internal.ServiceImpl").getDeclaredConstructor(new Class[]{Service.class}).newInstance(new Object[]{this});
                } catch (Throwable var8) {
                    ;
                }
            }

        }

        ALog.d("BaseService", "onCreate", new Object[0]);
        if(this.mBaseService != null) {
            this.mBaseService.onCreate();
        } else {
            ALog.e("BaseService", "cann't start ServiceImpl!", new Object[0]);
        }

    }

    public int onStartCommand(Intent var1, int var2, int var3) {
        if(this.mBaseService == null) {
            ALog.e("BaseService", "onStartCommand mBaseService null", new Object[0]);
            return 2;
        } else {
            return this.mBaseService.onStartCommand(var1, var2, var3);
        }
    }

    public IBinder onBind(Intent var1) {
        ALog.d("BaseService", "onBind:" + var1, new Object[0]);
        return this.mBaseService.onBind(var1);
    }

    public boolean onUnbind(Intent var1) {
        return this.mBaseService.onUnbind(var1);
    }

    public void onDestroy() {
        if(this.mBaseService != null) {
            this.mBaseService.onDestroy();
            this.mBaseService = null;
        }

        super.onDestroy();
    }
}
