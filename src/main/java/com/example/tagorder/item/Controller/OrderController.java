package com.example.tagorder.item.Controller;


import com.example.tagorder.dto.OrderDto;
import com.example.tagorder.item.entity.Cart;
import com.example.tagorder.item.entity.Item;
import com.example.tagorder.item.entity.Order;
import com.example.tagorder.item.entity.newOrder;
import com.example.tagorder.item.repository.CartRepository;
import com.example.tagorder.item.repository.ItemRepository;
import com.example.tagorder.item.repository.OrderRepository;
import com.example.tagorder.item.repository.newOrderRepository;
import com.example.tagorder.item.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderController {

    @Autowired
    JwtService jwtService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    newOrderRepository newOrderRepository;

    @Autowired
    CartRepository cartRepository;




    @Transactional
    @PostMapping("/api/order")
    public ResponseEntity nowOrder(
            @RequestBody OrderDto dto,
            @CookieValue(value = "token", required = false) String token
    ) {

        int memberId = jwtService.getId(token);
        newOrder newOrder = new newOrder();
        LocalDate currentDate = LocalDate.now();

        newOrder.setMemberId(memberId);
        newOrder.setName(dto.getName()); //유효성검사도 해주면 좋긴한데...
        newOrder.setPhone(dto.getPhone());
        newOrder.setItems(dto.getItems());
        newOrder.setUid(dto.getUid());
        newOrder.setDate(currentDate);
        newOrder.setFinished(false);
        newOrder.setOrdering(false);
        //사용자가 입력한 내용을 Dto 에 담아서 새로운 오브젝트 만들어


        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/api/orders")
    public ResponseEntity getOrders(
            @CookieValue(value = "token", required = false) String token
    ) {
        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }


        int memberId = jwtService.getId(token);
        List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);

        return new ResponseEntity<>(orders, HttpStatus.OK);
        //orderRepository 내에 있던 내용을 인자값으로 전달해준다.
    }


    @GetMapping("/api/seller/orders")
    public ResponseEntity SellerGetOrders(
    ) {

        List<Order> orders = orderRepository.findAll();
        newOrderRepository.deleteAll();

        for(Order order:orders){
            newOrder neworder = new newOrder();
            if(order.isFinished() ){
            }else {
                if (order.isOrdering()){
                } else {

                    neworder.setMemberId(order.getMemberId());
                    neworder.setName(order.getName()); //유효성검사도 해주면 좋긴한데...
                    neworder.setPhone(order.getPhone());
                    neworder.setItems(order.getItems());
                    neworder.setUid(order.getUid());
                    neworder.setDate(order.getDate());
                    neworder.setOrdering(true);
                    newOrderRepository.save(neworder);

                }
            }

        }
        List<newOrder> ordering = newOrderRepository.findAll();

        return new ResponseEntity<>(ordering, HttpStatus.OK);
        //orderRepository 내에 있던 내용을 인자값으로 전달해준다.
    }


    @PostMapping("/api/seller/orders/{orderId}")
    public ResponseEntity ComOrders(
            @PathVariable("orderId") int orderId
    ) {

        newOrder neworder = newOrderRepository.findById(orderId);
        String orderUid =neworder.getUid();
        Order order = orderRepository.findByUid(orderUid);

        order.setFinished(true);
        neworder.setFinished(true);
        //사용자에게 주문이 완료 되었다는 데이터의 전달을
        //어떻게 할것인가?(매우 중요)

        newOrderRepository.delete(neworder);
        orderRepository.save(order);


        return new ResponseEntity<>(HttpStatus.OK);
        //orderRepository 내에 있던 내용을 인자값으로 전달해준다.
    }



    @Transactional
    @PostMapping("/api/orders")
    public ResponseEntity pushOrder(
            @RequestBody OrderDto dto,
            @CookieValue(value = "token", required = false) String token
    ) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Random random = new Random();
        int memberId = jwtService.getId(token);
        Order order = new Order();
        LocalDate currentDate = LocalDate.now();
        String uuid = UUID.randomUUID().toString();


        order.setMemberId(memberId);
        order.setName(dto.getName()); //유효성검사도 해주면 좋긴한데...
        order.setPhone(dto.getPhone());
        order.setItems(dto.getItems());
        order.setUid(uuid);
        order.setDate(currentDate);
        //사용자가 입력한 내용을 Dto 에 담아서 새로운 오브젝트 만들어

        //주문진행이 완료가 되면, 이뤄지면
        //SetIsOrdering True 로 바꾼뒤 저장
        orderRepository.save(order);
        //orderRepository 에 저장
        cartRepository.deleteByMemberId(memberId);


        return new ResponseEntity<>(HttpStatus.OK);
    }



}
