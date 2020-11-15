package com.roy.controller.lockscope;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;


/**
 * description：
 * author：dingyawu
 * date：created in 16:42 2020/11/6
 * history:
 */
@RestController
@RequestMapping("lockscope")
@Slf4j
public class LockScopeController {

    @GetMapping("wrong")
    public int wrong(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        Data.reset();
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().wrong());
        //这个地方要等一会，不然线程池还没执行完，主线程就去获取counter
        return Data.getCounter();
    }

    @GetMapping("right")
    public int right(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        Data.reset();
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().right());
        return Data.getCounter();
    }

    @GetMapping("wrong2")
    public String wrong2() {
        Interesting interesting = new Interesting();
        new Thread(() -> interesting.add()).start();
        new Thread(() -> interesting.compare()).start();
        return "OK";
    }

    @GetMapping("right2")
    public String right2() {
        Interesting interesting = new Interesting();
        new Thread(() -> interesting.add()).start();
        new Thread(() -> interesting.compareRight()).start();
        return "OK";
    }
}
