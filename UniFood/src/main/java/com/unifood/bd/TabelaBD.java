package com.unifood.bd;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.unifood.ed.TabelaED;

@Stateful
public class TabelaBD
{
	  @PersistenceContext(unitName = "UniFood")
	  private EntityManager em;

	  public List<TabelaED> lista()
	  {
		  return em.createQuery("from TabelaED WHERE schema = 'public'", TabelaED.class).getResultList();
	  }
}
