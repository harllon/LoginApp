package roomSensors.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "illuminanceTable")
public class illuminance {
    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "illuminance")
    private float illuminance;

    public illuminance(float illuminance, String date, String time){
        this.illuminance = illuminance;
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

    public float getIlluminance() {
        return illuminance;
    }

    public void setIlluminance(float illuminance) {
        this.illuminance = illuminance;
    }

    public int getPk_id() {
        return pk_id;
    }

    public void setPk_id(int pk_id) {
        this.pk_id = pk_id;
    }
}
