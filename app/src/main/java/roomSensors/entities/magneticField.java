package roomSensors.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "magneticTable")
public class magneticField {
    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "xValue")
    private float valueX;

    @ColumnInfo(name = "yValue")
    private float valueY;

    @ColumnInfo(name = "zValue")
    private float valueZ;

    public magneticField(float valueX, float valueY, float valueZ, String date, String time){
        this.valueX = valueX;
        this.valueY = valueY;
        this.valueZ = valueZ;
        this.date = date;
        this.time = time;
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


    public float getValueX() {
        return valueX;
    }

    public void setValueX(float valueX) {
        this.valueX = valueX;
    }

    public float getValueY() {
        return valueY;
    }

    public void setValueY(float valueY) {
        this.valueY = valueY;
    }

    public float getValueZ() {
        return valueZ;
    }

    public void setValueZ(float valueZ) {
        this.valueZ = valueZ;
    }
}
