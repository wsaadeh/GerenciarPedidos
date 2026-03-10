package com.jitterbit.GerenciarPedidos.repositories;

import com.jitterbit.GerenciarPedidos.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {

}
