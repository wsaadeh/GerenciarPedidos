package com.jitterbit.GerenciarPedidos.dto;

import com.jitterbit.GerenciarPedidos.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDto {
  private String numeroPedido;
  private double valorTotal;
  private OffsetDateTime dataCriacao;
  private List<ItemsDto> items;

  public PedidoDto(Order entity){
    this.numeroPedido = entity.getOrderId();
    this.valorTotal = entity.getOrderValue();
    this.dataCriacao = entity.getCreationDate();
    this.items = entity.getItems().stream().map(ItemsDto::new).toList();
  }
}
