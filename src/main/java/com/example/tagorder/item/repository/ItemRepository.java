package com.example.tagorder.item.repository;

import com.example.tagorder.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
//ID가 int 형이니까 Integer 로 가져옴
    List<Item> findByIdIn(List<Integer> ids);

    Item findItemById(int id);
}
