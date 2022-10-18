package com.example.tagorder.item.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
@Table(name="members")//만들어둔 Items 테이블이랑 연동할것이다,
public class member {

    @Id//프라이머리 키라고 선언하는것
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동증가값이다.
    private int id;

    @Column(length = 50, nullable = false)
    private String name;


    @Column(unique = true)
    private int phone;

    @Column(length = 50)
    private String password;


}
