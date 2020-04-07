package br.com.cupuama.service.cashflow;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.cashflow.DocumentType;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.repository.DocumentTypeRepository;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some documentType specific things.
 * <p/>
 */
@Service
public class DefaultDocumentTypeService implements DocumentTypeService {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultDocumentTypeService.class);

	private final DocumentTypeRepository documentTypeRepository;

	public DefaultDocumentTypeService(final DocumentTypeRepository documentTypeRepository) {
		this.documentTypeRepository = documentTypeRepository;
	}

	/**
	 * Selects a documentType by id.
	 *
	 * @param documentTypeId
	 * @return found documentType
	 * @throws EntityNotFoundException if no documentType with the given id was found.
	 */
	@Override
	public DocumentType find(Long documentTypeId) throws EntityNotFoundException {
		return findDocumentTypeChecked(documentTypeId);
	}

	/**
	 * Creates a new documentType.
	 *
	 * @param documentTypeDO
	 * @return
	 * @throws ConstraintsViolationException if a documentType already exists with the
	 *                                       given id, ... .
	 */
	@Override
	public DocumentType create(DocumentType documentTypeDO) throws ConstraintsViolationException {
		DocumentType documentType;
		try {
			documentType = documentTypeRepository.save(documentTypeDO);
		} catch (DataIntegrityViolationException e) {
			LOG.warn("ConstraintsViolationException while creating a documentType: {}", documentTypeDO, e);
			throw new ConstraintsViolationException(e.getMessage());
		}
		return documentType;
	}

	/**
	 * Deletes an existing documentType by id.
	 *
	 * @param documentTypeId
	 * @throws EntityNotFoundException if no documentType with the given id was found.
	 */
	@Override
	@Transactional
	public void delete(Long documentTypeId) throws EntityNotFoundException {
		DocumentType documentTypeDO = findDocumentTypeChecked(documentTypeId);
		documentTypeDO.setDeleted(true);
	}

	/**
	 * Update the location for a documentType.
	 *
	 * @param documentTypeId
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 */
	@Override
	@Transactional
	public void update(long documentTypeId, String name) throws EntityNotFoundException {
		DocumentType documentType = findDocumentTypeChecked(documentTypeId);
		documentType.setName(name);
	}

	/**
	 * Find all documentTypes.
	 *
	 */
	@Override
	public List<DocumentType> findAll() {
		List<DocumentType> list = new ArrayList<>();
		documentTypeRepository.findAll().forEach(c -> {
			list.add(c);
		});
		return list;
	}

	/**
	 * Find all documentTypes by name.
	 *
	 * @param name
	 */
	@Override
	public List<DocumentType> findByName(String name) {
		return documentTypeRepository.findByNameLike(name + "%");
	}

	private DocumentType findDocumentTypeChecked(Long documentTypeId) throws EntityNotFoundException {
		return documentTypeRepository.findById(documentTypeId)
				.orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + documentTypeId));
	}

}
