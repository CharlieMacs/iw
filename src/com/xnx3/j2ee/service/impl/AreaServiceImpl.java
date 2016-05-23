package com.xnx3.j2ee.service.impl;

import java.util.List;
import com.xnx3.j2ee.dao.AreaDAO;
import com.xnx3.j2ee.entity.Area;
import com.xnx3.j2ee.entity.Log;
import com.xnx3.j2ee.service.AreaService;

public class AreaServiceImpl implements AreaService {
	private AreaDAO areaDAO;
	
	public AreaDAO getAreaDAO() {
		return areaDAO;
	}

	public void setAreaDAO(AreaDAO areaDAO) {
		this.areaDAO = areaDAO;
	}

	@Override
	public void save(Log transientInstance) {
		// TODO Auto-generated method stub
		areaDAO.save(transientInstance);
	}

	@Override
	public void delete(Area persistentInstance) {
		// TODO Auto-generated method stub
		areaDAO.delete(persistentInstance);
	}

	@Override
	public Area findById(Integer id) {
		// TODO Auto-generated method stub
		return areaDAO.findById(id);
	}

	@Override
	public List<Area> findByExample(Area instance) {
		// TODO Auto-generated method stub
		return areaDAO.findByExample(instance);
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return areaDAO.findByProperty(propertyName, value);
	}

	@Override
	public List<Area> findByProvince(Object province) {
		// TODO Auto-generated method stub
		return areaDAO.findByProvince(province);
	}

	@Override
	public List<Area> findByCity(Object city) {
		// TODO Auto-generated method stub
		return areaDAO.findByCity(city);
	}

	@Override
	public List<Area> findByDistrict(Object district) {
		// TODO Auto-generated method stub
		return areaDAO.findByDistrict(district);
	}

	@Override
	public List<Area> findByLatitude(Object latitude) {
		// TODO Auto-generated method stub
		return areaDAO.findByLatitude(latitude);
	}

	@Override
	public List<Area> findByLongitude(Object longitude) {
		// TODO Auto-generated method stub
		return areaDAO.findByLongitude(longitude);
	}

	@Override
	public List findAll() {
		// TODO Auto-generated method stub
		return areaDAO.findAll();
	}

	@Override
	public Area merge(Area detachedInstance) {
		// TODO Auto-generated method stub
		return areaDAO.merge(detachedInstance);
	}

	@Override
	public void attachDirty(Area instance) {
		// TODO Auto-generated method stub
		areaDAO.attachDirty(instance);
	}

	@Override
	public void attachClean(Area instance) {
		// TODO Auto-generated method stub
		areaDAO.attachClean(instance);
	}

}
