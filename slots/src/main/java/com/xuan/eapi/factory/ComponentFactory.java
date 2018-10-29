package com.xuan.eapi.factory;

import android.content.Context;
import android.view.ViewGroup;

import com.xuan.annotation.ViewInfo;
import com.xuan.eapi.viewmodel.IPresenterBind;
import com.xuan.eapi.helper.SlotsMap;
import com.xuan.eapi.component.Component;
import com.xuan.eapi.component.IComponentBind;
import com.xuan.eapi.context.SlotContext;


/**
 * Author : xuan.
 * Date : 2018/5/14.
 * Description :组件工厂
 */

public class ComponentFactory implements IComponentFactory {
    private SlotContext tookContext;
    private Context context;
    private ViewGroup parent;

    public ComponentFactory(SlotContext tookContext) {
        this.tookContext = tookContext;
        this.context = tookContext.getContext();
        this.parent = tookContext.getParentRoot();
    }

    private IViewComponentFactory viewFactory;
    private IViewHolderComponentFactory viewHolderFactory;
    private IDFComponentFactory dfComponentFactory;
    private AdapterComponent adapter;


    @Override
    public Component createViewHolder(Context context, ViewGroup parent, int type) {
        //自定义映射关系,对应ViewHolder类型
        Component component = tookContext.createViewHolder(context, parent, type);
        if (component != null) {
            return component;
        }
        ViewInfo viewInfo;
        if (tookContext.getAttachMap() == null) {
            return defaultViewHolder();
        }
        int componentId = tookContext.getAttachMap().getComponentType(type);
        if (tookContext.isAttaching()) {
            /**
             * 绑定模式-对应与个人的楼层开发
             */
            //如果有绑定的类，则获取绑定类的映射表
            viewInfo = SlotsMap.getInstance().obtainRule()
                    .obtainAttachViewInfo(tookContext.getAttachMap().attachClass(),
                            componentId);
            if (viewInfo == null) {
                //没有绑定的类，则获取全局映射表
                viewInfo = SlotsMap.getInstance().obtainRule().obtainViewInfo(componentId);
            }
        } else {
            /**
             * 全局模式-对应与楼层的全局打通
             */
            //没有绑定的类，则获取全局映射表
            viewInfo = SlotsMap.getInstance().obtainRule().obtainViewInfo(componentId);
        }
        if (viewInfo == null) {
            return defaultViewHolder();
        }
        IComponentBind componentIml = null;
        initFactory(viewInfo);
        if (!viewInfo.isAutoCreate()) {
            //如果不需要自动创建
            //自定义映射关系，实现IComponentBind就行
            componentIml = selfCreateComponent(viewInfo);
        } else {
            int viewType = viewInfo.getViewType();
            switch (viewType) {
                case ViewInfo.TYPE_VIEW:
                    componentIml = viewFactory.createViewComponent(viewInfo);
                    break;
                case ViewInfo.TYPE_HOLDER:
                    componentIml = viewHolderFactory.createViewHolderComponent(viewInfo);
                    break;
                case ViewInfo.TYPE_COMPONENT:
                    componentIml = dfComponentFactory.createViewHolder(viewInfo);
                    break;
            }
        }
        //组件mvp,注入Presenter到View中
        if (IPresenterBind.class.isAssignableFrom(viewInfo.getView())) {
            IPresenterBind presenterBind = (IPresenterBind) componentIml;
            presenterBind.injectPresenter(tookContext.obtainLogic(viewInfo.getPresenter()));
        }
        if (viewInfo.getViewType() == ViewInfo.TYPE_COMPONENT) {
            return (Component) componentIml;
        } else {
            //需要适配器转换为Component
            component = adapter.adapterComponent(componentIml);
            return component;
        }
    }

    private void initFactory(ViewInfo viewInfo) {
        AdapterComponent adapterComponent = null;
        switch (viewInfo.getViewType()) {
            case ViewInfo.TYPE_VIEW:
                adapterComponent = createViewFactory();
                break;
            case ViewInfo.TYPE_HOLDER:
                adapterComponent = createViewHolderFactory();
                break;
            case ViewInfo.TYPE_COMPONENT:
                adapterComponent = createComponentFactory();
                break;
        }
        adapter = adapterComponent;
    }

    /**
     * 继承Component的ViewHolder的创建工厂
     */
    private AdapterComponent createComponentFactory() {
        if (dfComponentFactory == null) {
            dfComponentFactory = new DFComponentFactory(context, parent);
        }
        return (AdapterComponent) dfComponentFactory;
    }

    private IComponentBind selfCreateComponent(ViewInfo viewInfo) {
        return tookContext.createViewComponent(viewInfo);
    }

    private AdapterComponent createViewFactory() {
        if (viewFactory == null) {
            viewFactory = new ViewComponentFactory(context);
        }
        return (AdapterComponent) viewFactory;
    }

    private AdapterComponent createViewHolderFactory() {
        if (viewHolderFactory == null) {
            viewHolderFactory = new ViewHolderComponentFactory(context, parent);
        }
        return (AdapterComponent) viewHolderFactory;
    }

    private Component defaultViewHolder() {
        return null;
    }
}
