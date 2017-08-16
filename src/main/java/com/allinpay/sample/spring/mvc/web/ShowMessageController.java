package com.allinpay.sample.spring.mvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShowMessageController {

	Logger logger = LoggerFactory.getLogger(ShowMessageController.class);
	
	@RequestMapping("/showMessage")
	public DeferredResult<ModelAndView> showMessage() {
		final DeferredResult<ModelAndView> deferredResult = new DeferredResult<ModelAndView>(30 * 1000);

		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("showMessage");
				modelAndView.addObject("message", "Hello, World!");
				deferredResult.setResult(modelAndView);
			}

		}).start();
		
		deferredResult.onCompletion(new Runnable() {

			@Override
			public void run() {
				logger.info("onCompletion");
			}

		});

		return deferredResult;
	}
}
