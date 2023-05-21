package com.tamguo.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.tamguo.config.dao.SuperMapper;
import com.tamguo.model.ChapterEntity;

public interface ChapterMapper extends SuperMapper<ChapterEntity>{

	List<ChapterEntity> findByBookId(@Param(value="bookId") String bookId);
	
	List<ChapterEntity> findByParentId(@Param(value="parentId") String parentId);

	ChapterEntity findNextPoint(@Param(value="uid")String uid , @Param(value="orders")Integer orders);

}
