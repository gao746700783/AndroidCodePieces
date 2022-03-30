import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public class WeakRefHandlerEx<T extends WeakRefHandlerEx.OnHandleMessageCb> extends Handler {
    private final WeakReference<T> mWeakReference;

    public WeakRefHandlerEx(T t) {
        mWeakReference = new WeakReference<T>(t);
    }

    public WeakRefHandlerEx(T t, Looper looper) {
        super(looper);
        mWeakReference = new WeakReference<T>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        if (mWeakReference != null && mWeakReference.get() != null) {
            T t = mWeakReference.get();
            t.onHandleMessage(msg);
        }
    }

    public interface OnHandleMessageCb {
        void onHandleMessage(Message msg);
    }

}
