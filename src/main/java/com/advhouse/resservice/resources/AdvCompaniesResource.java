package com.advhouse.resservice.resources;

import com.advhouse.resservice.core.AdvertisingCompany;
import com.advhouse.resservice.db.AdvertisingCompanyDao;
import com.advhouse.resservice.security.UserPrincipal;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Этот ресурс предназначен для управления рекламными компаниями пользователей
 *
 * @author proweber1
 */
@Path("/advertising-companies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class AdvCompaniesResource {

    private final AdvertisingCompanyDao advertisingCompanyDao;

    /**
     * @param advertisingCompanyDao DAO для работы с рекламными компаниями
     */
    public AdvCompaniesResource(AdvertisingCompanyDao advertisingCompanyDao) {
        this.advertisingCompanyDao = advertisingCompanyDao;
    }

    /**
     * Создает новую рекламную компанию и привязывает ее к текущему авторизированному
     * пользователю
     *
     * @param advertisingCompany Модель рекламной компании
     * @param userPrincipal      Авторизированный пользователь
     * @return Созданная рекламная компания
     */
    @POST
    @Timed
    @UnitOfWork
    public AdvertisingCompany create(@NotNull @Valid AdvertisingCompany advertisingCompany,
                                     @Auth UserPrincipal userPrincipal) {

        advertisingCompany.setOwner(userPrincipal.getUser());
        return advertisingCompanyDao.create(advertisingCompany);
    }
}
