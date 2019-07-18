import entity.Customer;
import entity.Task;

import java.util.Random;
import java.util.concurrent.locks.Lock;

/**
 * The unit which will be sent to ThreadPool and run
 *
 * @author jikangwang
 */
public class ProcessTask implements Runnable {

    private Task task;
    private int executeTime;
    private MoveBackCallBack callBack;


    public ProcessTask(Task task, Customer customer, MoveBackCallBack callBack) {
        this.task = task;
        this.callBack = callBack;
        executeTime = getRandomNumberInRange(customer.getTaskMinSeconds(), customer.getTaskMaxSeconds()) * 1000;
    }

    @Override
    public void run() {
        System.out.println(task.get_id() + " running. ExeTime=" + executeTime);
        try {
            Thread.sleep(executeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println(task.get_id()+" finish");
        callBack.moveBack(task);
        synchronized (Lock.class) {
            Lock.class.notify();
        }
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public interface MoveBackCallBack {
        void moveBack(Task task);
    }

}
