package entity;


import java.util.Date;
import java.util.Objects;

/**
 * @author jikangwang
 */
public class Task {
    private String _id;
    private String customerId;
    private Date insertedTime;


    public Task(String _id, String customerId, Date insertedTime) {
        this._id = _id;
        this.customerId = customerId;
        this.insertedTime = insertedTime;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getInsertedTime() {
        return insertedTime;
    }

    public void setInsertedTime(Date insertedTime) {
        this.insertedTime = insertedTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "_id='" + _id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", insertedTime=" + insertedTime +
                '}';
    }

}

