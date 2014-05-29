package com.krissoft.saa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.krissoft.saa.model.HudlModel;
import com.krissoft.saa.model.MaxPrepsModel;
import com.krissoft.saa.repository.PlayerRepository;

@Controller
@RequestMapping("schedule")
public class ScheduleController {
	

	@Autowired
	PlayerRepository playerRepository;
	
	boolean isMp = false;
	boolean isHudlStarted = false;
	
	// day 86400000
	//hour 3600000
	@Scheduled(fixedDelay=86400000)
	public void maxprepsSchuler() {
		if (isMp) {	
			System.out.println("````````````````maxpreps`````````````````````````");
			isMp = false;
			MaxPrepsModel m = new MaxPrepsModel(playerRepository);
			m.run();
			isMp = true;
		}
		if (isHudlStarted) {	
			System.out.println("``````````````````hudl````````````````````````");
			isHudlStarted = false;
			HudlModel m = new HudlModel(playerRepository);
			m.run();
			isHudlStarted = true;
		}
	}
	
	@RequestMapping(value = "/startmaxpreps", method = RequestMethod.GET)
	public String startmaxpreps(ModelMap model) {
		isMp = true;
		maxprepsSchuler();
		return "";
	}
	
	@RequestMapping(value = "/stopmaxpreps", method = RequestMethod.GET)
	public String stopmaxpreps(ModelMap model) {
		isMp = false;
		return "";
	}
	
	@RequestMapping(value = "/startHudl", method = RequestMethod.GET)
	public String startHudl(ModelMap model) {
		isHudlStarted = true;
		maxprepsSchuler();
		return "";
	}
	
	@RequestMapping(value = "/stopHudl", method = RequestMethod.GET)
	public String stopHudl(ModelMap model) {
		isHudlStarted = false;
		return "";
	}
	

}
