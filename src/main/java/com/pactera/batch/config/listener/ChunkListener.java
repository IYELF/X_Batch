package com.pactera.batch.config.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

public class ChunkListener {
	@BeforeChunk
	public void beforeChunk(ChunkContext context) {
		//System.out.println("before chunk==="+new Date().getTime());
	}
	
	@AfterChunk
	public void afterChunk(ChunkContext context) {
		//System.out.println("after chunk==="+new Date().getTime());
	}
}
