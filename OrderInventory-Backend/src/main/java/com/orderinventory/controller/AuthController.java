package com.orderinventory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderinventory.entity.AuthRequest;
import com.orderinventory.entity.JwtResponse;
import com.orderinventory.entity.User;
import com.orderinventory.security.JwtTokenUtil;
import com.orderinventory.services.impl.UserService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	private final UserDetailsService userDetailsService;
	private final UserService userService;

	public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
			UserDetailsService userDetailsService, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
		this.userDetailsService = userDetailsService;
		this.userService = userService;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JwtResponse> authenticate(@RequestBody AuthRequest request) throws Exception {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		} catch (BadCredentialsException e) {
			throw new IllegalArgumentException("Invalid email or password.");
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody AuthRequest request) throws Exception {
		User user = userService.registerUser(request.getEmail(), request.getPassword());
		return ResponseEntity.ok(user);
	}
}
