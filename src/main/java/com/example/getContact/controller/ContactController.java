package com.example.getContact.controller;

import com.example.getContact.entity.Contact;
import com.example.getContact.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contacts")

public class ContactController {

    private final ContactRepository repository;

    public ContactController(ContactRepository repository){
        this.repository = repository;
    }

    // 1. Добавление
    @PostMapping
    public ResponseEntity<Contact> addContact(@Valid @RequestBody Contact contact) {
        return ResponseEntity.ok(repository.save(contact));
    }

    // 2. Просмотр списка(Пагинация)
    @GetMapping
    public ResponseEntity<Page<Contact>> getAllContacts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        List<String> allowedSortFields = List.of("id", "name", "phone", "email");
        if (!allowedSortFields.contains(sort)) {
            sort = "id";
        }

        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        Specification<Contact> spec = Specification.allOf();

        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (phone != null && !phone.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(root.get("phone"), "%" + phone + "%"));
        }

        if (email != null && !email.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }

        Page<Contact> contacts = repository.findAll(spec, pageable);

        return ResponseEntity.ok(contacts);
    }

    // 3. Просмотр списка
    @GetMapping("/{id}")
    public Optional<Contact> getContactsById(@PathVariable Long id){
        return repository.findById(id);
    }

    // 4. Обновление
    @PutMapping("/{id}")
    public Contact updateContact(@PathVariable Long id, @RequestBody Contact updatedContact){
        return repository.findById(id).map(contact -> {
            contact.setName(updatedContact.getName());
            contact.setPhone(updatedContact.getPhone());
            contact.setEmail(updatedContact.getEmail());
            return repository.save(contact);
        }).orElseGet(() -> repository.save(updatedContact));
    }

    //6. Delete
    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id){
        repository.deleteById(id);
    }

    // 7. Searching
    @GetMapping("/search")
    public List<Contact> searchContacts(@RequestParam String query){
        return repository.findByNameContainingIgnoreCaseOrPhoneContaining(query,query);
    }

}
