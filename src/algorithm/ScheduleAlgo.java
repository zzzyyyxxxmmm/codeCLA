package algorithm;

import entity.Task;

import java.util.List;

/**
 * @author jikangwang
 */
public interface ScheduleAlgo {
    Task getNext();
    void updateTodoList(List<Task> list);
    void addBack(Task task);
}
