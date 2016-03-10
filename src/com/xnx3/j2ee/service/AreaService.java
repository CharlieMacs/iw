package com.xnx3.j2ee.service;

import java.util.List;

import com.xnx3.j2ee.entity.Area;
import com.xnx3.j2ee.entity.Log;

/**
 * 地区，省市区
 * @author 管雷鸣
 *
 */
public interface AreaService {

	public void save(Log transientInstance);

	public void delete(Area persistentInstance);

	public Area findById(java.lang.Integer id);

	public List<Area> findByExample(Area instance);

	public List findByProperty(String propertyName, Object value);

	public List<Area> findByProvince(Object province);

	public List<Area> findByCity(Object city);

	public List<Area> findByDistrict(Object district);

	public List<Area> findByLatitude(Object latitude);

	public List<Area> findByLongitude(Object longitude);

	public List findAll();

	public Area merge(Area detachedInstance);

	public void attachDirty(Area instance);

	public void attachClean(Area instance);

}