package com.carrinhocompra.model.dto;

import com.carrinhocompra.model.enums.UserRole;

public record RegisterDTO(String login, String senha, UserRole role) {

}
