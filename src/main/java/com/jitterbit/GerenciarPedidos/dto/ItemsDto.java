package com.jitterbit.GerenciarPedidos.dto;

import com.jitterbit.GerenciarPedidos.entities.Items;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemsDto {
    private Long idItem;
    private int quantidadeItem;
    private double valorItem;

    public ItemsDto(Items entity){
        this.idItem = entity.getProductId();
        this.quantidadeItem = entity.getQuantity();
        this.valorItem = entity.getPrice();
    }

}
