package roomTest;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "login_table")
public class Person {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "userName")
    private String userName;
    @NonNull
    @ColumnInfo(name = "Email")
    private String userEmail;
    @NonNull
    @ColumnInfo(name = "Admin")
    private Boolean isAdmin;
    @NonNull
    @ColumnInfo(name = "Password")
    private String userPassword;
    @NonNull
    @ColumnInfo(name = "Name")
    private String name;

    public Person(@NonNull String userName, @NonNull String userEmail, @NonNull String userPassword, @NonNull String name, @NonNull Boolean isAdmin){
        this.userName = userName;
        this.userEmail = userEmail;
        this.name = name;
        this.userPassword = userPassword;
        this.isAdmin = isAdmin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUserName(){
        return this.userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserEmail(){
        return this.userEmail;
    }
    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public Boolean getIsAdmin(){
        return this.isAdmin;
    }
    public void setIsAdmin(Boolean isAdmin){
        this.isAdmin = isAdmin;
    }

    public String getUserPassword(){
        return this.userPassword;
    }
    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

}
