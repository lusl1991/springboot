package com.softel.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import com.softel.springboot.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="sys_permission")
@Data
@EqualsAndHashCode(callSuper=false)
public class PermissionEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;
	
	@Column(name="name")
    private String name;//权限名称
    
	@Column(name="descritpion")
    private String descritpion;//权限描述
    
	@Column(name="url")
    private String url;//授权链接
    
	@Column(name="pid")
    private int pid;//父节点id
    
}
