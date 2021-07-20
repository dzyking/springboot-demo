package com.demo.vo;

import lombok.Data;

import java.util.List;

/**
 * @date 2020/6/28
 * @desc
 */
@Data
public class UserVO {

    private Integer userId;

    private String userName;

    private Integer questionNum;

    private long startTime;

    private Integer majorType;

    private List<Integer> majorQuestionIdList;

}
