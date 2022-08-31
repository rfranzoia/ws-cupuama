package br.com.cupuama.services.persons;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.controller.persons.dto.SupplierDTO;
import br.com.cupuama.controller.persons.mapper.PersonMapper;
import br.com.cupuama.controller.persons.mapper.SupplierMapper;
import br.com.cupuama.domain.persons.Address;
import br.com.cupuama.domain.persons.Supplier;
import br.com.cupuama.domain.persons.repository.SupplierRepository;
import br.com.cupuama.exception.ConstraintsViolationException;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.util.DefaultService;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some Supplier specific things.
 * <p/>
 */
@Service
public class SupplierService extends DefaultService<Supplier, Long> {

	public SupplierService(final SupplierRepository supplierRepository) {
		super(supplierRepository);
	}

	@Transactional
	public SupplierDTO create(SupplierDTO dto) throws ConstraintsViolationException {
		Supplier supplier = SupplierMapper.makeSupplier(dto);
		return SupplierMapper.makeDTO(create(supplier));
	}
	
	/**
	 * Update Supplier information
	 *
	 * @param SupplierId
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(final Long supplierId, final SupplierDTO dto) throws EntityNotFoundException {
		final Address address = new Address(dto.getAddress().getStreet(),
				dto.getAddress().getCity(), dto.getAddress().getRegion(), dto.getAddress().getPostalCode(), dto.getAddress().getCountry());
		
		Supplier supplier = findByIdChecked(supplierId);
		supplier.setPerson(PersonMapper.makePerson(dto.getPerson()));
		supplier.setCompanyName(dto.getCompanyName());
		supplier.setPhone(dto.getPhone());
		supplier.setAddress(address);
		supplier.getAudit().setDateUpdated(ZonedDateTime.now());
	}

	/**
	 * Find all Suppliers by name.
	 *
	 * @param name
	 */
	public List<Supplier> findByName(final String name) {
		return ((SupplierRepository) repository).findByPersonFirstName(name + "%");
	}

}
