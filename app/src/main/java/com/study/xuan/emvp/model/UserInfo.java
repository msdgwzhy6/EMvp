package com.study.xuan.emvp.model;

import com.study.xuan.emvp.ComponentId;
import com.study.xuan.emvp.PresenterId;
import com.xuan.annotation.BindType;
import com.xuan.eapi.BaseLogic;
import com.xuan.eapi.vm.ICarryLogic;

/**
 * Author : xuan.
 * Date : 2018/5/17.
 * Description :the description of this file
 */
@BindType(ComponentId.USER_INFO_LAYOUT)
public class UserInfo implements ICarryLogic,IUserInfo{
    public boolean isIntercept = false;
    public BaseLogic presenter;
    public int imgUrl;
    public String name = "用户名字";

    public UserInfo() {
        this(false);
    }

    public UserInfo(boolean isIntercept) {
        this.isIntercept = isIntercept;
    }

    @Override
    public BaseLogic postPresenter() {
        return presenter;
    }

    @Override
    public void injectPresenter(BaseLogic presenter) {
        this.presenter = presenter;
    }

    @Override
    public int presenterId() {
        return PresenterId.COMMUNITY_PRESENTER;
    }

    @Override
    public int getImgUrl() {
        return imgUrl;
    }

    @Override
    public String getText() {
        return name;
    }
}
