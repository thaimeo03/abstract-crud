package com.tht.abstract_crud.service;

import com.tht.abstract_crud.model.base.BaseListRequest;
import com.tht.abstract_crud.model.user.request.UserFilter;

public interface UserDslService {
  public Object find(BaseListRequest<UserFilter> request);
}
