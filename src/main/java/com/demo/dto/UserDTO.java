package com.demo.dto;

import com.demo.entity.PageParam;
import lombok.Data;

/**
 * @author dzy
 * @date 2020/6/28
 * @desc
 */
@Data
public class UserDTO extends PageParam {

    private String account;

    private String password;

}
