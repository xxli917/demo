package com.lixiaoxue.demolxx.websocket;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;

import com.lixiaoxue.demolxx.BuildConfig;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * https://blog.csdn.net/qq_25602107/article/details/104022502?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
 * https://blog.csdn.net/feixiangsmile/article/details/85623545
 * https://blog.csdn.net/zly921112/article/details/76758424?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
 * //长连接
 * //网络切换崇廉
 * IntentFilter intentFilter = new IntentFilter();
 * intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
 * registerReceiver(new NetStatusReceiver(),intentFilter);
 * OkhttpWsManager.getInstance().initWebSocket();
 * //监听前后台切换
 * initAppStatusListener();
 * private void initAppStatusListener() {
 * ForegroundCallbacks.init(this).addListener(new ForegroundCallbacks.Listener() {
 *
 * @Override public void onBecameForeground() {
 * LogUtils.e("WsManager应用回到前台调用重连方法");
 * // OkhttpWsManager.getInstance().reconnect();
 * }
 * @Override public void onBecameBackground() {
 * <p>
 * }
 * });
 * }
 */
public class OkhttpWsManager {
    private final String TAG = this.getClass().getSimpleName();
    private static OkhttpWsManager mInstance;

    /**
     * WebSocket config
     */
    private static final String DEF_TEST_URL = "ws://123.207.136.134:9010/ajaxchattest";//测试服默认地址
    private static final String DEF_RELEASE_URL = "ws://123.207.136.134:9010/ajaxchattest";//正式服默认地址
    private static final String DEF_URL = BuildConfig.DEBUG ? DEF_TEST_URL : DEF_RELEASE_URL;

    private WebSocket mSocket;
    private int reconnectCount = 0;//重连次数
    private long minInterval = 3000;//重连最小时间间隔
    private long maxInterval = 60000;//重连最大时间间隔
    private String url;
    private WsStatus mStatus;
    private long sendTime = 0L;
    //重新连接
    private Handler reHandler = new Handler();
    // 发送心跳包
    private Handler mHandler = new Handler();

    Handler mainHandler = new Handler(Looper.getMainLooper());

    // 每隔2秒发送一次心跳包，检测连接没有断开
    private static final long HEART_BEAT_RATE = 2 * 1000;
    private OkHttpWsListener mlistener;
    private OkHttpClient mOkHttpClient;
    private Request request;


    private OkhttpWsManager() {

    }

    public static OkhttpWsManager getInstance() {
        if (mInstance == null) {
            synchronized (OkhttpWsManager.class) {
                if (mInstance == null) {
                    mInstance = new OkhttpWsManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 释放单例, 及其所引用的资源
     */
    public static void release() {
        try {
            if (mInstance != null) {
                mInstance = null;
            }
        } catch (Exception e) {
            LogUtils.e("OkhttpWsManager", "release : " + e.toString());
        }
    }


    public void init() {
        LogUtils.e(TAG, "第一次连接");
        //    OkHttpClient mOkHttpClient = OkHttpUtils.INSTANCE.getClient();
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        request = new Request.Builder().url(DEF_URL).build();
        connect();

    }

    private void connect() {
        EchoWebSocketListener socketListener = new EchoWebSocketListener();
        // 刚进入界面，就开启心跳检测
        if (mHandler != null) {
            mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);
        }
        mOkHttpClient.newWebSocket(request, socketListener);
        mOkHttpClient.dispatcher().executorService().shutdown();
    }


    // 发送心跳包
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (mSocket != null) {
                if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                    String message = "test";
                    boolean isSend = mSocket.send(message);
                    LogUtils.e(TAG, "心跳包是否发送成功=" + isSend);
                    sendTime = System.currentTimeMillis();
                }
                mHandler.postDelayed(this, HEART_BEAT_RATE); //每隔一定的时间，对长连接进行一次心跳检测
            }
        }
    };


    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            mSocket = webSocket;
            //连接成功后，发送登录信息
            String message = "name";
            mSocket.send(message);
            setStatus(WsStatus.CONNECT_SUCCESS);
            if (mlistener != null) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mlistener.onOpen(webSocket, response);
                    }
                });
            }
            LogUtils.e(TAG, "连接成功");

        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            if (mlistener != null) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mlistener.onMessage(webSocket, bytes);
                    }
                });
            }

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            LogUtils.e(TAG, "服务器端发送来的信息：" + text);
            if (mlistener != null) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mlistener.onMessage(webSocket, text);
                    }
                });
            }
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            LogUtils.e(TAG, "断开连接" + reason);
            setStatus(WsStatus.CONNECT_CLOSE);
            stopHeartBeat();
            if (mlistener != null) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mlistener.onClosed(webSocket, code, reason);
                    }
                });
            }
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            LogUtils.e(TAG, "正在断开连接" + reason);
            setStatus(WsStatus.CONNECT_CLOSE);
            stopHeartBeat();
            if (mlistener != null) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mlistener.onClosing(webSocket, code, reason);
                    }
                });
            }

        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                LogUtils.e(TAG, "主线程");
            } else {
                LogUtils.e(TAG, "非主线程");
            }
            if (mlistener != null) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mlistener.onFailure();
                    }
                });
            }
            LogUtils.e(TAG, "连接失败");
            setStatus(WsStatus.CONNECT_FAIL);
            stopHeartBeat();
            reconnect();

        }
    }


    /**
     * 无限次重连，时间间隔越来越久，最大maxInterval
     */
    public void reconnect() {
        LogUtils.e(TAG, "重连");
        if (!isNetConnect()) {
            reconnectCount = 0;
            LogUtils.e(TAG, "重连失败网络不可用");
            return;
        }
        //没有连接成功才会重新连接，前天切后台如果连接断了，则后台切前台，从新获取连接
        if ((getStatus() == WsStatus.CONNECT_FAIL || getStatus() == WsStatus.CONNECT_CLOSE) &&
                getStatus() != WsStatus.CONNECTING) {//不是正在重连状态
            reconnectCount++;
            setStatus(WsStatus.CONNECTING);
            long reconnectTime = minInterval;
            if (reconnectCount > 3) {
                url = DEF_URL;
                long temp = minInterval * (reconnectCount - 2);
                reconnectTime = temp > maxInterval ? maxInterval : temp;
            }
            LogUtils.e(TAG, "准备开始第" + reconnectCount + "次重连,重连间隔" + reconnectTime + "----+ url:" + url);
            reHandler.postDelayed(mReconnectTask, reconnectTime);
        }
    }


    private Runnable mReconnectTask = new Runnable() {
        @Override
        public void run() {
            connect();
        }
    };

    //取消重连
    private void cancelReconnect() {
        reconnectCount = 0;
        if (reHandler != null) {
            reHandler.removeCallbacks(mReconnectTask);
        }
    }

    //停止心跳
    private void stopHeartBeat() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    //断开连接
    public void disconnect() {
        if (mSocket != null) {
            mSocket.cancel();
            mSocket.close(1001, "客户端关闭");
        }
        stopHeartBeat();
        cancelReconnect();
    }

    private void setStatus(WsStatus status) {
        this.mStatus = status;
    }

    private WsStatus getStatus() {
        return mStatus;
    }

    public void setWsListener(OkHttpWsListener listener) {
        mlistener = listener;
    }

    public interface OkHttpWsListener {
        void onOpen(WebSocket webSocket, Response response);

        void onMessage(WebSocket webSocket, String text);

        void onMessage(WebSocket webSocket, ByteString bytes);

        void onClosing(WebSocket webSocket, int code, String reason);

        void onClosed(WebSocket webSocket, int code, String reason);

        void onFailure();
    }

    private boolean isNetConnect() {
        ConnectivityManager connectivity = (ConnectivityManager) BaseAppCache.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

}



