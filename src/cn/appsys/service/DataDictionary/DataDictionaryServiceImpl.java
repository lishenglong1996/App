package cn.appsys.service.DataDictionary;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.dao.dataDictionary.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;

@Service("dataDictionary")
public class DataDictionaryServiceImpl implements DataDictionaryService {
	
	@Resource
	private DataDictionaryMapper dataDictionaryMapper;
	public DataDictionaryMapper getDataDictionaryMapper() {
		return dataDictionaryMapper;
	}
	public void setDataDictionaryMapper(DataDictionaryMapper dataDictionaryMapper) {
		this.dataDictionaryMapper = dataDictionaryMapper;
	}


	@Override
	public List<DataDictionary> getListByTypeName(String typeName) {
		
		return dataDictionaryMapper.getListByTypeName(typeName);
	}

}
