package com.xuan.eapi.factory;

import com.xuan.annotation.ViewInfo;
import com.xuan.eapi.IComponentBind;
import com.xuan.eapi.component.Component;

/**
 * Author : xuan.
 * Date : 2018/5/14.
 * Description :the description of this file
 */

public interface IViewComponentFactory extends ReflectCreate<IComponentBind>{
    Component createViewComponent(ViewInfo type);

    IComponentBind reflectCreate(Class<?> clazz);
}