import algorithm.BRR;
import algorithm.FIFO;
import algorithm.RR;
import entity.Customer;
import entity.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Customer> customerList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();

        customerList.add(new Customer("customer1", 3, 3));
        taskList.add(new Task("task1", "customer1", new Date()));
        taskList.add(new Task("task2", "customer1", new Date()));

        customerList.add(new Customer("customer3", 1, 1));
        taskList.add(new Task("task3", "customer3", new Date()));


        TaskManager taskManager = new TaskManager(taskList, customerList, new FIFO());

        taskManager.commit();

    }
}
