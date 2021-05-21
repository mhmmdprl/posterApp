package com.esandmongodb.posterapp.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esandmongodb.posterapp.entity.Author;
import com.esandmongodb.posterapp.repository.AuthorRepository;
import com.esandmongodb.posterapp.service.AuthorService;

@Service(value = "authorService")
@Transactional
public class AuthorServiceImpl implements AuthorService, UserDetailsService {

	@Autowired
	private MongoOperations mongoOperations;
	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public Author save(Author document) {
		return this.authorRepository.save(document);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Author author = this.authorRepository.findByUsername(username);
		if (author == null) {
			throw new UsernameNotFoundException("Invalid username, password or you are banned");
		}
		return new User(author.getUsername(), author.getPassword(), this.getAuthorities(author));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Author author) {

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		author.getRoles().forEach(role -> {

			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});

		return authorities;
	}

	@Override
	public Author findByUsername(String username) {
		return this.authorRepository.findByUsername(username);
	}

	@Override
	public List<Author> findAllActive() {
		
		return this.authorRepository.findAllActive();
	}

	@Override
	public boolean existsByUsername(String username) {

		return this.authorRepository.existsByUsername(username);
	}

	@Override
	public boolean existsByUuid(String uuid) {

		return this.authorRepository.existsByUuid(uuid);
	}

	@Override
	public boolean delete(String uuid, Long updatedBy) {

		Query query = new Query(Criteria.where("uuid").is(uuid));
		Update update = new Update().set("updatedBy", updatedBy).set("deleted", '1').set("updatedDate", new Date());

		try {
			this.mongoOperations.findAndModify(query, update, Author.class);
			return true;
		} catch (Exception e) {

			System.out.println("Hata : " + e);
		}

		return false;
	}

	@Override
	public Author findByUuid(String uuid) {
		return this.authorRepository.findByUuid(uuid);
	}

	@Override
	public boolean ban(String uuid, Long updatedBy) {
		Query query = new Query(Criteria.where("uuid").is(uuid));
		Update update = new Update().set("updatedBy", updatedBy).set("banStatus", '1').set("updatedDate", new Date());

		try {
			this.mongoOperations.findAndModify(query, update, Author.class);
			return true;
		} catch (Exception e) {

			System.out.println("Hata : " + e);
		}

		return false;
	}

	@Override
	public List<Author> findAll() {
		return this.authorRepository.findAll();
	}

	@Override
	public List<Author> findAllPassive() {
		return this.authorRepository.findAllPassive();
	}

	@Override
	public Author findById(Long id) {
		return this.authorRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Long id) {
		return this.authorRepository.existsById(id);
	}

	@Override
	public List<Author> findSpecific(Query query, Pageable sortedByName) {
		List<Author> authors=null;
		try {
			query.with(sortedByName);
			authors=	 this.mongoOperations.find(query, Author.class);
		} catch (Exception e) {
			throw e;
		}
		return authors;
	}

	@Override
	public Author findByEmail(String email) {
		return this.authorRepository.findByEmail(email);
	}

}
