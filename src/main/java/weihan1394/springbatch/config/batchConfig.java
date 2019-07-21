package weihan1394.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import weihan1394.springbatch.tasks.taskOne;
import weihan1394.springbatch.tasks.taskTwo;

@Configuration
@EnableBatchProcessing
public class batchConfig {
  @Autowired
  private JobBuilderFactory jobs;

  @Autowired
  private StepBuilderFactory steps;

  @Bean
  public Step stepOne(){
    return steps.get("stepOne")
        .tasklet(new taskOne())
        .build();
  }

  @Bean
  public Step stepTwo(){
    return steps.get("stepTwo")
        .tasklet(new taskTwo())
        .build();
  }

  @Bean
  public Job demoJob(){
    return jobs.get("demoJob")
        .incrementer(new RunIdIncrementer())
        .start(stepOne())
        .next(stepTwo())
        .build();
  }
}
