package entity;

/**
 * @author jikangwang
 */
public class Customer {
    private String _id;
    private int taskMinSeconds;
    private int taskMaxSeconds;


    public Customer(String _id, int taskMinSeconds, int taskMaxSeconds) {
        this._id = _id;
        this.taskMinSeconds = taskMinSeconds;
        this.taskMaxSeconds = taskMaxSeconds;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getTaskMinSeconds() {
        return taskMinSeconds;
    }

    public void setTaskMinSeconds(int taskMinSeconds) {
        this.taskMinSeconds = taskMinSeconds;
    }

    public int getTaskMaxSeconds() {
        return taskMaxSeconds;
    }

    public void setTaskMaxSeconds(int taskMaxSeconds) {
        this.taskMaxSeconds = taskMaxSeconds;
    }
}
