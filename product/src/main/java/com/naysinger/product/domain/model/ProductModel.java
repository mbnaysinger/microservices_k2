package com.naysinger.product.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductModel {
    Long id;
    String name;
    String model;
    Double price;

    //Exemplo de regra aplicada na camada de modelo de domínio
    public double aplicarDesconto(double percentage) {
        return price - (price * percentage / 100);
    }
}