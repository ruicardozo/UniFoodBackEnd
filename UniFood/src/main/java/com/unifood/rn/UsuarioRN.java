package com.unifood.rn;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.unifood.bd.UsuarioBD;
import com.unifood.ed.UsuarioED;
import com.unifood.util.RN;
import com.unifood.val.UsuarioVal;

@Stateless
public class UsuarioRN extends RN<UsuarioED>
{
	@Inject
   private UsuarioBD usuarioBD;

	@Inject
	private UsuarioVal usuarioVal;

	public UsuarioRN()
	{
		setBD(usuarioBD);
		setVal(usuarioVal);
	}

}
