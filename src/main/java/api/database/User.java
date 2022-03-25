package api.database;


import org.json.JSONObject;


public class User {


    private String username;
    private String password = null;
    private String email;

    // Default constructor
    public User(){}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Note: The JSONObject doesn't include the password
     */
    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("email", email);
        return json;
    }

    /**
     * Note: The JSONObject doesn't include the password
     */
    public static User fromJSONObject(JSONObject json, boolean requirePassword){
        User user = new User();
        user.username = json.getString("username");
        user.email = json.getString("email");

        if( json.has("password") || requirePassword )
            user.password = json.getString("password");

        return user;
    }
}
