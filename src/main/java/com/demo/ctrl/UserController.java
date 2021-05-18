package com.demo.ctrl;

import com.demo.dto.UserDTO;
import com.demo.entity.PageParam;
import com.demo.entity.Result;
import com.demo.entity.User;
import com.demo.intercept.logs.Log;
import com.demo.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@Api(tags = "用户")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/getAll")
    @ApiOperation("用户列表")
    @Log(title = "用户列表")
    public String getAll(PageParam pageParam) {
        //设置分页规则
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        //返回所有分页信息参数为查询所有记录的信息
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectAll());
        return Result.success(pageInfo);
    }

    @GetMapping("/getListByParam")
    @ApiOperation("根据参数查询反用户列表")
    @Log(title = "根据参数查询用户列表")
    public String getListByParam(UserDTO dto) {
        //设置分页规则
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        //返回所有分页信息参数为查询所有记录的信息
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectAll());
        return Result.success(pageInfo);
    }
}
