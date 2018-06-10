package com.study.xuan.emvp.presenter;

import android.content.Context;
import android.hardware.usb.UsbRequest;
import android.widget.Toast;

import com.study.xuan.emvp.PresenterId;
import com.study.xuan.emvp.model.Product;
import com.study.xuan.emvp.model.UserInfo;
import com.xuan.annotation.RegisterLogic;
import com.xuan.eapi.BasePresenter;

/**
 * Author : xuan.
 * Date : 2018/5/24.
 * Description :the description of this file
 */
public class OtherPresenter extends BasePresenter implements IUserInfoPresenter<UserInfo> {
    public OtherPresenter(Context context) {
        super(context);
    }

    @Override
    public void onTextClick(UserInfo userInfo) {
        Toast.makeText(mContext, "个人信息逻辑，进入个人主页", Toast.LENGTH_SHORT).show();
    }
}
