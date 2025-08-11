package com.example.getContact.repository;

import com.example.getContact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long>, JpaSpecificationExecutor<Contact> {
    //for search
    List<Contact> findByNameContainingIgnoreCaseOrPhoneContaining(String name, String phone);

}
