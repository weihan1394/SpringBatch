package weihan1394.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import weihan1394.springbatch.model.Employee;
import weihan1394.springbatch.tasks.taskOne;
import weihan1394.springbatch.tasks.taskTwo;
import weihan1394.springbatch.writer.ConsoleItemWriter;

@Configuration
@EnableBatchProcessing
public class batchConfig {

  @Autowired
  private JobBuilderFactory jobs;

  @Autowired
  private StepBuilderFactory steps;

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Bean
  public Step stepOne() {
    return steps.get("stepOne")
        .tasklet(new taskOne())
        .build();
  }

  @Bean
  public Step stepTwo() {
    return steps.get("stepTwo")
        .tasklet(new taskTwo())
        .build();
  }

//  @Bean
//  public Job demoJob() {
//    return jobs.get("demoJob")
//        .incrementer(new RunIdIncrementer())
//        .start(stepOne())
//        .next(stepTwo())
//        .build();
//  }

  @Bean
  public Job readCSVFilesJob() {
    return jobBuilderFactory
        .get("readCSVFilesJob")
        .incrementer(new RunIdIncrementer())
        .start(step1())
        .build();
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1").<Employee, Employee>chunk(5)
        .reader(reader())
        .writer(writer())
        .build();
  }

  @Bean
  public FlatFileItemReader<Employee> reader() {
    //Create reader instance
    FlatFileItemReader<Employee> reader = new FlatFileItemReader<Employee>();

    //Set input file location
    reader.setResource(new FileSystemResource("resource/inputData.txt"));

    //Set number of lines to skips. Use it if file has header rows.
    reader.setLinesToSkip(1);

    //Configure how each line will be parsed and mapped to different values
    reader.setLineMapper(new DefaultLineMapper() {
      {
        //3 columns in each row
        setLineTokenizer(new DelimitedLineTokenizer() {
          {
            setNames(new String[]{"id", "firstName", "lastName"});
          }
        });
        //Set values in Employee class
        setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
          {
            setTargetType(Employee.class);
          }
        });
      }
    });
    return reader;
  }

  @Bean
  public ConsoleItemWriter<Employee> writer() {
    return new ConsoleItemWriter<Employee>();
  }
}
