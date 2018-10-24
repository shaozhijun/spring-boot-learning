package com.shaozj.async.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;
import com.shaozj.async.exception.AsyncException;

@EnableAsync
@Configuration
public class TaskPoolConfig implements AsyncConfigurer {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10); //核心线程数
		executor.setMaxPoolSize(20);	//最大线程数	
		executor.setQueueCapacity(200);	//缓冲队列
		executor.setKeepAliveSeconds(60); //允许空闲时间
		executor.setThreadNamePrefix("taskExcutor-"); //线程池名的前缀
		//线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，当线程没有处理能力的时候
		//该策略会直接在execute方法的调用线程中运行被拒绝任务，如果程序已关闭，则会丢弃该任务
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}

	
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncExceptionHandler();
	}
	
	/**
	 * 异步异常处理
	 * @author Administrator
	 *
	 */
	class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {  
        @Override  
        public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {  
        	logger.info("Async method: {} has uncaught exception,params:{}", method.getName(), JSON.toJSONString(obj));
        	if (throwable instanceof AsyncException) {
        		AsyncException asyncException = (AsyncException) throwable;
        		logger.info("asyncException:{}", asyncException.getMessage());
        	}
        	logger.info("Exception :");
        	throwable.printStackTrace();
        }  
    } 
}
