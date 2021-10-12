package roomSensors.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gameTable")
public class gameRotation {
    @PrimaryKey(autoGenerate = true)
    private int pk_id;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "gameX")
    private float gameX;

    @ColumnInfo(name = "gameY")
    private float gameY;

    @ColumnInfo(name = "gameZ")
    private float gameZ;

    public gameRotation(float gameX, float gameY, float gameZ, String date, String time){
        this.gameX = gameX;
        this.gameY = gameY;
        this.gameZ = gameZ;
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

    public float getGameX() {
        return gameX;
    }

    public void setGameX(float gameX) {
        this.gameX = gameX;
    }

    public float getGameY() {
        return gameY;
    }

    public void setGameY(float gameY) {
        this.gameY = gameY;
    }

    public float getGameZ() {
        return gameZ;
    }

    public void setGameZ(float gameZ) {
        this.gameZ = gameZ;
    }
}
