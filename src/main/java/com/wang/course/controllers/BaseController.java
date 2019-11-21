package com.wang.course.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A base controller - put all the reuse methods for a controller here
 */
public class BaseController {

    /**
     * Convert a exception object to string
     *
     * @param ex
     * @return
     */
    protected String getStackTrace(Exception ex) {
        Throwable root = getRootException(ex);
        StringWriter sw = new StringWriter();
        root.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        int max = Math.min(exceptionAsString.length(), 200);
        return exceptionAsString.substring(0, max);
    }

    /**
     * Get the root exception
     *
     * @param exception
     * @return
     */
    private Throwable getRootException(Throwable exception) {
        Throwable rootException = exception;
        while (rootException.getCause() != null) {
            rootException = rootException.getCause();
        }
        return rootException;
    }
}
