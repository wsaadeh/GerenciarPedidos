package com.jitterbit.GerenciarPedidos.service;

import com.jitterbit.GerenciarPedidos.dto.ItemsDto;
import com.jitterbit.GerenciarPedidos.dto.PedidoDto;
import com.jitterbit.GerenciarPedidos.entities.Items;
import com.jitterbit.GerenciarPedidos.entities.Order;
import com.jitterbit.GerenciarPedidos.repositories.ItemsRepository;
import com.jitterbit.GerenciarPedidos.repositories.OrderRepository;
import com.jitterbit.GerenciarPedidos.service.exceptions.DatabaseException;
import com.jitterbit.GerenciarPedidos.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Transactional(readOnly = true)
    public PedidoDto findById(String id){
        Order result = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id not found"));
        return new PedidoDto(result);
    }

    @Transactional
    public PedidoDto insert(PedidoDto dto){
        Order entity = new Order();
        entity.setOrderId(dto.getNumeroPedido());
        CopyDtoToEntity(dto,entity);
        //Salva ordem
        entity = repository.save(entity);

        return new PedidoDto(entity);
    }

    @Transactional
    public PedidoDto update(String id, PedidoDto dto){
        try {
            Order entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id not found"));
            CopyDtoToEntity(dto,entity);
            for (Items i: entity.getItems()){
                i.setOrder(entity);
                itemsRepository.save(i);
            }
            entity = repository.save(entity);
            return new PedidoDto(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found");
        }
    }

    @Transactional
    public void delete(String id){
        if (!repository.existsById(id)){
            throw new ResourceNotFoundException("Id not found");
        }
        try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation.");
        }
    }

    private void CopyDtoToEntity(PedidoDto dto, Order entity) {
        entity.setOrderValue(dto.getValorTotal());
        entity.setCreationDate(dto.getDataCriacao());
        entity.getItems().clear();
        for (ItemsDto i: dto.getItems()){
            Items items = new Items();
            items.setProductId(i.getIdItem());
            items.setPrice(i.getValorItem());
            items.setQuantity(i.getQuantidadeItem());
            items.setOrder(null);
            entity.addItem(items);
        }
    }

}
