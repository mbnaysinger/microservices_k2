package com.naysinger.product.repository;

import com.naysinger.product.entity.ClientCredential;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientCredentialRepository extends CrudRepository<ClientCredential, Integer> {

    Optional<ClientCredential> findByApiKey(String apiKey);
}