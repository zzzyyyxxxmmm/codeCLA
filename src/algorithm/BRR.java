package algorithm;

import entity.Task;

import java.lang.reflect.Parameter;
import java.util.*;

/**
 * For this algorithm, tasks should be selected from the to-do list
 * based on the customer they are associated with. This should be done
 * in a round robin fashion based on the customers and the number of tasks
 * currently in the processing list. The goal of this algorithm is to have
 * the same number of tasks for each customer in the processing list at any
 * time. This should be demonstrated by setting much higher values for
 * taskMinSeconds and taskMaxSeconds for a specific customer, and showing
 * that tasks are still distributed evenly across all customers.
 * One customer should not dominate the processing list.
 *
 * First, how many different customers for different types is confirmed after we get the todolist and the sizeof processinglist.
 * 1. The maximum tasks for one customer will be the minimum number of task for all customers
 * 0 (Customer1):  Task1 -> Task2 -> Task3
 * 1 (Customer2):  Task5 -> Task4
 * 2 (Customer3):  Task6
 *
 * The size of customer3 is the maximum tasks we can run together for each types of customers
 *
 * So we can run (maximum tasks for one customer * the number of the customer types) = 3*1=3 tasks in sum.
 *
 *
 * 2. If the maximum tasks for one customer * the number of the customer types is bigger than the processinglist size
 * Then, we can get the maximum tasks for one customer by (MAX_TASKS_PROCESSING/the number of the customer types)
 *
 * 3. we can get the real maximum tasks for one customer by 1,2 above.
 *
 *
 *
 * After we calculate the maximum tasks for one customer, we set a leftCapacityForCustomer, it records how many capacity left for one
 * customer.
 * For example:
 *                                              capacity
 * 0 (Customer1):  Task1 -> Task2 -> Task3      1
 * 1 (Customer2):  Task5 -> Task4               1
 * 2 (Customer3):  Task6                        1
 *
 * we go through the rrList to get the task and decrease the capacity.
 * If the capacity is 0 that means we can not add more tasks until we add task back.
 *
 * @author jikangwang
 */
public class BRR extends RR implements ScheduleAlgo {

    private int maxNumForEachCustomer;
    private int minNumCustomerTask = Integer.MAX_VALUE;
    private int numForEachCustomer;
    private HashMap<Integer, Integer> leftCapacityForCustomer = new HashMap<>();


    public BRR() {

    }

    @Override
    synchronized public Task getNext() {

        Task task = null;
        for (LinkedList<Task> linkedList : rrList) {
            if (linkedList.size() != 0) {
                task = linkedList.getFirst();

                int index = mpFromIdToIndex.get(task.getCustomerId());
                int cap = leftCapacityForCustomer.get(index);
                if (cap <= 0) {
                    task = null;
                    continue;
                }
                leftCapacityForCustomer.put(index, cap - 1);
                linkedList.remove(0);
                return task;
            }
        }

        return task;

    }

    @Override
    synchronized public void addBack(Task task) {
        super.addBack(task);

        int index = mpFromIdToIndex.get(task.getCustomerId());
        int cap = leftCapacityForCustomer.get(index);

        leftCapacityForCustomer.put(index, cap + 1);
    }

    @Override
    public void updateTodoList(List<Task> list) {
        super.updateTodoList(list);

        for (Map.Entry<Integer, ArrayList<Task>> entry : mp.entrySet()) {
            minNumCustomerTask = Math.min(minNumCustomerTask, entry.getValue().size());
        }
        maxNumForEachCustomer = Parameters.MAX_TASKS_PROCESSING / rrList.size();

        numForEachCustomer = Math.min(maxNumForEachCustomer, minNumCustomerTask);


        System.out.println("capacity for each customer: " + numForEachCustomer);
        for (Map.Entry<Integer, ArrayList<Task>> entry : mp.entrySet()) {
            leftCapacityForCustomer.put(entry.getKey(), numForEachCustomer);
        }


    }
}
