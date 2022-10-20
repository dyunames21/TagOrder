package com.example.tagorder.item.repository;

import com.example.tagorder.item.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
//ID가 int 형이니까 Integer 로 가져옴
    List<Order> findByMemberIdOrderByIdDesc(int memberId);

    Order findById(int id);

    Order findByUid(String uid);
}
