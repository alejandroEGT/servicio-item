package com.springbootservicioitem.servicioitem.service;

import com.springbootservicioitem.servicioitem.models.Items;

import java.util.List;

public interface ItemService {
    public List<Items> findAll();
    public Items findBy(Long id, Integer cantidad);
}
