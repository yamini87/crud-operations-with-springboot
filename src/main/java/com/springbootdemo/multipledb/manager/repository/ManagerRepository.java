package com.springbootdemo.multipledb.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springbootdemo.multipledb.model.manager.Manager;



@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer>{

}
