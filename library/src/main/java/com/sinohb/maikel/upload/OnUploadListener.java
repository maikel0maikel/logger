package com.sinohb.maikel.upload;

public interface OnUploadListener {
    void onSuccess();
    void onFailure(String message);
}
