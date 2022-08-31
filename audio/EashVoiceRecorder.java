import java.io.File;
import java.io.IOException;
import java.util.Date;


import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;

import com.lvgou.distribution.driect.entity.EMError;
import com.lvgou.distribution.utils.PathUtil;

public class EaseVoiceRecorder {
    MediaRecorder recorder;

    static final String PREFIX = "voice";
    static final String EXTENSION = ".mp3";
    String uid;

    private boolean isRecording = false;
    private long startTime = -4;
    private String voiceFilePath = null;
    private String voiceFileName = null;
    private File file;
    private Handler handler;
    private Context mContext;

//    public EaseVoiceRecorder(Handler handler) {
//        this.handler = handler;
//    }

    public EaseVoiceRecorder() {

    }

    /**
     * @param appContext
     * @param userId     传入userId 用于标示 名称
     * @return
     */
    public String startRecording(Context appContext, String userId) {
        mContext = appContext;
        file = null;
        startTime = -4;
        try {
            // need to create recorder every time, otherwise, will got exception
            // from setOutputFile when try to reuse
            if (recorder != null) {
                recorder.release();
                recorder = null;
            }
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
//            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
/*//           方案一
           recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setAudioChannels(2); // MONO
            recorder.setAudioSamplingRate(16000); // 8000Hz*/
//           方案二
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setAudioChannels(2);
            recorder.setAudioSamplingRate(16000);
/*//            方案三
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);*/
//            recorder.setAudioEncodingBitRate(64); // seems if change this to
            // 128, still got same file
            // size.
            // one easy way is to use temp file
            // file = File.createTempFile(PREFIX + userId, EXTENSION,
            // User.getVoicePath());
            voiceFileName = getVoiceFileName(userId);
            voiceFilePath = PathUtil.getInstance().getVoicePath() + "/" + voiceFileName;
            file = new File(voiceFilePath);
            recorder.setOutputFile(file.getAbsolutePath());
            recorder.prepare();
            isRecording = true;
            recorder.start();
        } catch (IOException e) {

        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isRecording) {
//                        android.os.Message msg = new android.os.Message();
//                        msg.what = recorder.getMaxAmplitude() * 13 / 0x7FFF;
//                        handler.sendMessage(msg);
                        SystemClock.sleep(100);
                    }
                } catch (Exception e) {
                    // from the crash report website, found one NPE crash from
                    // one android 4.0.4 htc phone
                    // maybe handler is null for some reason
                }
            }
        }).start();
        startTime = new Date().getTime();
        return file == null ? null : file.getAbsolutePath();
    }

    /**
     * stop the recoding
     *
     * @return seconds of the voice recorded
     */

    public void discardRecording() {
        if (recorder != null) {
            try {
                recorder.stop();
                recorder.release();
                recorder = null;
                if (file != null && file.exists() && !file.isDirectory()) {
                    file.delete();
                }
            } catch (IllegalStateException e) {
            } catch (RuntimeException e) {
            }
            isRecording = false;
        }
    }

    public int getRatio() {
        if (recorder != null) {
            int ratio = recorder.getMaxAmplitude() / 600;
            return ratio;
        }
        return -1;
    }

    public int stopRecoding() {
        if (recorder != null) {
            isRecording = false;
            if (startTime > -1) {
                try {
                    recorder.stop();
                    recorder.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recorder = null;
            }
            int seconds = (int) (new Date().getTime() - startTime) / 1000;
            if (seconds > 0) {
                if (file == null || !file.exists() || !file.isFile()) {
                    return EMError.FILE_INVALID;
                }
                if (file.length() == 0) {
                    file.delete();
                    return EMError.FILE_INVALID;
                }
            }
            return seconds;
        }
        return 0;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (recorder != null) {
            recorder.release();
        }
    }

    private String getVoiceFileName(String uid) {
        Time now = new Time();
        now.setToNow();
        this.uid = uid;
        return uid + now.toString().substring(0, 15) + EXTENSION;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public String getVoiceFilePath() {
        return voiceFilePath;
    }

    public String getVoiceTargetFilePath() {
        Time now = new Time();
        now.setToNow();
        return PathUtil.getInstance().getVoicePath() + "/" + uid + now.toString().substring(0, 15) + ".mp3";
    }

    public String getVoiceFileName() {
        return voiceFileName;
    }
}

