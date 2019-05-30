package com.pactera.batch.config.listener;

import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

import com.pactera.batch.model.Agent;

@Component
public class AgentSkipListener implements SkipListener<Agent, Agent>{

	@Override
	public void onSkipInRead(Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkipInWrite(Agent item, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSkipInProcess(Agent item, Throwable t) {
		// TODO Auto-generated method stub
		System.out.println("失败数据："+item.toString()+" exception"+t);
	}

}
