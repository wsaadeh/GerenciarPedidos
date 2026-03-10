package com.jitterbit.GerenciarPedidos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table( name = "tb_order")
public class Order {
    @Id
    @Column(length = 20)
    private String orderId;
    private double orderValue;
    private OffsetDateTime creationDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Items> items = new ArrayList<>();

    public void addItem(Items item){
        items.add(item);
        item.setOrder(this);
    }

}
