package com.ebms.domain.client.repository;

import com.ebms.domain.client.entity.Client;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByStatusOrderByClientNameAsc(String status);
    List<Client> findAllByOrderByStatusDescUpdatedAtDesc();
}
