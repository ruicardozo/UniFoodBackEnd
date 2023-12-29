package com.unifood.bd;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.unifood.ed.FrequentacaoED;
import com.unifood.util.BD;

@Stateless
public class FrequentacaoBD extends BD<FrequentacaoED>
{
	@PersistenceContext(unitName = "UniFood")
	private EntityManager em;

	@Override
	public EntityManager getEntityManager()
	{
		return em;
	}
}
