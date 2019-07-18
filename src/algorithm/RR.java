package algorithm;

import entity.Task;

import java.util.*;

/**
 * For this algorithm, tasks should be selected from the to-do list based
 * on the customer they are associated with. This should be done in a round
 * robin fashion based on the customers. For example, if there are 3 customers,
 * the first task is chosen for customer1, then a task for customer2, customer3,
 * then back to customer1, and so on. When choosing a task narrowed down to a
 * specific customer (e.g knowing that you are choosing a task for customer1),
 * the task with the earliest insertedTime should be selected.
 *
 *
 *
 *
 * First, we map the customerId to 0,1,2,3,4...
 * Then, I group all tasks which have the same customerId in a linkedList. And put it to a list by it's Id.
 * This is what rrList looks like:
 *
 * 0 (Customer1):  Task1 -> Task2 -> Task3
 * 1 (Customer2):  Task5 -> Task4
 * 2 (Customer3):  Task6
 *
 * iterator here indicates which customer we need to add.
 *
 * For example, iterator will be 0 at first.
 * When we call getNext(), it will return the first element at position 0. And delete the first element.
 * Then iterator will become 1
 * Then we call getNext() again, it will return the first element at position 1. And delete the first element.
 *
 * If we can not get a task, getNext() will return null and block the thread.
 *
 *
 *
 * When we get a task to add, we just need to find the index of this Task's customer and insert it to the end of linkedList.
 *
 *
 * @author jikangwang
 */
public class RR implements ScheduleAlgo {
    List<LinkedList<Task> > rrList;
    TreeMap<Integer,ArrayList<Task> > mp;

    /**
     * map the customer's ID to interger like 0,1,2,3,4 and the integer will be used as the index of rrList
     */
    HashMap<String,Integer> mpFromIdToIndex;
    private int iterator=0;


    public RR() {
        mpFromIdToIndex=new HashMap<>();
        mp=new TreeMap<>();
        rrList=new ArrayList<>();
    }

    @Override
    synchronized public Task getNext() {
        Task task=null;
        LinkedList<Task> linkedList=rrList.get(iterator);
        if(linkedList.size()!=0){
            task=linkedList.getFirst();
            linkedList.remove(0);
            iterator++;
            iterator%=rrList.size();
        }
        return task;
    }

    @Override
    synchronized public void addBack(Task task) {
        rrList.get(mpFromIdToIndex.get(task.getCustomerId())).add(task);
    }

    @Override
    public void updateTodoList(List<Task> list) {
        int index=0;
        for(Task task:list){
            if(!mpFromIdToIndex.containsKey(task.getCustomerId())){
                mpFromIdToIndex.put(task.getCustomerId(),index);
                index++;
            }
            Integer integer = mpFromIdToIndex.get(task.getCustomerId());

            mp.putIfAbsent(integer, new ArrayList<>());

            List<Task> taskList = mp.get(integer);

            taskList.add(task);

        }

        for(Map.Entry<Integer,ArrayList<Task>> entry:mp.entrySet()){
            ArrayList<Task> tasklist=entry.getValue();
            tasklist.sort(new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    return o1.getInsertedTime().compareTo(o2.getInsertedTime());
                }
            });
            rrList.add(new LinkedList<Task>(tasklist));
        }

    }
}
