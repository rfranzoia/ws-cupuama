package br.com.cupuama.domain.users.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.users.User;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findByNameLike(String name);

}
