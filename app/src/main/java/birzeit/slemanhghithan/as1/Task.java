package birzeit.slemanhghithan.as1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Task implements Serializable {
    private String title;
    private String description;
    private Date date;
    private String status;

    public Task(String title, String description, Date date, String status) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;

    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(getTitle(), task.getTitle());
    }
}
