package com.unifood.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
	private static final SessionFactory sessionFactory = buildSessionFactory();

	// Método para construir a fábrica de sessões
	private static SessionFactory buildSessionFactory()
	{
		try
		{
			// Cria uma configuração baseada no arquivo hibernate.cfg.xml
			Configuration configuration = new Configuration().configure();

			// Retorna a fábrica de sessões configurada
			return configuration.buildSessionFactory();
		} catch (Throwable ex)
		{
			// Handle the exception (log it, rethrow it, etc.)
			System.err.println("Falha na inicialização da SessionFactory: " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	// Método para obter a fábrica de sessões
	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
}