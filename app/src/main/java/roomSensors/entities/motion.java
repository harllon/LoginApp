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

    @ColumnInfo(name = "dateTime")
    private String dateTime;

    public motion(float motion, String dateTime){
        this.dateTime = dateTime;
        this.motion = motion;
    }

    public float getMotion() {
        return motion;
    }

    public void setMotion(float motion) {
        this.motion = motion;
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
