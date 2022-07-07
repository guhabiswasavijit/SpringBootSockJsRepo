package org.self.service;

import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.self.model.User;
import org.self.repository.RoleRepository;
import org.self.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService, UserDetailsService {
    private RoleRepository roleRepository;
	private UserRepository repository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(RoleRepository roleRepository,UserRepository repository, PasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}
    @Override
    @Transactional(propagation = Propagation.REQUIRED,readOnly = false)
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        repository.save(user);
    }
	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly = false)
	public User createNewUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(new HashSet<>(roleRepository.findAll()));
		return repository.save(user);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly = true)
	public User findByUserName(String username) {
		User user = repository.findByUsername(username);
		return user;
	}
	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = repository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorities);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED,readOnly = true)
	public boolean isUsernameExist(String username) {
		User user = repository.findByUsername(username);
		return user != null;
	}
	
}