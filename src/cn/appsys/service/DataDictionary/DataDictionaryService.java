package cn.appsys.service.DataDictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {
	/**
	 * 在字典中根据类型查询
	 * @param typeName
	 * @return
	 */
	public List<DataDictionary> getListByTypeName(@Param("typeName") String typeName);
}
