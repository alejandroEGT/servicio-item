package com.springbootservicioitem.servicioitem.clientes;

import com.springbootservicioitem.servicioitem.models.Producto;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import org.springframework.cloud.openfeign.FallbackFactory;

public class CustomFeignConfiguration implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        System.out.println("ERROR DE MIERDAAAAAAA_::::: " + response.status());
        switch (response.status()){
            case 400:
                return new Exception("Exception while getting product details");
            case 404:
                return new Exception("Exception while getting product details");
            case 503:
                return new Exception("Exception while getting product details");
            default:
                return new Exception("Exception while getting product details");
        }
    }
}
