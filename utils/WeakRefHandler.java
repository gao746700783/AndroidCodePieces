import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

public class WeakRefHandler extends Handler {
    private final WeakReference<Callback> mWeakReference;

    public WeakRefHandler(Callback callback) {
        super(callback);
        mWeakReference = new WeakReference<Handler.Callback>(callback);
    }

    public WeakRefHandler(Callback callback, Looper looper) {
        super(looper,callback);
        mWeakReference = new WeakReference<Handler.Callback>(callback);
    }

    @Override
    public void handleMessage(Message msg) {
        if (mWeakReference != null && mWeakReference.get() != null) {
            Callback callback = mWeakReference.get();
            callback.handleMessage(msg);
        }
    }
}
