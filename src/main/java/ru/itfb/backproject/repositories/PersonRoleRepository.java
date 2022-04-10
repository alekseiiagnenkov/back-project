package ru.itfb.backproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itfb.backproject.entity.PersonRole;

/**
 * Репозиторий для {@link PersonRole}
 */
@Repository
public interface PersonRoleRepository extends JpaRepository<PersonRole, Long> {
}
