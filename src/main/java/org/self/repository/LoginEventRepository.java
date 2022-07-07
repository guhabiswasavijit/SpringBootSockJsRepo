package org.self.repository;

import org.self.model.LoginEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginEventRepository extends JpaRepository<LoginEvent, Long>{

}
