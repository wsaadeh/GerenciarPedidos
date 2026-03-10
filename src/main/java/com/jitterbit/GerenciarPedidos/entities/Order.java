package com.jitterbit.GerenciarPedidos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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

    @OneToMany(mappedBy = "order")
    private List<Items> items;

}
