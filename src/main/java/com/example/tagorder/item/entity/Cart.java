package com.example.tagorder.item.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name="carts")//만들어둔 Items 테이블이랑 연동할것이다,
public class Cart {

    @Id//프라이머리 키라고 선언하는것
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동증가값이다.
    private int id;

    @Column
    private int memberId;

    @Column
    private int itemId;




}
