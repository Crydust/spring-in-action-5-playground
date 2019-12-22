package com.example.demo.orders;

import com.example.demo.design.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private final SimpleJdbcInsert orderInserter;
    private final SimpleJdbcInsert orderTacoInserter;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbc) {
        orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("Taco_Order_Tacos");
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for (Taco taco : tacos) {
            saveTacoToOrder(taco, orderId);
        }
        return order;
    }

    private long saveOrderDetails(Order order) {
        Map<String, Object> values = Map.ofEntries(
                entry("deliveryName", order.getDeliveryName()),
                entry("deliveryStreet", order.getDeliveryStreet()),
                entry("deliveryCity", order.getDeliveryCity()),
                entry("deliveryState", order.getDeliveryState()),
                entry("deliveryZip", order.getDeliveryZip()),
                entry("ccNumber", order.getCcNumber()),
                entry("ccExpiration", order.getCcExpiration()),
                entry("ccCVV", order.getCcCVV()),
                entry("placedAt", order.getPlacedAt())
        );
        final long orderId = orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId) {
        Map<String, Object> values = Map.of(
                "tacoOrder", orderId,
                "taco", taco.getId()
        );
        orderTacoInserter.execute(values);
    }
}
