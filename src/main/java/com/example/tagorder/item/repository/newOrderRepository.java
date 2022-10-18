package com.example.tagorder.item.repository;

import com.example.tagorder.item.entity.Order;
import com.example.tagorder.item.entity.newOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface newOrderRepository extends JpaRepository<newOrder, Integer> {
//ID가 int 형이니까 Integer 로 가져옴

    newOrder findById(int Id);

}
