package com.advhouse.resservice.db.impl;

import com.advhouse.resservice.core.AdvertisingCompany;
import com.advhouse.resservice.db.AdvertisingCompanyDao;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class AdvertisingCompanyDaoImpl extends AbstractDAO<AdvertisingCompany> implements AdvertisingCompanyDao {

    /**
     * {@inheritDoc}
     */
    public AdvertisingCompanyDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdvertisingCompany create(AdvertisingCompany advertisingCompany) {
        return persist(advertisingCompany);
    }
}
