//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.taobao.accs.d;

import android.content.Context;
import android.content.SharedPreferences;
import com.taobao.accs.utl.ALog;
import dalvik.system.DexClassLoader;
import java.io.File;

public class a {
    private static a a = null;
    private ClassLoader b = null;
    private boolean c = false;
    private Context d;

    public a() {
    }

    public static synchronized a a() {
        if(a == null) {
            a = new a();
        }

        return a;
    }

    public synchronized ClassLoader a(Context var1) {
        if(var1 != null) {
            this.d = var1;
        }

        if(this.b == null) {
            ALog.d("ACCSClassLoader", "create new class loader", new Object[0]);
            SharedPreferences var2 = var1.getSharedPreferences("ACCS_SDK", 0);
            String var3 = var2.getString("update_folder", (String)null);
            ALog.d("ACCSClassLoader", "baseUpdateFolder:" + var3, new Object[0]);
            if(var3 != null) {
                File var4 = var1.getDir(var3, 0);
                if(var4.exists() && var4.isDirectory()) {
                    File var5 = new File(var4, "accs.zip");
                    if(var5.exists() && var5.isFile() && var2.getInt("update_verion", 220) > 220) {
                        if(var2.getBoolean("update_done", false)) {
                            ALog.d("ACCSClassLoader", "dexopt already done", new Object[0]);
                            this.b = new a.a(var5.getAbsolutePath(), var4.getAbsolutePath(), (new File(var4.getParentFile(), "lib")).getAbsolutePath(), a.class.getClassLoader());
                        } else {
                            ALog.d("ACCSClassLoader", "try dexopt", new Object[0]);
                            this.a(var5.getAbsolutePath(), var4.getAbsolutePath());
                        }
                    }
                }
            }
        }

        if(this.b == null) {
            ALog.d("ACCSClassLoader", "get defalut class loader", new Object[0]);
            this.b = a.class.getClassLoader();
        }

        return this.b;
    }

    public synchronized ClassLoader b() {
        if(this.b == null) {
            ALog.d("ACCSClassLoader", "get defalut class loader", new Object[0]);
            this.b = a.class.getClassLoader();
        }

        return this.b;
    }

    private synchronized void a(String var1, String var2) {
        if(this.c) {
            ALog.d("ACCSClassLoader", "dexOpting, exit", new Object[0]);
        } else {
            this.c = true;
            (new b(this, var1, var2)).start();
        }
    }

    private static class a extends DexClassLoader {
        private ClassLoader a;

        public a(String var1, String var2, String var3, ClassLoader var4) {
            super(var1, var2, var3, var4.getParent());
            this.a = var4;
        }

        protected Class<?> findClass(String var1) throws ClassNotFoundException {
            try {
                return super.findClass(var1);
            } catch (Exception var2) {
                return this.a.loadClass(var1);
            }
        }

        public String toString() {
            return "ACCSClassLoader$InnerClassLoader$" + this.hashCode();
        }
    }
}
