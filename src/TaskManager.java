import algorithm.Parameters;
import algorithm.ScheduleAlgo;
import entity.Customer;
import entity.Task;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;

/**
 * @author jikangwang
 */
public class TaskManager {


    ExecutorService executorService = Executors.newFixedThreadPool(Parameters.MAX_TASKS_PROCESSING);
    private ScheduleAlgo scheduleAlgo;
    private HashMap<String, Customer> customerMap = new HashMap<>();
    private List<Task> todoList;
    private List<Task> processingList = Collections.synchronizedList(new ArrayList<>());

    TaskManager(List<Task> todoList, List<Customer> customerList, ScheduleAlgo scheduleAlgo) {
        this.scheduleAlgo = scheduleAlgo;
        scheduleAlgo.updateTodoList(todoList);
        this.todoList = Collections.synchronizedList(todoList);
        for (Customer customer : customerList) {
            customerMap.put(customer.get_id(), customer);
        }
    }

    void commit() {
        new Thread(new Runnable() {
            @Override
            public void run() {


                for (; ; ) {

                    synchronized (Lock.class) {
                        Task task = scheduleAlgo.getNext();
                        todoList.remove(task);
                        processingList.add(task);
                        if (task == null) {
                            try {
                                Lock.class.wait();
                                continue;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("get task: " + task.get_id() + " customer: " + task.getCustomerId());
                        executorService.submit(new ProcessTask(task, GetCustomerById(task.getCustomerId()), new ProcessTask.MoveBackCallBack() {
                            @Override
                            public void moveBack(Task task) {
                                //System.out.println(task.get_id() + " finish in " + Thread.currentThread().getName());
                                task.setInsertedTime(new Date());
                                scheduleAlgo.addBack(task);

                                processingList.remove(task);
                                todoList.add(task);

                            }
                        }));

                    }

                }
            }
        }).start();
    }


    private void outputProcessingTasks() {
        processingList.remove(null);
        System.out.println(processingList);

    }

    private void outputtodoList() {
        System.out.println(todoList);
    }

    private void adjustCustomerSeconds(String customerId, int taskMinSeconds, int taskMaxSeconds) {
        Customer customer = customerMap.get(customerId);

        customer.setTaskMinSeconds(taskMinSeconds);
        customer.setTaskMaxSeconds(taskMaxSeconds);

        customerMap.put(customerId, customer);

    }

    private Customer GetCustomerById(String id) {
        return customerMap.get(id);
    }
}
