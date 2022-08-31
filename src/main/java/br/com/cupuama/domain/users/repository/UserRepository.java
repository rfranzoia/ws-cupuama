package br.com.cupuama.domain.users.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.cupuama.domain.users.Users;

/**
 * Repository interface for User table
 * <p/>
 */
public interface UserRepository extends CrudRepository<Users, String> {

    List<Users> findByPersonFirstNameLike(String name);

}
