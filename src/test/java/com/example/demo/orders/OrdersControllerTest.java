package com.example.demo.orders;

import com.example.demo.users.User;
import org.junit.jupiter.api.Test;

import static com.example.demo.orders.OrdersController.copyDeliveryInformationFromUserToOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

class OrdersControllerTest {

    User user = new User("password", "username", "fullname", "street", "city", "state", "zip", "phoneNumber");

    @Test
    void shouldCopyName() {
        Order order = new Order();
        copyDeliveryInformationFromUserToOrder(user, order);
        assertThat(order.getDeliveryName(), is("fullname"));
    }

    @Test
    void shouldCopyStreet() {
        Order order = new Order();
        copyDeliveryInformationFromUserToOrder(user, order);
        assertThat(order.getDeliveryStreet(), is("street"));
    }

    @Test
    void shouldCopyCity() {
        Order order = new Order();
        copyDeliveryInformationFromUserToOrder(user, order);
        assertThat(order.getDeliveryCity(), is("city"));
    }

    @Test
    void shouldCopyZip() {
        Order order = new Order();
        copyDeliveryInformationFromUserToOrder(user, order);
        assertThat(order.getDeliveryZip(), is("zip"));
    }

    @Test
    void shouldCopyState() {
        Order order = new Order();
        copyDeliveryInformationFromUserToOrder(user, order);
        assertThat(order.getDeliveryState(), is("state"));
    }

    @Test
    void shouldNotOverwriteName() {
        Order order = new Order();
        order.setDeliveryName("keep me");

        copyDeliveryInformationFromUserToOrder(user, order);

        assertThat(order.getDeliveryName(), is("keep me"));
    }

    @Test
    void shouldNotOverwriteStreet() {
        Order order = new Order();
        order.setDeliveryStreet("keep me");

        copyDeliveryInformationFromUserToOrder(user, order);

        assertThat(order.getDeliveryStreet(), is("keep me"));
    }

    @Test
    void shouldNotOverwriteCity() {
        Order order = new Order();
        order.setDeliveryCity("keep me");

        copyDeliveryInformationFromUserToOrder(user, order);

        assertThat(order.getDeliveryCity(), is("keep me"));
    }

    @Test
    void shouldNotOverwriteZip() {
        Order order = new Order();
        order.setDeliveryZip("keep me");

        copyDeliveryInformationFromUserToOrder(user, order);

        assertThat(order.getDeliveryZip(), is("keep me"));
    }

    @Test
    void shouldNotOverwriteState() {
        Order order = new Order();
        order.setDeliveryState("keep me");

        copyDeliveryInformationFromUserToOrder(user, order);

        assertThat(order.getDeliveryState(), is("keep me"));
    }
}