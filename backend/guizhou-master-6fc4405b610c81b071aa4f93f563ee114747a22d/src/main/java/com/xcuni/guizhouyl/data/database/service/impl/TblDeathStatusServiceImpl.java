package com.xcuni.guizhouyl.data.database.service.impl;

import com.xcuni.guizhouyl.data.database.dao.BaseDao;
import com.xcuni.guizhouyl.data.database.entity.TblDeathDataSrcEntity;
import com.xcuni.guizhouyl.data.database.service.TblDeathDataSrcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TblDeathStatusServiceImpl implements TblDeathDataSrcService {
    final static Logger LOGGER = LoggerFactory.getLogger(TblDeathStatusServiceImpl.class);
    @Resource
    private BaseDao baseDao;

    @Override
    public void insertDeathData(TblDeathDataSrcEntity entity) {
        try {
            baseDao.save(entity);
        } catch (Exception e) {
            LOGGER.error("insertDeathData异常,{}", e.getMessage());
        }
    }

    @Override
    public void updateDeathData(TblDeathDataSrcEntity entity) {
        try {
            baseDao.update(entity);
        } catch (Exception e) {
            LOGGER.error("updateDeathData异常,{}", e.getMessage());
        }
    }

    @Override
    public TblDeathDataSrcEntity getDeathDataEntityById(String userId) {
        try {
            TblDeathDataSrcEntity entity = baseDao.findByID(userId, TblDeathDataSrcEntity.class);
            if (entity == null) {
                LOGGER.debug("getDeathDataEntityById,未找到userId为{}的记录.", userId);
            }
            return entity;
        } catch (Exception e) {
            LOGGER.error("getDeathDataEntityById,{}", e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteDeathData(String userId) {
        try {
            String sql = "DELETE FROM TBL_DEATH_DATA_SRC WHERE user_id=" + "\"" + userId + "\"";
            baseDao.getNameJdbc().getJdbcTemplate().execute(sql);
        } catch (Exception e) {
            LOGGER.error("deleteDeathData,{}", e.getMessage());
        }
    }
}
