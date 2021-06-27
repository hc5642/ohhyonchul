package com.ohhyonchul.fepmanager.ctl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ohhyonchul.fepmanager.dto.FepLineDto;
import com.ohhyonchul.fepmanager.svc.FepLineSvc;

@Controller
public class FepLineController {
	
	@Autowired
	private FepLineSvc svc;
	
	@RequestMapping("/log/list")
	public String selectList(
			@RequestParam(value="searchKey", required=false) String searchKey,
			Model model) { 
		model.addAttribute("title", "FEP LINE LIST");
		model.addAttribute("list", svc.selectList(searchKey));
		model.addAttribute("searchKey", searchKey);
		return "line";
	}
	
	@PostMapping("/log/update")
	public String updateItem(
			@RequestParam(value="seqNum") int seqNum,
			@RequestParam(value="instCode") String instCode,
			@RequestParam(value="instName") String instName,
			@RequestParam(value="applCode") String applCode,
			@RequestParam(value="applName") String applName,
			@RequestParam(value="typeClcd") String typeClcd
			) {
		FepLineDto dto = new FepLineDto();
		dto.setSeqNum(seqNum);
		dto.setInstCode(instCode);
		dto.setInstName(instName);
		dto.setApplCode(applCode);
		dto.setApplName(applName);
		dto.setTypeClcd(typeClcd);
		svc.updateItem(dto);
		return "redirect:/log/list";
	}
	
	@RequestMapping("/log/insert")
	public String insertItem(
			@RequestParam(value="instCode") String instCode,
			@RequestParam(value="instName") String instName,
			@RequestParam(value="applCode") String applCode,
			@RequestParam(value="applName") String applName,
			@RequestParam(value="typeClcd") String typeClcd
			) {
		FepLineDto dto = new FepLineDto();
		dto.setInstCode(instCode);
		dto.setInstName(instName);
		dto.setApplCode(applCode);
		dto.setApplName(applName);
		dto.setTypeClcd(typeClcd);
		svc.insertItem(dto);
		return "redirect:/log/list";
	}
	
	@RequestMapping("/log/delete") 
	public String deleteItem(@RequestParam(value="seqNum") int seqNum) {
		svc.deleteItem(seqNum);
		return "redirect:/log/list";
	}

}
