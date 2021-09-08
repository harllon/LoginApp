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

    @ColumnInfo(name = "dateTime")
    private String dateTime;

    public stepCounter(float step, String dateTime){
        this.step = step;
        this.dateTime = dateTime;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public float getStep() {
        return step;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getPk_id() {
        return pk_id;
    }

    public void setPk_id(int pk_id) {
        this.pk_id = pk_id;
    }
}
