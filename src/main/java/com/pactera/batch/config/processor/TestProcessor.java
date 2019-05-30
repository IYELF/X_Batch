package com.pactera.batch.config.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.pactera.batch.model.Agent;

@Component
public class TestProcessor implements ItemProcessor<Agent, Agent>{

	@Override
	public Agent process(Agent item) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("======Processor========"+item.getName());
		//过滤数据，如果不符合返回null
		return item;
	}

}
