package com.jtl.optimize;

import androidx.annotation.Keep;

/**
 * 作者:jtl
 * 日期:Created in 2023/7/21 14:47
 * 描述:proguard-rules文件测试
 * 更改:
 */
@Keep
class KeepTest {
    private static final KeepTest ourInstance = new KeepTest();

    static KeepTest getInstance() {
        return ourInstance;
    }

    private KeepTest() {
    }
}
