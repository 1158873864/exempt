package njurestaurant.njutakeout.entity.event;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "time")
    private Date time;
    @Column(name = "uid")
    private int uid;
    @Column(name = "message")
    private String message;

    public Log() {
    }

    public Log(Date time, int uid, String message) {
        this.time = time;
        this.uid = uid;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
