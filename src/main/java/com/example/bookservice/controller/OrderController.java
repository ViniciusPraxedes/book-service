package com.example.bookservice.controller;

import com.example.bookservice.service.OrderService;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-checkout-session")
    @ResponseBody
    public RedirectView createCheckoutSession(@RequestParam String price) throws StripeException {

        return orderService.createOrder(price);
    }

}