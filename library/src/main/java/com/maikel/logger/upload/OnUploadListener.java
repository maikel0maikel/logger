package com.maikel.logger.upload;

public interface OnUploadListener {
    void onSuccess();
    void onFailure(String message);
}
