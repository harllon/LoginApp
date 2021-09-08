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

    @ColumnInfo(name = "dateTime")
    private String dateTime;

    public accelerometer(float ax, float ay, float az, String dateTime){
        this.ax = ax;
        this.ay = ay;
        this.az = az;
        this.dateTime = dateTime;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
