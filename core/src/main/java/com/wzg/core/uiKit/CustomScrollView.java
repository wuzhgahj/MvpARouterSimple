package com.wzg.core.uiKit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/4/24
 * 修改时间:  2018/4/24
 * 修改备注:  说明本次修改内容
 */
public class CustomScrollView extends NestedScrollView {
    public CustomScrollView(@NonNull Context context) {
        super(context);
    }

    public CustomScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
