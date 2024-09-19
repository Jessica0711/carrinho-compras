package com.carrinhocompra.controller;

import static java.util.Objects.nonNull;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrinhocompra.config.TokenService;
import com.carrinhocompra.model.User;
import com.carrinhocompra.model.dto.AuthenticationDTO;
import com.carrinhocompra.model.dto.LoginResponseDTO;
import com.carrinhocompra.model.dto.RegisterDTO;
import com.carrinhocompra.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository repository;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((User) auth.getPrincipal());
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

	@PostMapping("/registrar")
	public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
		if (nonNull(this.repository.findByLogin(data.login()))) {
			return ResponseEntity.badRequest().build();
		}
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
		User newUser = new User(data.login(), encryptedPassword, data.role());
		this.repository.save(newUser);
		return ResponseEntity.ok().build();
	}
}
