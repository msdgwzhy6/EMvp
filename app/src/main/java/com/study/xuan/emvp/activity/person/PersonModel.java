package com.study.xuan.emvp.activity.person;

import com.study.xuan.emvp.activity.common.CommonModel;
import com.xuan.eapi.helper.binder.HandlerType;

/**
 * Author : xuan.
 * Date : 2018/10/30.
 * Description :the description of this file
 */
public class PersonModel implements HandlerType {
    public String name;
    public int type;
    public CommonModel model;

    @Override
    public int handlerType() {
        return type;
    }
}
