/*
 * Copyright (C) 2015-2020 yunjiweidian
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.yunji.titan.manager.bo;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

/**
 * 用户登录参数BO
 *
 * @author liuliang
 *
 */
@Component
public class UserInfoBO implements Serializable {

	private static final long serialVersionUID = 8034878444962808442L;

	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	private String username;
	/**
	 * 密码
	 */
	@NotBlank(message = "密码不能为空")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserInfoBO [username=" + username + ", password=" + password + "]";
	}

}
