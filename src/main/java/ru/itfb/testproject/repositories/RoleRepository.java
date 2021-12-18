package ru.itfb.testproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.testproject.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
