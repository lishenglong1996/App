package cn.appsys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.DataDictionary.DataDictionaryService;

@RequestMapping("/dic")
@Controller
public class DictionaryController {
	@Resource
	private DataDictionaryService dicService;
	
	/**
	 * 根据类型查询平台列表
	 * @return
	 */
	@RequestMapping("/getDic")
	@ResponseBody
	public List<DataDictionary> getDataDictionary(@RequestParam String tcode){
		System.out.println("查询平台列表");
		List<DataDictionary> dictionary=dicService.getListByTypeName(tcode);
		return dictionary;
	}
}
