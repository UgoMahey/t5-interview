package com.talentroc.t5.interview.pages.contact;


import com.talentroc.t5.interview.entities.Contact;
import com.talentroc.t5.interview.services.ContactManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import com.talentroc.t5.interview.utils.BusinessException;
import java.util.List;

public class ContactEdit {

    @Property
    private Contact contact;
	
	@Inject
	private ContactManager contactManager;
	private boolean maj;
	
	public void onSelectedFromMaj (){maj = true;}
	
	public Object onSuccess() {
		if (!maj){
			try {
				contactManager.create(contact);	
			}
			catch (BusinessException e){
				e.getMessage();
				
			}
		}
		else {
			try {
				contactManager.update(contact);
			}
			catch(BusinessException e){
				e.getMessage();
			}
		}
		return ContactIndex.class;
	}
	 
    public void onActivate() {
        contact = new Contact();
    }

    public Boolean onActivate(Contact contact) {
        this.contact = contact;
        return Boolean.TRUE;
    }
	public List<Contact> getContacts() {
        return contactManager.retrieveAll();
    }
	
}
