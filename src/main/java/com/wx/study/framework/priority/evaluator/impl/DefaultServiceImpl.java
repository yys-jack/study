package com.wx.study.framework.evaluator.impl;

import com.wx.study.framework.evaluator.AbstractService;
import com.wx.study.framework.task.Task;

public class DefaultServiceImpl extends AbstractService {
    public DefaultServiceImpl(Task task) {
        super(task);
    }

    @Override
    protected void execute() {
        //default execute
    }
}
