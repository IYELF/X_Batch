package com.pactera.batch.config.reader;

import java.util.Date;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

import com.pactera.batch.model.Agent;

@Configuration
public class FlatFileReader{
	
	@Bean
	public synchronized FlatFileItemReader<Agent> fileReader() {
		// TODO Auto-generated method stub
		FlatFileItemReader<Agent> reader = new FlatFileItemReader<Agent>();
		reader.setResource(new ClassPathResource("Agent.txt"));
//		reader.setLinesToSkip(1);//跳过第一行
		//解析
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames(new String[]{"id","name","password","email","phone","status"});
		DefaultLineMapper<Agent> mapper  = new DefaultLineMapper<Agent>();
		mapper.setLineTokenizer(tokenizer);
		mapper.setFieldSetMapper(new FieldSetMapper<Agent>() {
			
			@Override
			public Agent mapFieldSet(FieldSet fieldSet) throws BindException {
				Agent Agent = new Agent();
				Agent.setId(fieldSet.readInt("id"));
				Agent.setName(fieldSet.readString("name"));
				Agent.setPassword(fieldSet.readString("password"));
				Agent.setEmail(fieldSet.readString("email"));
				Agent.setPhoneNumber(fieldSet.readString("phone"));
				Agent.setStatus(fieldSet.readInt("status"));
				Agent.setLastLoginTime(new Date());
				Agent.setLastUpdateTime(new Date());
				Agent.setCreateTime(new Date());
				return Agent;
			}
		});
		mapper.afterPropertiesSet();
		reader.setLineMapper(mapper);
		return reader;
	}
}
