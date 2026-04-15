package com.wx.study.aqs.semaphore;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Semaphore;

/**
 * https://www.baeldung.com/java-semaphore
 * <p>
 * 登录队列来限制系统中用户数量
 *
 * @author wxli
 * @date 2021/7/15 22:16
 */
public class LoginQueueUsingSemaphore {
    private Semaphore semaphore;

    public LoginQueueUsingSemaphore(int slotLimit) {
        this.semaphore = new Semaphore(slotLimit);
    }

    boolean tryLogin() {
        return semaphore.tryAcquire();
        // tryAcquire()如果许可证立即可用则返回真，否则返回假，
        // 但acquire()获取许可并阻塞直到一个许可可用
    }

    void logout() {
        semaphore.release();  //释放许可证
    }

    int availableSlots() {
        return semaphore.availablePermits();    //返回当前可用许可证数量
    }


}
