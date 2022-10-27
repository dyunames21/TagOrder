package com.example.tagorder.dto;


import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ItemDto {


    private String name;
    private int price;
    private MultipartFile uploadFile;
    private int many;
}
