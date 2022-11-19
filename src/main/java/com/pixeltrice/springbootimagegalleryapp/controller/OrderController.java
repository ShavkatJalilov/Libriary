package com.pixeltrice.springbootimagegalleryapp.controller;

import com.pixeltrice.springbootimagegalleryapp.entity.BooksGallery;
import com.pixeltrice.springbootimagegalleryapp.entity.Orders;
import com.pixeltrice.springbootimagegalleryapp.entity.Users;
import com.pixeltrice.springbootimagegalleryapp.repository.BooksGalleryRepository;
import com.pixeltrice.springbootimagegalleryapp.repository.OrdersRepository;
import com.pixeltrice.springbootimagegalleryapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
@Autowired
    OrdersRepository ordersRepository;
@Autowired
    UsersRepository usersRepository;
@Autowired
    BooksGalleryRepository booksGalleryRepository;

    @PostMapping("/orderThis/{userId}/{kitobId}")
    public @ResponseBody
    ResponseEntity<?> saveOrder(@PathVariable Long userId, @PathVariable Long kitobId){
        Optional<Users> byId = usersRepository.findById(userId);
        Optional<BooksGallery> byId1 = booksGalleryRepository.findById(kitobId);
        Users users=byId.get();
        BooksGallery booksGallery=byId1.get();

        Orders orders=new Orders();
        orders.setNomi(booksGallery.getName());
        orders.setMuallif(booksGallery.getMuallif());
        orders.setIsmi(users.getUsername());
        orders.setPasport(users.getPasport());
        orders.setTelRaqam(users.getTelNomer());
        orders.setMuddati(booksGallery.getIjaraKuni());
        ordersRepository.save(orders);
        return new ResponseEntity<>("Buyurtma saqlandi", HttpStatus.OK);
    }

    @GetMapping("/orderPage/{userId}/{kitobId}")
    public String download(@PathVariable Long userId, @PathVariable Long kitobId, Model map){
        List<BooksGallery> list=booksGalleryRepository.findAll();
        for(BooksGallery i:list){
            if (i.getId().equals(kitobId)) map.addAttribute("OrderBy",i);
        }
        List<Users> users = usersRepository.findAll();
        for (Users b:users){
            if (b.getId().equals(userId)) map.addAttribute("users",b);
        }
        return "About/orderPage";
    }


    @GetMapping("/booksOrder/{id}")
    String orderbooks(Model map, @PathVariable Long id){
        List<BooksGallery> books = booksGalleryRepository.findAll();
        map.addAttribute("ijara",books);

        System.out.println(id);
        List<Users> all = usersRepository.findAll();
        for(Users i:all){
            if (i.getId().equals(id)) map.addAttribute("order",i);
        }
        return "About/booksOrder";
    }


    @GetMapping("/display/{id}")
    @ResponseBody
    void showbook(@PathVariable("id") Long id, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<BooksGallery> byId = booksGalleryRepository.findById(id);
        if (byId.isPresent()){
            BooksGallery booksGallery=byId.get();
            response.setContentType(booksGallery.getBooksGalleryByte().getContentType());
            response.getOutputStream().write(booksGallery.getBooksGalleryByte().getFileByte());
            response.getOutputStream().close();
        }
    }
    /////////////////////////start admin panel/////////////

    @GetMapping("/Buyurtmalar") public String buyurtma(Model map){
        List<Orders> ordersList = ordersRepository.findAll();
        for(Orders i:ordersList){
            if (i.isOlibKetdi()==false && i.isQabulQilindi()==false) map.addAttribute("orders",i);
        }
        return "AdminPanel/Buyurtmalar";}

    ////////////////////////end admin panel////////////////


    @PutMapping("/output/{id}")
    public ResponseEntity<?> updateKitob(@PathVariable Long id){
        Optional<Orders> byId = ordersRepository.findById(id);
        if (byId.isPresent()){
            Orders orders=byId.get();
            LocalDateTime now=LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String[] b=orders.getMuddati().split(" ");
            String kuni=b[0];
            String newData=String.valueOf(LocalDate.now());
            LocalDate date= LocalDate.parse(newData);
            LocalDate kun=date.plusDays(Long.parseLong(kuni));
            System.out.println(kun);


            String formatDateTime = now.format(formatter);
            orders.setBerilganSana(formatDateTime);
            orders.setOlibKetdi(true);


            ordersRepository.save(orders);
            return new ResponseEntity<>("Ma'lumot taxrirlandi",HttpStatus.OK);
        }
        return new ResponseEntity<>("Maqola ma'lumotlari topilmadi - ", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/input/{id}")
    public ResponseEntity<?> updateInput(@PathVariable Long id){
        Optional<Orders> byId = ordersRepository.findById(id);
        if (byId.isPresent()){
            Orders orders=byId.get();
            LocalDateTime now=LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formatDateTime = now.format(formatter);
            orders.setOlibKelishMudadti(formatDateTime);
            orders.setQabulQilindi(true);
            ordersRepository.save(orders);
            return new ResponseEntity<>("Ma'lumot taxrirlandi",HttpStatus.OK);
        }
        return new ResponseEntity<>("Maqola ma'lumotlari topilmadi - ", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/accertBook")
    String accertpBook(Model map){
        List<Orders> all = ordersRepository.findAll();
        for(Orders i:all){
            if (i.isOlibKetdi()==true && i.isQabulQilindi()==false) map.addAttribute("orders",i);
        }
        return "AdminPanel/KitobQabul";
    }
    @GetMapping("/archive")
    String archiveBook(Model map){
        List<Orders> all = ordersRepository.findAll();
        for(Orders i:all){
            if (i.isOlibKetdi()==true && i.isQabulQilindi()==true) map.addAttribute("orders",i);
        }
        return "AdminPanel/Arxiv";
    }

}
