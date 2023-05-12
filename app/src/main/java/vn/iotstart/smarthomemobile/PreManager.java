package vn.iotstart.smarthomemobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import vn.iotstart.smarthomemobile.activity.LoginActivity;
import vn.iotstart.smarthomemobile.model.User;

public class PreManager {
    static Context context;
    public PreManager (Context context){
        this.context = context;
    }

    public void saveUserDetail(User user){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", user.getId());
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());
        editor.putString("avatar", user.getAvatar());
        editor.putString("gender", user.isGender());
        editor.putString("email", user.getEmail());
        editor.putString("phoneNumber", user.getPhoneNumber());
        editor.putString("address", user.getAddress());
        editor.commit();
    }

    public String getId(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", "");
    }

    public boolean isUserLogout(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        boolean isAccountEmpty = sharedPreferences.getString("id", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("password", "").isEmpty();
        return isAccountEmpty || isPasswordEmpty;
    }
    public User getUser(){
        User user = new User();

        if (!isUserLogout()){
            SharedPreferences sharedPreferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
            user.setId(sharedPreferences.getString("id", ""));
            user.setPassword(sharedPreferences.getString("password", ""));
            user.setEmail(sharedPreferences.getString("email", ""));
            user.setGender(sharedPreferences.getString("gender", ""));
            user.setAddress(sharedPreferences.getString("address", ""));
            user.setPhoneNumber(sharedPreferences.getString("phoneNumber", ""));
            user.setUsername(sharedPreferences.getString("username", ""));
            user.setAvatar(sharedPreferences.getString("avatar", ""));
        }

        return user;
    }

    public static void logoutUser() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Perform any additional actions required upon logout
        // For example, navigate to the login screen
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
