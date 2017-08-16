package com.allinpay.sample.spring.mvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShowMessageControllerError {
	
	Logger logger = LoggerFactory.getLogger(ShowMessageControllerError.class);

	@RequestMapping("/showMessage3")
	public DeferredResult<ModelAndView> showMessage() {
		final DeferredResult<ModelAndView> deferredResult = new DeferredResult<ModelAndView>();

		//在另一个线程中进行业务处理，然后设置结果即可（setResult）
		new Thread(new Runnable() {
			public void run() {
				try{
					int a = 3/0;
				}catch(Throwable e){
					logger.error(Boolean.toString(deferredResult.setErrorResult(new Throwable("error",e))));
				}
			}

		}).start();

		return deferredResult;
	}
}
