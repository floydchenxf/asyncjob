package floyd.com.aync;


import android.app.Application;

import java.util.Map;

import floyd.com.ApplicationManager;
import floyd.com.request.BaseRequest;
import floyd.com.request.FileItem;
import floyd.com.request.HttpMethod;
import floyd.com.request.RequestCallback;
import floyd.com.utils.NetworkUtil;

/**
 * Created by floyd on 15-11-22.
 */
public class HttpJobFactory {

    public static AsyncJob<byte[]> createHttpJob(final String url, final Map<String, String> params, final HttpMethod httpMethod) {
        return new AsyncJob<byte[]>() {
            @Override
            public void start(final ApiCallback<byte[]> callback) {

                Application context = ApplicationManager.getApplication();
                if (context != null) {
                    boolean isNetworkAvailable = NetworkUtil.isNetworkAvailable(ApplicationManager.getApplication());
                    if (!isNetworkAvailable) {
                        if (callback != null) {
                            callback.onError(APIError.API_NETWORK_ERROR, "无网络，请检查网络设置．");
                        }
                        return;
                    }
                }

                new BaseRequest(url, params, httpMethod, new RequestCallback() {
                    @Override
                    public void onProgress(int progress) {
                        if (callback != null) {
                            callback.onProgress(progress);
                        }
                    }

                    @Override
                    public <T> void onSuccess(T... result) {
                        if (result == null || result.length <= 0) {
                            if (callback != null) {
                                callback.onError(APIError.API_CONTENT_EMPTY, "empty!");
                            }
                            return;
                        }

                        byte[] content = (byte[]) result[0];
                        if (callback != null) {
                            callback.onSuccess(content);
                        }
                    }

                    @Override
                    public void onError(int code, String info) {
                        if (callback != null) {
                            callback.onError(code, info);
                        }
                    }
                }).execute();

            }
        }.threadOn();
    }

    public static AsyncJob<byte[]> createFileJob(final String url, final Map<String, String> params, final Map<String, FileItem> files, final HttpMethod httpMethod) {
        return new AsyncJob<byte[]>() {
            @Override
            public void start(final ApiCallback<byte[]> callback) {

                Application context = ApplicationManager.getApplication();
                if (context != null) {
                    boolean isNetworkAvailable = NetworkUtil.isNetworkAvailable(ApplicationManager.getApplication());
                    if (!isNetworkAvailable) {
                        if (callback != null) {
                            callback.onError(APIError.API_NETWORK_ERROR, "无网络，请检查网络设置．");
                        }
                        return;
                    }
                }

                new BaseRequest(url, params, files, httpMethod, new RequestCallback() {
                    @Override
                    public void onProgress(int progress) {
                        if (callback != null) {
                            callback.onProgress(progress);
                        }
                    }

                    @Override
                    public <T> void onSuccess(T... result) {
                        if (result == null || result.length <= 0) {
                            if (callback != null) {
                                callback.onError(APIError.API_CONTENT_EMPTY, "empty!");
                            }
                            return;
                        }

                        byte[] content = (byte[]) result[0];
                        if (callback != null) {
                            callback.onSuccess(content);
                        }
                    }

                    @Override
                    public void onError(int code, String info) {
                        if (callback != null) {
                            callback.onError(code, info);
                        }
                    }
                }).execute();

            }
        }.threadOn();
    }

}
