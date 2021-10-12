package roomSensors.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "stepTable")
public class stepCounter {
    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "step")
    private float step;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    public stepCounter(float step, String date, String time){
        this.step = step;
        this.date = date;
        this.time = time;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public float getStep() {
        return step;
    }

    public int getPk_id() {
        return pk_id;
    }

    public void setPk_id(int pk_id) {
        this.pk_id = pk_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
