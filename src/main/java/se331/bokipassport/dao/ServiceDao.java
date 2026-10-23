package se331.bokipassport.dao;

import se331.bokipassport.entity.Service;

import java.util.List;

public interface ServiceDao {
    List<Service> findAll();
}
