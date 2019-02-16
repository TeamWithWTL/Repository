package com.jcwx.dao.dflz;

import java.util.List;

import com.jcwx.dao.BaseDao;
import com.jcwx.entity.dflz.AccessoryEntity;
/**
 * 曝光附件Dao
 * @author 李伟
 * @time 2017年10月26日上午10:23:35
 */
public interface ExpAcceDao extends BaseDao{

	List<AccessoryEntity> findByNewId(String new_id);

}
