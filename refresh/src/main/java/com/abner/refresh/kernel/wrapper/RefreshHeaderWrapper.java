package com.abner.refresh.kernel.wrapper;

import android.annotation.SuppressLint;
import android.view.View;

import com.abner.refresh.kernel.api.RefreshHeader;
import com.abner.refresh.kernel.simple.SimpleComponent;

/**
 * 刷新头部包装
 * Created by scwang on 2017/5/26.
 */
@SuppressLint("ViewConstructor")
public class RefreshHeaderWrapper extends SimpleComponent implements RefreshHeader {

    public RefreshHeaderWrapper(View wrapper) {
        super(wrapper);
    }

}
