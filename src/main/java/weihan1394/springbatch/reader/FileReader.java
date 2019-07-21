//package weihan1394.springbatch.reader;
//
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.FileSystemResource;
//import weihan1394.springbatch.model.Employee;
//
//public class FileReader {
//
//  @Bean
//  public FlatFileItemReader<Employee> reader() {
//    //Create reader instance
//    FlatFileItemReader<Employee> reader = new FlatFileItemReader<Employee>();
//
//    //Set input file location
//    reader.setResource(new FileSystemResource("input/inputData.csv"));
//
//    //Set number of lines to skips. Use it if file has header rows.
//    reader.setLinesToSkip(1);
//
//    //Configure how each line will be parsed and mapped to different values
//    reader.setLineMapper(new DefaultLineMapper() {
//      {
//        //3 columns in each row
//        setLineTokenizer(new DelimitedLineTokenizer() {
//          {
//            setNames(new String[]{"id", "firstName", "lastName"});
//          }
//        });
//        //Set values in Employee class
//        setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>() {
//          {
//            setTargetType(Employee.class);
//          }
//        });
//      }
//    });
//    return reader;
//  }
//}
