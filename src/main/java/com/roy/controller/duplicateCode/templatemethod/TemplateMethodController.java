package com.roy.controller.duplicateCode.templatemethod;

import com.roy.controller.duplicateCode.templatemethod.right.AbstractCart;
import com.roy.controller.duplicateCode.templatemethod.wrong.InternalUserCart;
import com.roy.controller.duplicateCode.templatemethod.wrong.NormalUserCart;
import com.roy.controller.duplicateCode.templatemethod.wrong.VipUserCart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * description：利用工厂模式 + 模板方法模式，消除 if…else 和重复代码
 * author：dingyawu
 * date：created in 17:10 2020/11/15
 * history:
 */

@Slf4j
@RestController
@RequestMapping("test")
public class TemplateMethodController {

    /**
     * 商品清单map，key：商品id，value：商品数量
     */
    private static Map<Long, Integer> items = new HashMap<>();

    static {
        items.put(1L, 2);
        items.put(2L, 4);
    }

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("wrong")
    public Cart wrong(@RequestParam("userId") int userId) {
        //根据userId得到用户的类型
        String userCategory = Db.getUserCategory(userId);

        if (userCategory.equals("Normal")) {
            NormalUserCart normalUserCart = new NormalUserCart();
            return normalUserCart.process(userId, items);
        }

        if (userCategory.equals("Vip")) {
            VipUserCart vipUserCart = new VipUserCart();
            return vipUserCart.process(userId, items);
        }

        if (userCategory.equals("Internal")) {
            InternalUserCart internalUserCart = new InternalUserCart();
            return internalUserCart.process(userId, items);
        }

        return null;
    }

    @GetMapping("right")
    public Cart right(@RequestParam("userId") int userId) {
        String userCategory = Db.getUserCategory(userId);
        AbstractCart cart = (AbstractCart) applicationContext.getBean(userCategory + "UserCart");
        return cart.process(userId, items);
    }
}
