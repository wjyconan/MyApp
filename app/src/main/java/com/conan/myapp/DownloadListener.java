package com.conan.myapp;

/**
 * Author        conan
 * PublishDate   2017-03-23
 * Description   下载服务回调接口
 * Version       1.0
 * Updated       conan
 */
public interface DownloadListener {

    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();
}
