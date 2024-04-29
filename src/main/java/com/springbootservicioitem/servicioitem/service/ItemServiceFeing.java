package com.springbootservicioitem.servicioitem.service;

import com.springbootservicioitem.servicioitem.clientes.ProductoClienteRest;
import com.springbootservicioitem.servicioitem.models.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("serviceFeing")
@Primary
public class ItemServiceFeing implements ItemService{

    @Autowired
    private ProductoClienteRest clienteFeing;
    @Override
    public List<Items> findAll() {
        System.out.println("VENGAAAAAA:::::::");
        System.out.println(clienteFeing.listar().get(1).toString());
        return clienteFeing.listar().stream().map(p-> new Items(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Items findBy(Long id, Integer cantidad) {
        System.out.println("HOLAAAAAAAAA_____");
        try{
            System.out.println("triiiii");
            return new Items(clienteFeing.detalle(id), 1);
        }catch(Exception e){
            System.out.println("caxxx");
            return new Items();
        }
    }
}
