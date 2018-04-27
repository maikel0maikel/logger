package com.sinohb.logger.upload;

public interface OnUploadListener {
    void onSuccess();
    void onFailure(String message);
}
