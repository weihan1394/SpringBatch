package weihan1394.springbatch.writer;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import weihan1394.springbatch.model.Employee;

public class ConsoleItemWriter<T> implements ItemWriter<T> {

  @Override
  public void write(List<? extends T> items) throws Exception {
    for (T item : items) {
      Employee employee = (Employee)item;
      System.out.println(employee.getFirstName());
    }
  }
}
