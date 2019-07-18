package algorithm;

import entity.Task;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * For this algorithm, tasks should be selected from the to-do list simply
 * based on their insertedTime. The customer the tasks are associated with
 * is not pertinent to the algorithm. The task chosen to be placed in the
 * processing list should be the task with the earliest insertedTime of all
 * to-do tasks.
 *
 *
 * We use linkedList here becaure we need to insert and delete elements frequently.
 * We sort the todolist by it's insertedTime and insert todolist to linkedlist. Because we
 * add back a Task on real time. The new inserttime is the time we insert it to linkedlist. So we
 * just need to simply insert it to the end of the linkedlist.
 *
 *
 *
 * @author jikangwang
 */
public class FIFO implements ScheduleAlgo {
    private LinkedList<Task> fifoList;


    public FIFO() {
        fifoList = new LinkedList<>();
    }

    @Override
    synchronized public Task getNext() {
        if(fifoList.isEmpty())  return null;
        Task task = fifoList.getFirst();
        fifoList.remove(0);
        return task;
    }

    /**
     * pass the todolist to priorityqueue
     * @param list
     */
    @Override
    public void updateTodoList(List<Task> list) {
        list.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.getInsertedTime().compareTo(o2.getInsertedTime());
            }
        });
        fifoList.addAll(list);
    }

    @Override
    synchronized public void addBack(Task task) {
        //System.out.println(task.get_id());
        fifoList.add(task);
    }
}
