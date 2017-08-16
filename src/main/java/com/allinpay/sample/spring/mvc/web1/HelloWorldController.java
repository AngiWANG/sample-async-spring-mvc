package com.allinpay.sample.spring.mvc.web1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
public class HelloWorldController {

	@RequestMapping("/helloWorld")
	@ResponseBody
	public DeferredResult<String> showMessage() {
		final DeferredResult<String> deferredResult = new DeferredResult<String>(30 * 1000);

		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				deferredResult.setResult("Hello, World!");
			}

		}).start();

		return deferredResult;
	}
}
