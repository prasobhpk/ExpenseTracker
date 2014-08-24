package com.pk.et.infra.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.h2.constraint.Constraint;
import org.h2.engine.Session;
import org.h2.jdbc.JdbcConnection;
import org.h2.schema.Schema;
import org.h2.table.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestUtil {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private DataSource dataSource;

	@Transactional
	public void cleanDataBase() {
		deleteDataBase();
	}

	@SuppressWarnings("unchecked")
	private void deleteDataBase() {
		final List<Object[]> constraintsCreations = this.em
				.createNativeQuery(
						"SELECT SQL,TABLE_SCHEMA,TABLE_NAME FROM INFORMATION_SCHEMA.CONSTRAINTS")
				.getResultList();
		final List<Object[]> constraintnames = this.em
				.createNativeQuery(
						"SELECT CONSTRAINT_NAME,TABLE_SCHEMA,TABLE_NAME FROM INFORMATION_SCHEMA.CONSTRAINTS")
				.getResultList();
		for (final Object[] constraintName : constraintnames) {
			if (constraintName[1].equals("PUBLIC")
					&& !(constraintName[2].equals("ID_GEN") || constraintName[2]
							.equals("SEQUENCE"))) {
				this.em.createNativeQuery(
						"ALTER TABLE PUBLIC." + constraintName[2]
								+ " DROP CONSTRAINT " + constraintName[0])
						.executeUpdate();
			}
		}

		final List<Object[]> tables = this.em
				.createNativeQuery(
						"SELECT TABLE_SCHEMA,TABLE_NAME FROM INFORMATION_SCHEMA.TABLES")
				.getResultList();

		for (final Object[] table : tables) {
			if (table[0].equals("PUBLIC")
					&& !(table[1].equals("ID_GEN") || table[1]
							.equals("SEQUENCE"))) {
				this.em.createNativeQuery("DELETE FROM PUBLIC." + table[1])
						.executeUpdate();
			}
		}

		for (final Object[] constraintsCreation : constraintsCreations) {
			if (constraintsCreation[1].equals("PUBLIC")
					&& !(constraintsCreation[2].equals("ID_GEN") || constraintsCreation[2]
							.equals("SEQUENCE"))) {
				this.em.createNativeQuery(constraintsCreation[0].toString())
						.executeUpdate();
			}
		}
	}

	private void cleanupUsingAPI() {
		try {
			final Map<Table, List<Constraint>> tableConstrints = new HashMap<Table, List<Constraint>>();
			final JdbcConnection jcon = (JdbcConnection) this.em
					.unwrap(Connection.class);
			final Session s = (Session) (jcon).getSession();
			final Schema pub = s.getDatabase().getSchema("PUBLIC");
			for (final Table table : pub.getAllTablesAndViews()) {
				if (table.canTruncate()
						&& !(table.getName().equals("ID_GEN") || table
								.getName().equals("SEQUENCE"))) {
					table.truncate(s);
					final List<Constraint> constraints = new ArrayList<Constraint>();
					for (final Constraint c : table.getConstraints()) {
						constraints.add(c);
					}
					tableConstrints.put(table, constraints);
				}
			}
			for (final Table table : tableConstrints.keySet()) {
				for (final Constraint c : tableConstrints.get(table)) {
					table.removeConstraint(c);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
