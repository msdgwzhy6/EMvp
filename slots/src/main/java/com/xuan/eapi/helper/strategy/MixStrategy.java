package com.xuan.eapi.helper.strategy;

/**
 * Author : xuan.
 * Date : 2018/11/10.
 * Description :默认的混合策略
 */
public abstract class MixStrategy<T> implements IMixStrategy<T> {
    @Override
    public Object getBindItem(int pos, T o) {
        return o;
    }

    @Override
    public int getComponentId(int type) {
        return type;
    }

    @Override
    public Class<?> attachClass(int type) {
        return null;
    }
}
