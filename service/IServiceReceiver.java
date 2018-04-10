//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.accs;

public interface IServiceReceiver {
    void onData(String var1, String var2, String var3, byte[] var4);

    void onBind(String var1, int var2);

    void onUnbind(String var1, int var2);

    void onSendData(String var1, String var2, int var3);

    void onResponse(String var1, String var2, int var3, byte[] var4);
}
