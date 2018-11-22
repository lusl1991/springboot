package com.softel.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import com.softel.springboot.base.BaseEntity;
import com.softel.springboot.validator.add.UserEntityAdd;
import com.softel.springboot.validator.update.UserEntityUpdate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="sys_user")
@Data
@EqualsAndHashCode(callSuper=false)
public class UserEntity extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	@Null(groups = {UserEntityAdd.class}, message = "null.id")
	@NotNull(groups = {UserEntityUpdate.class}, message = "notnull.id")
	private Integer id;
	
	@Column(name="sex")
	@Min(value = 0, message = "format.sex")
	@Max(value = 1, message = "format.sex")
	private Integer sex;
	
	@Column(name="username")
	@NotNull(groups = {UserEntityAdd.class, UserEntityUpdate.class}, message = "notnull.username")
	private String username;
	
	@Column(name="password")
	@NotNull(groups = {UserEntityAdd.class}, message = "notnull.password")
	private String password;
	
}