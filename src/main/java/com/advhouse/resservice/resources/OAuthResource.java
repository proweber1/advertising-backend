package com.advhouse.resservice.resources;

import com.advhouse.resservice.api.LoginRepresentation;
import com.advhouse.resservice.core.AccessToken;
import com.advhouse.resservice.services.AuthService;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Этот ресурс позволяет нам авторизироваться в нашей
 * рекламной системе по протоколу OAuth2
 *
 * @author proweber1
 */
@Path("/oauth2")
@Api(
        value = "Авторизация",
        tags = {"Пользователи", "Авторизация"},
        description = "Данный метод позволяет авторизироваться пользователю в своем аккаунте"
)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OAuthResource {

    private final AuthService authService;

    /**
     * @param authService Сервис который авторизирует нашего пользователя
     */
    public OAuthResource(final AuthService authService) {
        this.authService = authService;
    }

    /**
     * Авторизация пользователя в системе по логину и паролю, сначала
     * проверяется существование пользователя, потом создается access token
     * и возвращается
     *
     * @return Токен доступа для пользователя
     */
    @POST
    @Timed
    @UnitOfWork
    @ApiOperation(value = "Вход по протоколу OAuth", response = AccessToken.class)
    public AccessToken login(@NotNull @Valid LoginRepresentation request) {
        try {
            return authService.login(request);
        } catch (LoginException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
