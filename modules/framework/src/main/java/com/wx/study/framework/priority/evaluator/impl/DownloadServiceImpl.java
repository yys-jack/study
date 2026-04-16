package com.wx.study.framework.evaluator.impl;

import com.wx.study.framework.evaluator.AbstractService;
import com.wx.study.framework.task.Task;

public class DownloadServiceImpl extends AbstractService implements Comparable<DownloadServiceImpl> {
    public DownloadServiceImpl(Task task) {
        super(task);
    }

    @Override
    protected void execute() {
        //execute download

    }


    @Override
    public int compareTo(DownloadServiceImpl o) {
        return getPriority() - o.getPriority();
    }
}
