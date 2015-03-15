package com.talentroc.t5.interview.services;

import com.talentroc.t5.interview.entities.Contact;
import com.talentroc.t5.interview.utils.BusinessException;
import org.apache.tapestry5.jpa.annotations.CommitAfter;

import javax.persistence.EntityManager;
import java.util.List;

public class ContactManagerImpl implements ContactManager {

    private final EntityManager entityManager;

    public ContactManagerImpl(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void validate(final Contact contact) throws BusinessException {
        if (contact.getFirstName().length() > 50){
				throw new BusinessException("Taille de prénom trop long");
		}
		else if(contact.getLastName() == null || contact.getLastName().length() < 3 || contact.getLastName().length() > 50 ){
				throw new BusinessException("Taille de nom invalide");
		}
		else if(contact.getTelephone().length() != 10 || !contact.getTelephone().matches("[0-9]*")){
				throw new BusinessException("numéro de telephone invalide");
		}
		
    }

    @Override
    @CommitAfter
    public void create(final Contact contact) throws BusinessException {
        if (contact.getId() == null) {
            validate(contact);
            entityManager.persist(contact);
        } else {
            throw new BusinessException("This contact already exists in DB. Use update.");
        }
    }

    @Override
    @CommitAfter
    public void update(final Contact contact) throws BusinessException {
        if (contact.getId() == null) {
            throw new BusinessException("This contact does not exist in DB. Use create.");
        } else {
            validate(contact);
            entityManager.merge(contact);
        }
    }

    @Override
    @CommitAfter
    public void delete(final Contact contact) throws BusinessException {
        entityManager.remove(contact);
    }

    @Override
    public List<Contact> retrieveAll() {
        return entityManager.createNamedQuery(Contact.RETRIEVE_ALL).getResultList();
    }

    @Override
    public Contact retrieveById(final Long id) {
        return entityManager.find(Contact.class, id);
    }
}
