package com.pactera.batch.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pactera.batch.model.Agent;
import com.pactera.batch.service.impl.AgentServiceImpl;

@RestController
@RequestMapping("/agent")
public class AgentController {

	@Autowired
	private AgentServiceImpl agentServiceImpl;
	
	@GetMapping("/insertBatch")
	public String insertBatch() {
		List<Agent> list = new ArrayList<Agent>();
		for(int i=0;i<2000;i++){
			Agent agent = new Agent();
			agent.setId(i);
			agent.setName(i+"");
			agent.setPassword("123456");
			agent.setEmail(1+"@"+i+".com");
			agent.setPhoneNumber(i+"");
			agent.setLastLoginTime(new Date());
			agent.setLastUpdateTime(new Date());
			agent.setStatus(1);
			list.add(agent);
		}
		long begin = new Date().getTime();
		System.out.println("======begin batch======");
		int result=agentServiceImpl.insertBatch(list);
		long end = new Date().getTime();
		System.out.println("======end batch======");
		System.out.println("======cost batch======"+(end-begin));
		return result+"";
	}
	
	@GetMapping("/insertBatch2")
	public String insertBatch2() {
		List<Agent> list = new ArrayList<Agent>();
		for(int i=0;i<200000;i++){
			Agent agent = new Agent();
			agent.setId(i);
			agent.setName(i+"");
			agent.setPassword("123456");
			agent.setEmail(1+"@"+i+".com");
			agent.setPhoneNumber(i+"");
			agent.setLastLoginTime(new Date());
			agent.setLastUpdateTime(new Date());
			agent.setStatus(1);
			list.add(agent);
		}
		long begin = new Date().getTime();
		System.out.println("======begin batch======");
		int result=agentServiceImpl.insertBatch2(list);
		long end = new Date().getTime();
		System.out.println("======end batch======");
		System.out.println("======cost batch======"+(end-begin));
		return result+"";
	}
}
