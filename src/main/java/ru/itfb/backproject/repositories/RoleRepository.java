package ru.itfb.backproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.backproject.entity.Role;

/**
 * Репозиторий для {@link Role}
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
