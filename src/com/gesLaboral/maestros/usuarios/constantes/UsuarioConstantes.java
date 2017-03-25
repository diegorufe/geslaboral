package com.gesLaboral.maestros.usuarios.constantes;

import java.util.HashMap;

import com.gesLaboral.maestros.usuarios.enums.EnumPermisos;

public class UsuarioConstantes {
	public static final HashMap<String, String> PERMISOS = new HashMap<String, String>();

	static {
		// Roles/Permisos
		PERMISOS.put(EnumPermisos.ADMIN.getValor(), "Admin"); // Administrador
		PERMISOS.put(EnumPermisos.BASIC.getValor(), "Basico"); // Usuario basico
	}
}
