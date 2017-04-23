package com.advhouse.resservice.resources;

import com.advhouse.resservice.api.UserApi;
import com.advhouse.resservice.core.User;
import com.advhouse.resservice.db.UserDao;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Ресурс для работы с пользователями
 *
 * @author proweber1
 */
@Path("/users")
@Api(value = "/users", description = "Операции над пользователем", tags = {"Пользователи"})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {

    /**
     * DAO для управления пользователями, сделано чтобы обеспечить
     * CRUD операции над пользователями в этом ресурсе
     */
    private UserDao userDao;

    public UsersResource(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Этот метод позволяет нам зарегистрировать нового пользователя
     * в рекламной системе
     *
     * @return Статус
     */
    @POST
    @Timed
    @UnitOfWork
    @ApiOperation(value = "Регистрация пользователя", response = User.class)
    public User registration(@Valid @NotNull UserApi userApi) {
        final User user = User.valueOf(userApi);

        if (userDao.isUserExist(user)) {
            throw new BadRequestException("User must be unique!");
        }

        return userDao.save(user);
    }
}
