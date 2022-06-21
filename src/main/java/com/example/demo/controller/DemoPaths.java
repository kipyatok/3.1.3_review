package com.example.demo.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.example.demo.controller.DemoParams.ID;
import static com.example.demo.controller.DemoParams.USERS;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DemoPaths {

    public static final String BASE_URL = "/";
    public static final String USER_URL = BASE_URL + "user";
    public static final String ID_URL = "{" + ID + "}";

    public static final String ADMIN_URL = BASE_URL + "admin";
    public static final String REDIRECT_URL = "redirect:" + ADMIN_URL + "/";

    public static final String GET_USERS_URL = BASE_URL + USERS;

    public static final String UPDATE_USER = USER_URL + "/update";
    public static final String DELETE_USER = USER_URL + "/delete";

    public static final String SAVE_USER_URL = USER_URL + "/save";
    public static final String UPDATE_USER_URL = UPDATE_USER + "/" + ID_URL;
    public static final String DELETE_USER_URL = DELETE_USER + "/" + ID_URL;
}
