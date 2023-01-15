package org.mddg.domain.request;

public abstract class Request {
    protected boolean isParam(String param, String longName, String shortName) {
        if (shortName == null) {
            return param.startsWith(longName);
        }
        return param.startsWith(longName) || param.startsWith(shortName);
    }
}
