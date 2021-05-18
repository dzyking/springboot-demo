package com.demo.vo;

import lombok.Data;

/**
 * @author dzy
 * @date 2020/6/28
 * @desc
 */
@Data
public class AchievementVO {

    private Integer userId;

    private String userName;

    private Integer totalScore;

    private String useTime;

    private boolean isCrossSector;
}
