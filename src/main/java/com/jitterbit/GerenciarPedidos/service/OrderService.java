package com.jitterbit.GerenciarPedidos.service;

import com.jitterbit.GerenciarPedidos.dto.PedidoDto;
import com.jitterbit.GerenciarPedidos.entities.Items;
import com.jitterbit.GerenciarPedidos.entities.Order;
import com.jitterbit.GerenciarPedidos.repositories.ItemsRepository;
import com.jitterbit.GerenciarPedidos.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ItemsRepository itemsRepository;

    @Transactional(readOnly = true)
    public List<PedidoDto> findAll(){
        List<Order> result = repository.findAll();
        return result.stream().map(PedidoDto::new).toList();
    }

    @Transactional
    public PedidoDto insert(PedidoDto dto){
        Order entity = new Order();
        CopyDtoToEntity(dto,entity);
        for (Items i: entity.getItems()){
            itemsRepository.save(i);
        }
        entity = repository.save(entity);
        for (Items i: entity.getItems()){
            itemsRepository.save(i.setOrder(entity););
        }
        return new PedidoDto(entity);
    }

    private void CopyDtoToEntity(PedidoDto dto, Order entity) {
        entity.setOrderId(dto.getNumeroPedido());
        entity.setOrderValue(dto.getValorTotal());
        entity.setCreationDate(dto.getDataCriacao());
        entity.setItems(dto.getItems().stream().map(x-> new Items(x.getIdItem(),x.getQuantidadeItem(),x.getValorItem(),null)).toList());
    }

}
