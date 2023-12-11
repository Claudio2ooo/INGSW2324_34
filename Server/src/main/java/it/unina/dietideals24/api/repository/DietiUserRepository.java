package it.unina.dietideals24.api.repository;

import it.unina.dietideals24.api.model.DietiUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietiUserRepository extends JpaRepository<DietiUser, String> {

}
