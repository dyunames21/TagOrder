package com.example.tagorder.item.Controller;


import com.example.tagorder.dto.ItemDto;
import com.example.tagorder.item.entity.Cart;
import com.example.tagorder.item.entity.Item;
import com.example.tagorder.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class ItemController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

        itemRepository.save(item);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/file")
    public String getFile(Model model){
        return "/files/file";
    }


    @PostMapping(value = "/api/addItemSet")
    public String postFile(
            @RequestParam MultipartFile uploadFile,
            RedirectAttributes redirectAttributes,
            @RequestParam int price,
            @RequestParam String name,
            HttpServletRequest request
    ) throws IOException {



        Item item = new Item();

        item.setName(name);
        item.setPrice(price);
        HttpSession session = request.getSession();
        String root_path = session.getServletContext().getRealPath("/");


        logger.info("uploadFile: {}",uploadFile);
        System.out.println(uploadFile.getOriginalFilename());
        System.out.println(uploadFile.getName());


        String fileName = uploadFile.getOriginalFilename();
        String uploadPath =root_path + fileName;
        File destinationFile = new File(uploadPath);
        System.out.println(uploadPath);
        destinationFile.getParentFile().mkdir();
        uploadFile.transferTo(destinationFile);

        item.setImgpath(uploadPath);

        String message =fileName + "is upload";
        redirectAttributes.addFlashAttribute("message",message);

        Logger log = LoggerFactory.getLogger(getClass());

        itemRepository.save(item);

        return "redirect:/file";
    }




    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("/file/{filename}")
    public ResponseEntity fileDownload(@PathVariable("filename") String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + fileName);
        File file = resource.getFile();
        String fileLen = file.length()+"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .header(HttpHeaders.CONTENT_LENGTH, fileLen)
                .body(resource);
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
