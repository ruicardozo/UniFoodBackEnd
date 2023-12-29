package com.unifood.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Stateful
public abstract class BD<E extends ED>
{
	private Class<E> entidade;

	public abstract EntityManager getEntityManager();

	@SuppressWarnings("unchecked")
	public List<E> lista(E e)
	{
		Query query = getEntityManager().createNativeQuery(montaSqlSelect(e), entidade);

		return query.getResultList();
	}

   private static boolean isGetter(Method method) {
       return method.getName().startsWith("get") &&
              method.getParameterCount() == 0 &&
              !method.getReturnType().equals(void.class);
   }

	@SuppressWarnings("unchecked")
	private Class<E> getEntityClass()
	{
		Type type = getClass().getGenericSuperclass();
		ParameterizedType paramType = (ParameterizedType) type;
		return (Class<E>) paramType.getActualTypeArguments()[0];
	}

	private String montaSqlSelect(E e)
	{
		StringBuilder sql = new StringBuilder();

		montaSelect(sql);

		Class<?> clazz = e.getClass();
		Method[] methods = clazz.getMethods();

		for (Method method : methods)
		{
			if (isGetter(method))
			{
				if (!"getClass".equals(method.getName()))
				{
					Object valor;
					valor = obtemValorDoGetter(e, method);

					if (valor != null)
					{
						sql.append(" and ");
						sql.append(obtemNomeAtributoDoGetter(method.getName()));
						if ("java.lang.String".equals(method.getReturnType().getName()))
						{
							sql.append(String.format(" like '%%%s%%'", valor));
						} else
						{
							sql.append(" = ").append(valor.toString());
						}
					}
				}
			}
		}

		return sql.toString();
	}

	private Object obtemValorDoGetter(E e, Method method)
	{
		Object valor;
		try
		{
			valor = method.invoke(e);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1)
		{
			valor = null;
			e1.printStackTrace();
		}
		return valor;
	}

	private void montaSelect(StringBuilder sql)
	{
		sql.append("select * from ");
		sql.append(obtemNomeTabela());
		sql.append(" where 1 = 1"); // apenas para facilitar a montagem do sql
	}

	private String obtemNomeTabela()
	{
		String result = getEntidade().getName().substring(0, getEntidade().getName().length() - 2);
		return result.substring(result.lastIndexOf(".") + 1);
	}

	private static String obtemNomeAtributoDoGetter(String nomeMetodoGetter)
	{
		String result;

		if (nomeMetodoGetter.startsWith("get") && nomeMetodoGetter.length() > 3)
		{
			result = Character.toLowerCase(nomeMetodoGetter.charAt(3)) + nomeMetodoGetter.substring(4);
		} else if (nomeMetodoGetter.startsWith("is") && nomeMetodoGetter.length() > 2)
		{
			result = Character.toLowerCase(nomeMetodoGetter.charAt(2)) + nomeMetodoGetter.substring(3);
		} else
		{
			throw new IllegalArgumentException("Não foi possível obeter o nome do método getter");
		}

		return result;
	}

	public E inclui(E ed)
	{
		return ed;
	}

	public void remove(E ed)
	{

	}

	public E atualiza(E ed)
	{
		return ed;
	}

	private Class<E> getEntidade()
	{
		if (entidade == null)
		{
			entidade = getEntityClass();
		}

		return entidade;
	}

}

