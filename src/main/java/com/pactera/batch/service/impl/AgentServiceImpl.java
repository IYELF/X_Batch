package com.pactera.batch.service.impl;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.batch.mapper.AgentMapper;
import com.pactera.batch.model.Agent;
import com.pactera.batch.service.AgentService;

@Service
public class AgentServiceImpl implements AgentService{

	@Autowired
	private AgentMapper agentMapper;
	
	@Autowired
    private SqlSession sqlSession;
	
	@Autowired
    private SqlSessionTemplate sqlSessionTemplate;
	
	@Transactional
	public int insertBatch(List<Agent> list) {
		// TODO Auto-generated method stub
		return agentMapper.insertBatch(list);
	}

	@Transactional
	public int insertBatch2(List<Agent> list) {
		// TODO Auto-generated method stub
		try {
			sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);//跟上述sql区别
		for(int i=0;i<list.size();i++) {
			agentMapper.insertSelective(list.get(i));
		}
		sqlSession.commit();
        sqlSession.clearCache();
		} catch (Exception e) {
            if (sqlSession != null)
                sqlSession.rollback();
        } finally {
            if (sqlSession != null)
                sqlSession.close();
        }
		return 0;
	}

}
