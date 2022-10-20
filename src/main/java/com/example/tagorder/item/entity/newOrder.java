package com.example.tagorder.item.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name="neworder")//만들어둔 Items 테이블이랑 연동할것이다,
public class newOrder {

    @Id//프라이머리 키라고 선언하는것
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동증가값이다.
    private int id;

    @Column
    private int memberId;

    @Column(length = 50)
    private String name;

    @Column(nullable = false)
    private int phone;

    @Column(length = 500)
    private String items;

    @Column(length = 500)
    private String uid;

    @Column
    private LocalDate Date;

    @Column
    private boolean finished;

    @Column
    private boolean ordering;

}
