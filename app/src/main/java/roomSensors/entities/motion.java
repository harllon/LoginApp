package roomSensors.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "motionTable")
public class motion {

    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "motion")
    private float motion;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    public motion(float motion, String date, String time){
        this.date = date;
        this.motion = motion;
        this.time = time;
    }

    public float getMotion() {
        return motion;
    }

    public void setMotion(float motion) {
        this.motion = motion;
    }

    public int getPk_id() {
        return pk_id;
    }

    public void setPk_id(int pk_id) {
        this.pk_id = pk_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
