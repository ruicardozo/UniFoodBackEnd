package com.unifood.util;

import java.util.List;

public abstract class RN<E extends ED>
{
	private BD<E> bd;
	private Val<E> val;

	public void setBD(BD<E> bd)
	{
		this.bd = bd;
	}

	public void setVal(Val<E> val)
	{
		this.val = val;
	}

	public List<E> lista(E ed)
	{
		val.validaLista(ed);
		return bd.lista(ed);
	}

	public E inclui(E ed)
	{
		val.validaInclui(ed);
		return bd.inclui(ed);
	}

	public void remove(E ed)
	{
		val.validaRemove(ed);
		bd.remove(ed);
	}

}
