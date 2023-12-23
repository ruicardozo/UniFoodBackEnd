package com.unifood.ed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "pg_tables")
public class TabelaED
{
	@Id
	@Column(name = "tablename")
	String nome;

	@Column(name = "schemaname")
	String schema;
}
