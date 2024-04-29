package com.springbootservicioitem.servicioitem.controller;

import com.netflix.discovery.converters.Auto;
import com.springbootservicioitem.servicioitem.models.Items;
import com.springbootservicioitem.servicioitem.service.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ItemController {

    @Autowired
    private CircuitBreakerFactory cbFactory;


    @Autowired
    private Environment env;

    @Autowired
    @Qualifier("serviceFeing")
    private ItemService itemService;

    @Value("${configuracion.texto}")
    private String texto;

    public ItemController(CircuitBreakerFactory cbFactory) {
        this.cbFactory = cbFactory;
    }


    @GetMapping("/listar")
    public List<Items> findAll(){
        System.out.println("::::CORREEEEEEEE:::::::::::");
        System.out.println(itemService.findAll());
        return itemService.findAll();
    }

    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Items detalle(@PathVariable Long id, @PathVariable Integer cantidad){
        System.out.println("ID::" + id + "--CANT:::"+ cantidad);
       return cbFactory.create("items")
                .run(()->itemService.findBy(id, cantidad));
    }

    @CircuitBreaker(name="test-api", fallbackMethod = "metodoFalla")
    @GetMapping("/ver2/{id}/cantidad/{cantidad}")
    public ResponseEntity<Items> detalle2(@PathVariable Long id, @PathVariable Integer cantidad){
        System.out.println("2ID::" + id + "--2CANT:::"+ cantidad);
        try{
            System.out.println("TRYY:::::::::::");
            return ResponseEntity.accepted().body(itemService.findBy(id, cantidad));
        }catch (HttpMessageNotWritableException e) {
            System.out.println("catch:::::::::::");
            return ResponseEntity.badRequest().body(new Items());
        }
    }

    public ResponseEntity<Items> metodoFalla(Exception exception){
        System.out.println("NADA DE NADA????");
        return ResponseEntity.badRequest().body(new Items());
    }

    @GetMapping("/obtener-config")
    public ResponseEntity<?> getConfig(@Value("${server.port}") String puerto){
        Map<String, String> json = new HashMap<>();
        json.put("texto", "texto");
        json.put("puerto", puerto);

        if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")){
            json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
            json.put("autor.email", env.getProperty("configuracion.autor.email"));
        }

        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }
}
