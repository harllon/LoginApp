package roomSensors.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "accelerometerTable")
public class accelerometer {
    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "ax")
    private float ax;

    @ColumnInfo(name = "ay")
    private float ay;

    @ColumnInfo(name = "az")
    private float az;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    public accelerometer(float ax, float ay, float az, String date, String time){
        this.ax = ax;
        this.ay = ay;
        this.az = az;
        this.date = date;
        this.time = time;
    }

    public void setAz(float az) {
        this.az = az;
    }

    public void setAy(float ay) {
        this.ay = ay;
    }

    public void setAx(float ax) {
        this.ax = ax;
    }

    public float getAz() {
        return az;
    }

    public float getAy() {
        return ay;
    }

    public float getAx() {
        return ax;
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
