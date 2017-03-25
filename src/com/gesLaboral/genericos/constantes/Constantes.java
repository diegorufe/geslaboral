package com.gesLaboral.genericos.constantes;

import java.util.HashMap;

public class Constantes {

	public static final String EXITO = "succes";
	public static final String ERROR = "error";
	public static final String LOGOUT = "logout";
	public static HashMap<String, String> ERRORES_EXISTE = new HashMap<String, String>();
	public static HashMap<String, String> NO_BORRAR = new HashMap<String, String>();
	public static HashMap<String, String> ORDER_JOIN = new HashMap<String, String>();
	public static final int ENTIDADES_PAGINA = 100;
	public static final int LIMIT_INI_GLOBAL = 0;
	public static final int ROWS_VISIBLES = 10;

	static {
		// Errores existe
		ERRORES_EXISTE.put("com.diego.practica4.entidad.Cliente", "clie.exist");
		ERRORES_EXISTE.put("com.diego.practica4.entidad.Proveedor", "prov.exist");
		ERRORES_EXISTE.put("com.diego.practica4.entidad.TipoCliente", "tipoClie.exist");
		ERRORES_EXISTE.put("com.diego.practica4.entidad.TipoProveedor", "tipoProv.exist");
		// No borrar
		NO_BORRAR.put("com.diego.practica4.entidad.TipoCliente", "tipoClie.noborrar");
		NO_BORRAR.put("com.diego.practica4.entidad.TipoProveedor", "tipoProv.noborrar");
		// Consultas Order_Join
		ORDER_JOIN.put("tipoCliente", "(select codigo from TipoClientes where id = idTipoCliente )");
		ORDER_JOIN.put("tipoProveedor", "(select codigo from TipoProveedores where id = idTipoProveedor )");
	}
}
