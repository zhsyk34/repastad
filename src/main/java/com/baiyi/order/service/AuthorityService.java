package com.baiyi.order.service;

import java.util.List;

import com.baiyi.order.pojo.Authority;

public interface AuthorityService {

	public void save(Authority authority);

	public void del(int id);

	public void del(int[] ids);

	public void mod(Authority authority);

	public Authority find(int id);

	public List<Authority> findList();

}
