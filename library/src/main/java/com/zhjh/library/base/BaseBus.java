package com.zhjh.library.base;

import com.squareup.otto.Bus;

/**
 * Created by zhang on 2016/3/2.
 */
public class BaseBus {
    private static Bus bus;

    public static Bus getInstance() {
        if (bus == null) {
            bus = new Bus();
        }
        return bus;
    }
}
