package com.jitterbit.GerenciarPedidos.controller;

import com.jitterbit.GerenciarPedidos.dto.PedidoDto;
import com.jitterbit.GerenciarPedidos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping
    public ResponseEntity<List<PedidoDto>> findAll(){
        List<PedidoDto> dto = service.findAll();
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PedidoDto> insert(@RequestBody PedidoDto dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getNumeroPedido())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}
