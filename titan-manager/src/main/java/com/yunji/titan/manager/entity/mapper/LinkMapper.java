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
package com.yunji.titan.manager.entity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.yunji.titan.manager.entity.Link;

/**
 * @desc 链路表实体映射Mapper
 *
 * @author liuliang
 *
 */
@Component
public class LinkMapper implements RowMapper<Link>{

	@Override
	public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
		Link link = new Link();
		link.setLinkId(rs.getLong("link_id"));
		link.setLinkName(rs.getString("link_name"));
		link.setProtocolType(rs.getInt("protocol_type"));
		link.setStresstestUrl(rs.getString("stresstest_url"));
		link.setRequestType(rs.getInt("request_type"));
		
		link.setContentType(rs.getInt("content_type"));
		link.setCharsetType(rs.getInt("charset_type"));
		link.setTestfilePath(rs.getString("testfile_path"));
		link.setCreateTime(rs.getLong("create_time"));
		link.setModifyTime(rs.getLong("modify_time"));
		return link;
	}

}
