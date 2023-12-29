package com.unifood.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

@Stateful
public abstract class BD<E extends ED>
{
	private Class<E> entidade;

	public BD()
	{
		entidade = getEntityClass();
	}

	public List<E> lista(E e)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<E> criteria = builder.createQuery(entidade);

		Root<E> root = criteria.from(entidade);

		preencheCriterios(e, builder, criteria, root);

		List<E> lista = session.createQuery(criteria).getResultList();

		session.getTransaction().commit();
		session.close();

		return lista;
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

	private void preencheCriterios(E e, CriteriaBuilder builder, CriteriaQuery<E> criteria, Root<E> root)
	{
		Class<?> clazz = e.getClass();
		Method[] methods = clazz.getMethods();
		for (Method method : methods)
		{
			if (isGetter(method))
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
				if (valor != null)
				{
					Predicate predicate = null;
					if ("String".equals(method.getReturnType().getName()))
					{
						predicate = builder.like(root.get(method.getName()), valor.toString());
					} else if ("int".equals(method.getReturnType().getName())
							|| "Integer".equals(method.getReturnType().getName())
							|| "Float".equals(method.getReturnType().getName())
							|| "float".equals(method.getReturnType().getName())
							|| "Long".equals(method.getReturnType().getName())
							|| "long".equals(method.getReturnType().getName()))
					{
						predicate = builder.equal(root.get(method.getName()), valor.toString());
					}
					criteria.where(predicate);
				}
			}
		}
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

}

