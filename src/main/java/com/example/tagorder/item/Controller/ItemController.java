package com.example.tagorder.item.Controller;


import com.example.tagorder.dto.ItemDto;
import com.example.tagorder.item.entity.Cart;
import com.example.tagorder.item.entity.Item;
import com.example.tagorder.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/api/items")
    public List<Item> getItems(){
        List<Item> items = itemRepository.findAll();
        //데이터베이스와 연동해서 동적으로 가져왔다.
        return items;
    }

    @PostMapping("/api/addItem")
    public ResponseEntity addItem(
            @RequestBody ItemDto dto
            ){

        Item item = new Item();

        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setImgPath(dto.getImg_path());

        itemRepository.save(item);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/item/{itemId}/{many}")
    public ResponseEntity setItemCount(
            @PathVariable("itemId") int itemId,
            @PathVariable("many") int many
    ) {

        Item item = itemRepository.findItemById(itemId);
        item.setMany(many);
        itemRepository.save(item);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/item/{itemId}")
    public ResponseEntity removeCartItem(
            @PathVariable("itemId") int itemId
    ) {

        Item item = itemRepository.findItemById(itemId);

        item.setMany(1);
        itemRepository.delete(item);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
