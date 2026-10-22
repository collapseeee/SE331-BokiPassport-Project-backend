package se331.bokipassport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.bokipassport.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
