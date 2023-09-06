package com.vertexid.viself.ma.dao;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

  

/**
 * 
 * @Class Name : ItemMstMngDAO.java
 * @Description : DB용 상위 Dao 클래스
 * 
 * @Modification Information  
 * @
 * @ Date            Author       Description
 * @ -------------  -----------  -------------
 * @   2023.09.06    윤태영         최초넥사크로 F/W 생성 작업  
 *
 */


@Repository("com.vertexid.viself.ma.dao.ItemMstMngDAO")
public class ItemMstMngDAO {
	private static final Logger logger = LoggerFactory.getLogger(ItemMstMngDAO.class);
	protected SqlSessionFactory sqlSessionFactory;
	 
	
	
    
	
}
