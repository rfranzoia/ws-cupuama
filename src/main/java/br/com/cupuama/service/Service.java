package br.com.cupuama.service;

import java.util.List;

import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

public interface Service<T, ID> {

    T find(ID id) throws EntityNotFoundException;

    T create(T t) throws ConstraintsViolationException;

    void delete(ID id) throws EntityNotFoundException;

    List<T> findAll();

}