package com.atguigu.spzx.model.dto.system;

import lombok.Data;

// com.atguigu.spzx.model.dto.system
@Data
public class LoginDto {

    private String userName ;
    private String password ;
    private String captcha;
    private String codeKey;
}