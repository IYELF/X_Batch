package com.pactera.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job fileReaderJob;
	
	@GetMapping 
	public String jobRun() throws Exception{
		JobParameters parameters = new JobParametersBuilder()
								.addString("msg","hello").toJobParameters();
		jobLauncher.run(fileReaderJob, parameters);
		return "success";
	}
}
