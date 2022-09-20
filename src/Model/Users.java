package Model;


/**
 * Users class object
 */
public class Users {
    private String password;
    private final String userName;
    private final int userId;


//    /**
//     * Constructor for users
//     * @param userID        Users ID
//     * @param userName      Users user
//     */
//    public Users(int userID, String userName) {
//        this.userId = userID;
//        this.userName = userName;
//    }

    /**
     * Constructor for users
     * @param userId    Users userId
     * @param userName  Users UserName
     * @param password  Users Password
     */
    public Users(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }


    /**
     * Gets the Users password
     *
     * @return Users password is returned
     */
    public String getPassword(){
        return password;
    }

    /**
     * Gets the Users UserName
     *
     * @return Users UserName is returned
     */
    public String getUserName(){
        return userName;
    }

    /**
     * Gets the Users UserId
     *
     * @return Users UserId is returned
     */
    public int getUserId(){
        return userId;
    }

    public static void add(Users users) {
    }

}
