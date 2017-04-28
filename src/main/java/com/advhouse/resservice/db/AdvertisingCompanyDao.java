package com.advhouse.resservice.db;

import com.advhouse.resservice.core.AdvertisingCompany;

/**
 * Это DAO для работы с рекламными кампаниями
 *
 * @author proweber1
 */
public interface AdvertisingCompanyDao {

    /**
     * Создание рекламной кампании в базе данных
     *
     * @param advertisingCompany Модель которую надо сохранить
     * @return Сохраненная сущность
     */
    AdvertisingCompany create(AdvertisingCompany advertisingCompany);
}
