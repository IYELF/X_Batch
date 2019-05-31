package com.pactera.batch.config.reader;

import java.util.Date;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import com.pactera.batch.model.Agent;

@Component("restartReader")
public class RestartReader implements ItemStreamReader<Agent>{

	private FlatFileItemReader<Agent> reader = new FlatFileItemReader<Agent>();
	private int curLine=0;
	private boolean restart = false;
	private ExecutionContext executionContext;
	
	public RestartReader() {
		reader = new FlatFileItemReader<Agent>();
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
	}
	
	
	
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		// TODO Auto-generated method stub
		this.executionContext= executionContext;
		if(executionContext.containsKey("curLine")) {
			this.curLine = executionContext.getInt("curLine");
			this.restart = true;
		}else {
			this.curLine = 0;
			executionContext.put("curLine", this.curLine);
			System.out.println("start reading frme line "+this.curLine+1);
		}
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		// TODO Auto-generated method stub
		executionContext.put("curLine", this.curLine);
		System.out.println("currentLine : " + this.curLine);
	}

	@Override
	public void close() throws ItemStreamException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Agent read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		Agent agent = new Agent();
		this.curLine++;
		if(restart) {
			reader.setLinesToSkip(this.curLine-1);
			restart = false ;
			System.out.println("restart reading from line "+this.curLine);
		}
		reader.open(executionContext);
		agent=reader.read();
		
//		if(user !=null && user.getUsername().equals("wrong")) {
//			throw new RuntimeException("something wrong : Username : "+user.getUsername()+" line:"+this.curLine);
//		}
		
		return agent;
	}

}
