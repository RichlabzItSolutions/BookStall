package com.fototechparivarstalls.webservices;

import android.content.Context;
import android.content.SharedPreferences;

import com.fototechparivarstalls.model.GetAddons.AddaddonsDetails;
import com.fototechparivarstalls.model.Login.UserStoreDetails;


public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);

    }

    public void storeUserData(UserStoreDetails cUser ) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putString("userId", cUser.getUserId());
        userLocalDatabaseEditor.putString("userCode", cUser.getUserCode());
        userLocalDatabaseEditor.putString("firstName", cUser.getFirstName());
        userLocalDatabaseEditor.putString("lastName", cUser.getLastName());
        userLocalDatabaseEditor.putString("emailId", cUser.getEmailId());
        userLocalDatabaseEditor.putString("mobileNumber", cUser.getMobileNumber());
        userLocalDatabaseEditor.putString("userCity", cUser.getUserCity());
        userLocalDatabaseEditor.putString("profileImage", cUser.getProfileImage());
        userLocalDatabaseEditor.putString("token", cUser.getToken());

        userLocalDatabaseEditor.apply();
    }

    public void storeQtyData(AddaddonsDetails cQtyr ) {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.putInt("addon_id", cQtyr.getAddon_id());
        userLocalDatabaseEditor.putInt("qty", cQtyr.getQty());

        userLocalDatabaseEditor.apply();
    }

    public UserStoreDetails getLoggedInUser() {
        String userId = userLocalDatabase.getString("userId", "");
        String userCode = userLocalDatabase.getString("userCode", "");
        String firstName = userLocalDatabase.getString("firstName", "");
        String lastName = userLocalDatabase.getString("lastName", "");
        String emailId = userLocalDatabase.getString("emailId", "");
        String mobileNumber = userLocalDatabase.getString("mobileNumber", "");
        String userCity = userLocalDatabase.getString("userCity", "");
        String profileImage = userLocalDatabase.getString("profileImage", "");
        String token = userLocalDatabase.getString("token", "");

        UserStoreDetails cUser = new UserStoreDetails( userId, userCode, firstName, lastName, emailId, mobileNumber,userCity, profileImage, token);
        return cUser;
    }

    public AddaddonsDetails getAddonOty() {
        int addon_id = userLocalDatabase.getInt("addon_id", 1);
        int qty = userLocalDatabase.getInt("qty", 2);

        AddaddonsDetails cQtyr = new AddaddonsDetails(addon_id, qty);
        return cQtyr;
    }

    public void clearUserData() {
        SharedPreferences.Editor userLocalDatabaseEditor = userLocalDatabase.edit();
        userLocalDatabaseEditor.clear();
        userLocalDatabaseEditor.apply();
    }

}
