package com.example.tagorder.item.repository;

import com.example.tagorder.item.entity.member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface memberRepository extends JpaRepository<member, Integer> {
//ID가 int 형이니까 Integer 로 가져옴

    member findByNameAndPassword(String name, String password);

}
