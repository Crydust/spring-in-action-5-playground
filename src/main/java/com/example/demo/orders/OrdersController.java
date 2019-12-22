package com.example.demo.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrdersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);

    private OrderRepository orderRepository;

    @Autowired
    public OrdersController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping("")
    public String processOrder(
            @Valid @ModelAttribute("order") Order order,
            Errors errors,
            SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        LOGGER.info("Order submitted: {}", order);
        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
