package com.shaozj.springboot.async;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class ApplicationStart {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	}
	
	/**
	 * 通过ThreadPoolTaskExecutor创建一个线程池
	 * @author shaozj
	 *
	 */
	@EnableAsync
	@Configuration
	class TaskPoolConfig{
		
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
	}
}
