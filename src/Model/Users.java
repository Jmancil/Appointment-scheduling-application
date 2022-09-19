package Model;


//User Class / construct
public class Users {
    private String password;
    private final String userName;
    private final int userId;


    //username and password construct
    public Users(int userID, String userName) {
        this.userId = userID;
        this.userName = userName;
    }

    //constructing user object for use

    public Users(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }


    public String getPassword(){
        return password;
    }

    public String getUserName(){
        return userName;
    }

    public int getUserId(){
        return userId;
    }

    public static void add(Users users) {
    }

}
