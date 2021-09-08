package roomSensors.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gravityTable")
public class gravity {
    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "gx")
    private float gx;

    @ColumnInfo(name = "gy")
    private float gy;

    @ColumnInfo(name = "gz")
    private float gz;

    @ColumnInfo(name = "dateTime")
    private String dateTime;

    public gravity(float gx, float gy, float gz, String dateTime){
        this.dateTime = dateTime;
        this.gx = gx;
        this.gy = gy;
        this.gz = gz;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setPk_id(int pk_id) {
        this.pk_id = pk_id;
    }

    public int getPk_id() {
        return pk_id;
    }

    public float getGx() {
        return gx;
    }

    public float getGy() {
        return gy;
    }

    public float getGz() {
        return gz;
    }

    public void setGx(float gx) {
        this.gx = gx;
    }

    public void setGy(float gy) {
        this.gy = gy;
    }

    public void setGz(float gz) {
        this.gz = gz;
    }

}
