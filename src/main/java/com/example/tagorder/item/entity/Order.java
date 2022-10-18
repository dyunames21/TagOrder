package com.example.tagorder.item.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name="orders")//만들어둔 Items 테이블이랑 연동할것이다,
public class Order {

    @Id//프라이머리 키라고 선언하는것
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동증가값이다.
    private int id;

    @Column
    private int memberId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private int phone;

    @Column(length = 500, nullable = false)
    private String items;

    @Column(length = 50)
    private String Uid;

    @Column
    private LocalDate date;

    @Column
    private boolean finished;

    @Column
    private boolean ordering;


}
