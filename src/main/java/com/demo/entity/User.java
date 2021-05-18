package com.demo.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 专业类型
     */
    @Column(name = "major_type")
    private Integer majorType;

    /**
     * 是否跨部门
     */
    @Column(name = "is_cross_sector")
    private Boolean isCrossSector;

    /**
     * 组长
     */
    @Column(name = "group_leader")
    private String groupLeader;

    /**
     * 成员
     */
    private String member;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取账号
     *
     * @return account - 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取名称
     *
     * @return user_name - 名称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置名称
     *
     * @param userName 名称
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取专业类型
     *
     * @return major_type - 专业类型
     */
    public Integer getMajorType() {
        return majorType;
    }

    /**
     * 设置专业类型
     *
     * @param majorType 专业类型
     */
    public void setMajorType(Integer majorType) {
        this.majorType = majorType;
    }

    /**
     * 获取是否跨部门
     *
     * @return is_cross_sector - 是否跨部门
     */
    public Boolean getIsCrossSector() {
        return isCrossSector;
    }

    /**
     * 设置是否跨部门
     *
     * @param isCrossSector 是否跨部门
     */
    public void setIsCrossSector(Boolean isCrossSector) {
        this.isCrossSector = isCrossSector;
    }

    /**
     * 获取组长
     *
     * @return group_leader - 组长
     */
    public String getGroupLeader() {
        return groupLeader;
    }

    /**
     * 设置组长
     *
     * @param groupLeader 组长
     */
    public void setGroupLeader(String groupLeader) {
        this.groupLeader = groupLeader;
    }

    /**
     * 获取成员
     *
     * @return member - 成员
     */
    public String getMember() {
        return member;
    }

    /**
     * 设置成员
     *
     * @param member 成员
     */
    public void setMember(String member) {
        this.member = member;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", account=").append(account);
        sb.append(", password=").append(password);
        sb.append(", userName=").append(userName);
        sb.append(", majorType=").append(majorType);
        sb.append(", isCrossSector=").append(isCrossSector);
        sb.append(", groupLeader=").append(groupLeader);
        sb.append(", member=").append(member);
        sb.append("]");
        return sb.toString();
    }
}