package com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.out.persistence;

import com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.out.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
