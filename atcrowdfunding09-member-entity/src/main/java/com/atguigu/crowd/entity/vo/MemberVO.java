package com.atguigu.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author Administrator
 * @Date 2020/5/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO implements Serializable {
 private String loginAcct;
    private String userPswd;
    private String email;
    private String userName;
    private String phoneNum;
    private String code;
}
