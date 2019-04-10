package com.didong.service;

import com.didong.serviceEntity.TbBackUser;

public interface BackLoginService {

    TbBackUser backLogin(String username, String password);
}
