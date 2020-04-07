package br.com.cupuama.service.cashflow;

import java.util.List;

import br.com.cupuama.domain.cashflow.DocumentType;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;

public interface DocumentTypeService {

    DocumentType find(Long documentTypeId) throws EntityNotFoundException;

    DocumentType create(DocumentType documentTypeDO) throws ConstraintsViolationException;

    void delete(Long documentTypeId) throws EntityNotFoundException;

    void update(long documentTypeId, String name) throws EntityNotFoundException;

    List<DocumentType> findByName(String name);

    List<DocumentType> findAll();

}