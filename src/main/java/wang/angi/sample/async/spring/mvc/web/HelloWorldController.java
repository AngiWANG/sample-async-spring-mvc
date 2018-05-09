package wang.angi.sample.async.spring.mvc.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author angi
 */
@Controller
public class HelloWorldController {

    Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping("/helloWorld")
    @ResponseBody
    public DeferredResult<String> helloWorld() {
        logger.info("helloWorld begin");
        final DeferredResult<String> deferredResult = new DeferredResult<String>(30 * 1000);
        // 另开worker线程进行回调处理
        deferredResult.onCompletion(new Runnable() {
            public void run() {
                logger.info("onCompletion begin");
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("onCompletion end");
            }

        });
        // 自定义线程进行业务处理
        new Thread(new Runnable() {
            public void run() {
                logger.info("biz begin");
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                deferredResult.setResult("Hello, World!");
                logger.info("biz end");
            }

        }).start();
        logger.info("helloWorld end");
        return deferredResult;
    }
}
