package cn.appsys.dao.dataDictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryMapper {
	/**
	 * 在字典中根据类型查询
	 * @param typeName
	 * @return
	 */
	public List<DataDictionary> getListByTypeName(@Param("typeCode") String typeCode);
}
