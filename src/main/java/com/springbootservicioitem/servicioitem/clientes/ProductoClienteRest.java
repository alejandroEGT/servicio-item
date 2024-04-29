package com.springbootservicioitem.servicioitem.clientes;

import com.springbootservicioitem.servicioitem.models.Producto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "servicio-producto", fallback = CustomFeignConfiguration.class)
public interface ProductoClienteRest {
    @GetMapping("/listar")
    public List<Producto> listar();

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id);
}

