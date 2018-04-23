package com.softel.springboot.controller;

import javax.validation.groups.Default;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.softel.springboot.repository.UserRepository;
import com.softel.springboot.annotation.BindingResultAnnotation;
import com.softel.springboot.base.BaseController;
import com.softel.springboot.entity.UserEntity;
import com.softel.springboot.service.UserService;
import com.softel.springboot.util.Result;
import com.softel.springboot.util.ResultUtils;
import com.softel.springboot.validator.add.UserEntityAdd;
import com.softel.springboot.validator.update.UserEntityUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="用户操作相关接口")
@RestController
public class UserController extends BaseController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 查询所有
	 * @return
	 * @throws SchedulerException 
	 */
	@ApiOperation(value="查询所有用户")
	@RequestMapping(value="/findall", method=RequestMethod.GET)
    public Result findall() throws SchedulerException {
        return success(userRepository.findAll());
    }
	
	/**
	 * 联合查询
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value="多字段联合查询用户")
	@RequestMapping(value="/findexample", method=RequestMethod.POST)
    public Result findexample(@RequestBody UserEntity userEntity) throws Exception {
        return success(userService.findByExample(userEntity));
    }
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据id查询用户")
	@RequestMapping(value="/findone", method=RequestMethod.GET)
    public Result findone(Integer id) {
        return success(userRepository.findOne(id));
    }
	
	/**
	 * 分页查询
	 * @return
	 */
	@ApiOperation(value="分页查询用户")
	@RequestMapping(value="/cutpage", method=RequestMethod.GET)
    public Result cutpage() {
		UserEntity userEntity = new UserEntity();
		userEntity.setPage(1);
		userEntity.setSize(2);
		userEntity.setSord("desc");
		return success(userService.cutPage(userEntity));
    }
	
	/**
	 * 添加
	 * @param user
	 * @return
	 */
	@ApiOperation(value="添加用户")
	@RequestMapping(value="/add", method=RequestMethod.GET)
	@BindingResultAnnotation
    public Result add(@Validated({UserEntityAdd.class, Default.class}) UserEntity userEntity, BindingResult bindingResult) {
		return ResultUtils.success(userRepository.save(userEntity));
    }
	
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	@ApiOperation(value="修改用户")
	@RequestMapping(value="/update", method=RequestMethod.GET)
	@BindingResultAnnotation
    public Result update(@Validated({UserEntityUpdate.class, Default.class}) UserEntity userEntity,BindingResult bindingResult) {
        return success(userRepository.save(userEntity));
    }
	
	/**
	 * 删除
	 * @param user
	 */
	@ApiOperation(value="删除用户")
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	@BindingResultAnnotation
    public Result findbyname(UserEntity userEntity) {
		userRepository.delete(userEntity);
        return success();
    }
	
}
