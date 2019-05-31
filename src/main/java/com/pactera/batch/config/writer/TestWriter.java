package com.pactera.batch.config.writer;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pactera.batch.model.Agent;

@Configuration
public class TestWriter{
	@Autowired
	private DataSource dataSource;
	
//	@Override
//	public void write(List<? extends Agent> items) throws Exception {
//		// TODO Auto-generated method stub
//		for (Agent agent : items) {
//			System.out.println(agent.toString());
//		}
//	}
	
	@Bean
	public JdbcBatchItemWriter<Agent> dbWriter() {
		JdbcBatchItemWriter<Agent> writer = new JdbcBatchItemWriter<Agent>();
		writer.setDataSource(dataSource);
		writer.setSql(" insert into BATCH.AGENT (ID, NAME, EMAIL," + 
				"PHONE_NUMBER, PASSWORD, STATUS," + 
				"CREATE_TIME, LAST_LOGIN_TIME, LAST_UPDATE_TIME) values" +
				"(:id ,"
				+ ":name,"
				+ ":email,"
				+ ":phoneNumber,"
				+ ":password,"
				+ ":status,"
				+ ":createTime,"
				+ ":lastLoginTime,"
				+ ":lastUpdateTime)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Agent>());
		return writer;
	} 
	
	@Bean
	public JdbcBatchItemWriter<Agent> dbUpdate() {
		JdbcBatchItemWriter<Agent> writer = new JdbcBatchItemWriter<Agent>();
		writer.setDataSource(dataSource);
		writer.setSql(" update BATCH.AGENT set phone_number = :phoneNumber where id = :id");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Agent>());
		return writer;
	} 
	
}
