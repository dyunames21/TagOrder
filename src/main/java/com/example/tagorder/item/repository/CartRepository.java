package com.example.tagorder.item.repository;

import com.example.tagorder.item.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
//ID가 int 형이니까 Integer 로 가져옴


    List<Cart> findByMemberId(int memberId);
    //멤버 아이디를 받으면 멤버 아이디에 해당하는 카트 받아옴
    Cart findByMemberIdAndItemId(int memberId, int itemId);
    //멤버, 아이템 아이디 값이 있으면 카트 정보 가져오는 메서드
    void deleteByMemberId(int memberID);
}
