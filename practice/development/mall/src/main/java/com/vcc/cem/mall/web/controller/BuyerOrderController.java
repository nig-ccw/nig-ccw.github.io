package com.vcc.cem.mall.web.controller;

import com.vcc.cem.mall.service.BuyerService;
import com.vcc.cem.mall.service.OrderService;
import com.vcc.cem.mall.service.dto.OrderDTO;
import com.vcc.cem.mall.web.form.OrderForm;
import com.vcc.cem.mall.web.utils.ResultVOUtils;
import com.vcc.cem.mall.web.utils.ValidUtils;
import com.vcc.cem.mall.web.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author czh
 * @date 2019-12-10
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @PostMapping("create")
    public ResultVO<Map<String,String>> create(@Valid  OrderForm orderForm, BindingResult bindingResult) {
        ValidUtils.validCreate(orderForm, bindingResult, log);

        OrderDTO orderDTO = orderForm.convert();
        ValidUtils.validCreate(orderDTO, log);

        OrderDTO result=orderService.create(orderDTO);

        Map<String,String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResultVOUtils.success(map);
    }

    @GetMapping("list")
    public ResultVO<List<OrderDTO>> list(@RequestParam(name="openid") String openid,
                                         @RequestParam(name="page",defaultValue="0") Integer page,
                                         @RequestParam(name="size",defaultValue="10") Integer size) {

        ValidUtils.validList(openid, log);

        Page<OrderDTO> orderDTOPage=orderService.findList(openid, PageRequest.of(page, size));

        return ResultVOUtils.success(orderDTOPage.getContent());
    }

    @GetMapping("detail")
    public ResultVO<OrderDTO> detail(@RequestParam(name="openid") String openid,
                                     @RequestParam(name="orderId") String orderId) {

        ValidUtils.validDetail(openid, log);

        OrderDTO orderDTO=buyerService.findOrderOne(openid, orderId);

        return ResultVOUtils.success(orderDTO);
    }

    @PostMapping("cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam(name="openid") String openid,
                                     @RequestParam(name="orderId") String orderId) {

        ValidUtils.validCancel(openid, log);

        OrderDTO orderDTO=buyerService.cancelOrder(openid, orderId);

        return ResultVOUtils.success(orderDTO);
    }
}
