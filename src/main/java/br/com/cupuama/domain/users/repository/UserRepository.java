package br.com.cupuama.domain.users.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.users.User;

/**
 * Repository interface for User table
 * <p/>
 */
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findByNameLike(String name);

}
