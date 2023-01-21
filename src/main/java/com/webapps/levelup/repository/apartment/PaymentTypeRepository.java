package com.webapps.levelup.repository.apartment;

import com.webapps.levelup.model.apartment.PaymentTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTypeRepository extends CrudRepository<PaymentTypeEntity, Integer> {
    List<PaymentTypeEntity> findAll();
}
