package com.lixiaoxue.demolxx.keep;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.nucarf.base.utils.LogUtils;


/**
 * author : lixiaoxue
 * date   : 2021/3/18/5:51 PM
 */
public class MyJobService extends JobService {
    public String TAG  = "MyJobService";

        private Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                LogUtils.e(TAG,"handleMessage");
                Toast.makeText(MyJobService.this, "MyJobService", Toast.LENGTH_SHORT).show();
                JobParameters param = (JobParameters) msg.obj;
                jobFinished(param, true);

                return true;
            }
        });

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            return START_STICKY;
        }

        @Override
        public boolean onStartJob(JobParameters params) {
            Message m = Message.obtain();
            m.obj = params;
            handler.sendMessageDelayed(m, 2000);
            return true;
        }

        @Override
        public boolean onStopJob(JobParameters params) {
            handler.removeCallbacksAndMessages(null);
            return false;
        }


}
