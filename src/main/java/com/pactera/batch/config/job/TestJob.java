package com.pactera.batch.config.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.pactera.batch.config.listener.AgentSkipListener;
import com.pactera.batch.config.listener.ChunkListener;
import com.pactera.batch.config.listener.JobListener;
import com.pactera.batch.model.Agent;

@Configuration
@EnableBatchProcessing
public class TestJob {
	//注入任务对象
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	//任务执行step
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	@Qualifier("fileReader")
	private FlatFileItemReader<Agent> fileReader;
	
	@Autowired
	private ItemProcessor<Agent, Agent> testProcessor;
	
	@Autowired
	private  ItemWriter<? super Agent> dbWriter;
	
	@Autowired
	private  ItemWriter<? super Agent> dbUpdate;
	
	@Autowired
	private AgentSkipListener agentSkipListener;
	
	
	@Bean
	public Job fileReaderJob() {
		return jobBuilderFactory.get("fileReaderJob23").start(fileReaderStep()).
				listener(new JobListener()).build();
	}
	
	@Bean
	public Step fileReaderStep() {
		// TODO Auto-generated method stub
		return stepBuilderFactory.get("fileReaderStep")
				.<Agent,Agent>chunk(20000)
				.faultTolerant()
				.listener(new ChunkListener())
				.reader(fileReader)
				.processor(testProcessor)
				//.processor(processor())
				.writer(dbUpdate)
				.faultTolerant()//容错  retry充实，skip跳过
				.skip(Exception.class)//异常类型
				.skipLimit(200)//跳过错误次数
				.listener(agentSkipListener)//记录错误对象
				.taskExecutor(taskExecutor())//开启线程
				.build();
	}
	
	@Bean
	public CompositeItemProcessor<Agent,Agent> processor(){
		CompositeItemProcessor<Agent,Agent> processor = new CompositeItemProcessor<Agent,Agent>();
		List<ItemProcessor<Agent,Agent>> list = new ArrayList<>();
		list.add(testProcessor);
		processor.setDelegates(list);
		return processor;
	}
	
	
	/**
	 * spring线程池，注意配置要小于数据连接池
	 * 读数方法要加线程安全synchronized
	 * @return
	 */
	@Bean
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(20);
        threadPoolTaskExecutor.setQueueCapacity(20);
        threadPoolTaskExecutor.setKeepAliveSeconds(200);
        return threadPoolTaskExecutor;
    }
}
