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
	 * ͨ��ThreadPoolTaskExecutor����һ���̳߳�
	 * @author shaozj
	 *
	 */
	@EnableAsync
	@Configuration
	class TaskPoolConfig{
		
		public Executor taskExecutor() {
			ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
			executor.setCorePoolSize(10); //�����߳���
			executor.setMaxPoolSize(20);	//����߳���	
			executor.setQueueCapacity(200);	//�������
			executor.setKeepAliveSeconds(60); //�������ʱ��
			executor.setThreadNamePrefix("taskExcutor-"); //�̳߳�����ǰ׺
			//�̳߳ضԾܾ�����Ĵ�����ԣ����������CallerRunsPolicy���ԣ����߳�û�д���������ʱ��
			//�ò��Ի�ֱ����execute�����ĵ����߳������б��ܾ�������������ѹرգ���ᶪ��������
			executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
			return executor;
		}
	}
}
