package org.mddg.controller;

import org.mddg.domain.request.SharedQueryRequest;

public abstract class Controller {

    public void p(String value) {
        System.out.println(value);
    }

    public SharedQueryRequest parseQueryParams(String[] args) {
        SharedQueryRequest params = new SharedQueryRequest();
        for (String arg : args) {
            if (params.isLimitParam(arg)) {
               params.setLimitParam(arg);
            } else if (params.isOrderParam(arg)) {
                params.setOrderParam(arg);
            }
        }
        return params;
    }
}
