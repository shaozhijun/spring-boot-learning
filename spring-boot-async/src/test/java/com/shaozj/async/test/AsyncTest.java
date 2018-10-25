package com.shaozj.async.test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.shaozj.async.ApplicationStart;
import com.shaozj.async.service.AsyncService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ApplicationStart.class)
public class AsyncTest {
	
	@Autowired
	private AsyncService asyncService;
	
	@Test
	public void test() throws InterruptedException, ExecutionException {
		asyncService.asyncTest("=============");
		asyncService.asyncWithException("++++++++++++");
		Future<String> future = asyncService.asyncReturnFuture(1);
		System.out.println(future.get());
	}
}
