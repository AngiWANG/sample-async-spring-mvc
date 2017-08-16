package com.allinpay.sample.spring.mvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShowMessageControllerTimeout {
	
	Logger logger = LoggerFactory.getLogger(ShowMessageControllerTimeout.class);

	@RequestMapping("/showMessage2")
	public DeferredResult<ModelAndView> showMessage() {
		final DeferredResult<ModelAndView> deferredResult = new DeferredResult<ModelAndView>(5 * 1000);

		//在另一个线程中进行业务处理，然后设置结果即可（setResult）
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(33 * 1000);
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

		deferredResult.onTimeout(new Runnable() {
			@Override
			public void run() {
				logger.error("onTimeout");
				ModelAndView modelAndView = new ModelAndView();
				modelAndView.setViewName("showMessage");
				modelAndView.addObject("message", "timeout");
				deferredResult.setResult(modelAndView);
			}

		});

		return deferredResult;
	}
}
