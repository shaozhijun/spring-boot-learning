package com.shaozj.redisson.utils;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;

import com.shaozj.redisson.locker.DistributedLocker;

public class RedissLockUtil {

	private static DistributedLocker redissLock;
	
	/**
     * ����
     * @param lockKey
     * @return
     */
    public static RLock lock(String lockKey) {
        return redissLock.lock(lockKey);
    }
    
    /**
     * �ͷ���
     * @param lockKey
     */
    public static void unlock(String lockKey) {
        redissLock.unlock(lockKey);
    }
    
    /**
     * �ͷ���
     * @param lock
     */
    public static void unlock(RLock lock) {
        redissLock.unlock(lock);
    }
    
    /**
     * ����ʱ����
     * @param lockKey
     * @param timeout ��ʱʱ��   ��λ����
     */
    public static RLock lock(String lockKey, int timeout) {
        return redissLock.lock(lockKey, timeout);
    }
    
    /**
     * ����ʱ����
     * @param lockKey
     * @param unit ʱ�䵥λ
     * @param timeout ��ʱʱ��
     */
    public static RLock lock(String lockKey, TimeUnit unit ,int timeout) {
        return redissLock.lock(lockKey, unit, timeout);
    }
    
    /**
     * ���Ի�ȡ��
     * @param lockKey
     * @param waitTime ���ȴ�ʱ��
     * @param leaseTime �������Զ��ͷ���ʱ��
     * @return
     */
    public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        return redissLock.tryLock(lockKey, TimeUnit.SECONDS, waitTime, leaseTime);
    }
    
    /**
     * ���Ի�ȡ��
     * @param lockKey
     * @param unit ʱ�䵥λ
     * @param waitTime ���ȴ�ʱ��
     * @param leaseTime �������Զ��ͷ���ʱ��
     * @return
     */
    public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        return redissLock.tryLock(lockKey, unit, waitTime, leaseTime);
    }
	
	public static void setLocker(DistributedLocker locker) {
        redissLock = locker;
    }
}
