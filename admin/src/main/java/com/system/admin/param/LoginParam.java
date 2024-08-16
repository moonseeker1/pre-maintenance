package com.system.admin.param;

import lombok.Data;

@Data
public class LoginParam {
    String username;
    String passwd;
    String email;
    String captcha;

}

