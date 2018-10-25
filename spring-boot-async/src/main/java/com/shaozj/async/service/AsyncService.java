package com.shaozj.async.service;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.shaozj.async.exception.AsyncException;

@Component
public class AsyncService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 异步调用：无返回信息，无异常
	 * @param parem
	 */
	@Async
	public void asyncTest(String parem) {
		logger.info("asyncTest, parem={}", parem);
	}
	
	/**
	 * 异步调用：无返回信息，有异常
	 * @param parem
	 */
	@Async
	public void asyncWithException(String parem) {
		
		logger.info("asyncWithException, parem={}", parem);
        throw new AsyncException(parem);
	}
	
	
	/**
	 * 异步调用：有数据返回
	 * @return
	 */
	@Async
	public Future<String> asyncReturnFuture(int i) {
		logger.info("invokeReturnFuture parem = {}", i);
		try {
            Thread.sleep(3000);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("thread", Thread.currentThread().getName());
            jsonObject.put("time", System.currentTimeMillis());
            return new AsyncResult<String>(jsonObject.toJSONString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
	}
}
