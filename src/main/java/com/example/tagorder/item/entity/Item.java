package com.example.tagorder.item.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name="items")//만들어둔 Items 테이블이랑 연동할것이다,
public class Item {

    @Id//프라이머리 키라고 선언하는것
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동증가값이다.
    private int id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 100)
    private String imgPath;

    @Column
    private int price;

    @Column
    private int many;


}
