package com.unifood.rn;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.unifood.bd.TabelaBD;
import com.unifood.ed.TabelaED;

@Stateless
public class TabelaRN
{
	@Inject
	private TabelaBD tabelaBD;

	public List<TabelaED> lista()
	{
		return tabelaBD.lista();
	}
}
