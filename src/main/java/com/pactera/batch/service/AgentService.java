package com.pactera.batch.service;

import java.util.List;

import com.pactera.batch.model.Agent;

public interface AgentService {
	public int insertBatch(List<Agent> list);
	public int insertBatch2(List<Agent> list);
}
