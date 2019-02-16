package com.jcwx.service.dflz;

import java.util.List;

import com.jcwx.entity.dflz.AccessoryEntity;
import com.jcwx.entity.dflz.ExpAcceEntity;

public interface ExpAcceService {

	void saveOrUpdate(ExpAcceEntity expAcceEntity);

	List<AccessoryEntity> findByYwId(String id);

	void delFj(String fjId);

}
