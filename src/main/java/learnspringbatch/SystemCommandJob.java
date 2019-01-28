package learnspringbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.SystemCommandTasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@EnableBatchProcessing
@Configuration
@Profile("system-command-job")
public class SystemCommandJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job job() {
		return this.jobBuilderFactory.get("systemCommandJob")
				.start(systemCommandStep())
				.build();
	}

	@Bean
	public Step systemCommandStep() {
		return this.stepBuilderFactory.get("systemCommandStep")
				.tasklet(systemCommandTasklet())
				.build();
	}

	@Bean
	public SystemCommandTasklet systemCommandTasklet() {
		SystemCommandTasklet systemCommandTasklet = new SystemCommandTasklet();

		systemCommandTasklet.setCommand("rm -rf /tmp/tmp.txt");
		systemCommandTasklet.setTimeout(5000);
		systemCommandTasklet.setInterruptOnCancel(true);

		return systemCommandTasklet;
	}

	public static void main(String[] args) {
		SpringApplication.run(SystemCommandJob.class, args);
	}
} 
