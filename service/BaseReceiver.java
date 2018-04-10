//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.accs.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.taobao.accs.d.a;
import com.taobao.accs.utl.ALog;

public class BaseReceiver extends BroadcastReceiver {
    private static final String TAG = "BaseReceiver";
    private IBaseReceiver baseReceiver;

    public BaseReceiver() {
    }

    public void onReceive(Context var1, Intent var2) {
        if(this.baseReceiver == null) {
            try {
                this.baseReceiver = (IBaseReceiver)a.a().a(var1.getApplicationContext()).loadClass("com.taobao.accs.internal.ReceiverImpl").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Exception var6) {
                var6.printStackTrace();

                try {
                    this.baseReceiver = (IBaseReceiver)Class.forName("com.taobao.accs.internal.ReceiverImpl").newInstance();
                } catch (Exception var5) {
                    ALog.e("BaseReceiver", "onReceive1", var5, new Object[0]);
                }

                ALog.e("BaseReceiver", "onReceive", var6, new Object[0]);
            }

            if(this.baseReceiver != null) {
                ALog.d("BaseReceiver", "onReceive", new Object[0]);
                this.baseReceiver.onReceive(var1, var2);
            } else {
                ALog.e("BaseReceiver", "onReceive baseReceiver NULL", new Object[0]);
            }
        }

    }
}
