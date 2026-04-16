package com.wx.study.framework.evaluator;

import java.util.Calendar;
import java.util.Queue;

/**
 * 默认评估接口
 */
public class DefaultEvaluator implements Evaluator {
    private float numerator = 20;//分子
    private float percentCmp = 70f;//复杂度百分比
    private float percentToc = 30f;//超时次数/其他百分比
    private int timeOut = 30_000;//超时阈值
    private int growthFactor = 1; //增长因子
    private boolean open = true;//评估开关
    private boolean reOpen = false;//重估开关

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void estimate(EvaluatorService service) {
        float cr = service.getComplexRate() * percentCmp / 100;
        float toc = service.getTimeOutCount() * percentToc / 100;
        service.setPriority(Math.round((numerator / (cr + toc))));
    }

    @Override
    public void reEstimate(Queue<EvaluatorService> services) {
        long now = Calendar.getInstance().getTimeInMillis();
        for (EvaluatorService item : services) {
            Calendar c = Calendar.getInstance();
            c.setTime(item.getStartTime());
            long start = c.getTimeInMillis();
            if (now - start > timeOut) {
                item.setPriority(item.getPriority() + growthFactor);
            }
        }
    }

    @Override
    public boolean isReOpen() {
        return reOpen;
    }
}
