package com.kk.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

	private String username;    // email is username

	private String password;
}
