package com.springbootservicioitem.servicioitem.service;

import com.springbootservicioitem.servicioitem.models.Items;
import com.springbootservicioitem.servicioitem.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("serviceTemplate")
public class ItemServiceImpl implements ItemService{
    @Autowired
    private RestTemplate clienteRest;

    @Override
    public List<Items> findAll() {
        List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://localhost:8085/listar", Producto[].class));
        return productos.stream().map(p->new Items(p,1)).collect(Collectors.toList());
    }

    @Override
    public Items findBy(Long id, Integer cantidad) {
        Map<String, String> pathVariable = new HashMap<>();
        pathVariable.put("id", id.toString());
        Producto producto = clienteRest.getForObject("http://localhost:8085/ver/{id}", Producto.class, pathVariable);
        return new Items(producto, cantidad);
    }
}
