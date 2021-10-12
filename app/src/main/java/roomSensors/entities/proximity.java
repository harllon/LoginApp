package roomSensors.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "proximityTable")
public class proximity {
    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "distance")
    private float distance;

    public proximity(float distance, String date, String time){
        this.distance = distance;
        this.date = date;
        this.time = time;
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

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getPk_id() {
        return pk_id;
    }

    public void setPk_id(int pk_id) {
        this.pk_id = pk_id;
    }
}
