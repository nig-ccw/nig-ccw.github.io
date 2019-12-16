package com.vcc.cem.mall.service.impl;

import com.vcc.cem.mall.entity.OrderMaster;
import com.vcc.cem.mall.service.dto.CartDTO;
import com.vcc.cem.mall.service.dto.OrderDTO;
import com.vcc.cem.mall.entity.OrderDetail;
import com.vcc.cem.mall.entity.ProductInfo;
import com.vcc.cem.mall.repository.OrderDetailRepository;
import com.vcc.cem.mall.repository.OrderMasterRepository;
import com.vcc.cem.mall.service.OrderService;
import com.vcc.cem.mall.service.ProductInfoService;
import com.vcc.cem.mall.service.utils.CheckUtils;
import com.vcc.cem.mall.service.utils.KeyUtils;
import com.vcc.cem.mall.utils.ConvertUtils;
import com.vcc.cem.mall.web.utils.enums.OrderPayStatusEnum;
import com.vcc.cem.mall.web.utils.enums.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author czh
 * @date 2019-12-10
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtils.generate();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        List<OrderDetail> orderDetailList=orderDTO.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList){
            //1 查询商品
            ProductInfo productInfo=productInfoService.findOne(orderDetail.getProductId());
            CheckUtils.check(productInfo);

            //2 计算总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            //3 写入订单详情数据库
            ConvertUtils.convert(productInfo, orderDetail);
            orderDetail.setOrderDetailId(KeyUtils.generate());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }

        //3 写入订单数据库
        orderDTO.setOrderId(orderId);
        OrderMaster orderMaster = ConvertUtils.convert(orderDTO, OrderMaster.class);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(OrderPayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4 扣库存
        List<CartDTO> cartDTOList=orderDetailList.stream().map(e -> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster=orderMasterRepository.findById(orderId).get();
        CheckUtils.check(orderMaster);

        List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderId(orderId);
        CheckUtils.checkList(orderDetailList);

        OrderDTO orderDTO = ConvertUtils.convert(orderMaster, OrderDTO.class);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterList=orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = ConvertUtils.convert(orderMasterList.getContent(), OrderDTO.class);
        return new PageImpl<>(orderDTOList, pageable, orderMasterList.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //1 判断订单状态
        CheckUtils.checkCancel(orderDTO, log);

        //2 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster=ConvertUtils.convert(orderDTO, OrderMaster.class);
        OrderMaster result=orderMasterRepository.save(orderMaster);
        CheckUtils.checkCancel(result, orderMaster, log);

        //3 返回库存
        CheckUtils.checkCancel(orderDTO.getOrderDetailList(), orderDTO, log);
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //4 如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(OrderPayStatusEnum.SUCCESS.getCode())) {
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //1 判断订单状态
        CheckUtils.checkFinish(orderDTO, log);

        //2 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster orderMaster=ConvertUtils.convert(orderDTO, OrderMaster.class);
        OrderMaster result=orderMasterRepository.save(orderMaster);
        CheckUtils.checkFinish(result, orderMaster, log);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //1 判断订单状态
        CheckUtils.checkPaid(orderDTO, log);

        //2 判断订单支付状态
        CheckUtils.checkPaidStatus(orderDTO, log);

        //3 修改订单支付状态
        orderDTO.setPayStatus(OrderPayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster=ConvertUtils.convert(orderDTO, OrderMaster.class);
        OrderMaster result=orderMasterRepository.save(orderMaster);
        CheckUtils.checkPaid(result, orderMaster, log);

        return orderDTO;
    }
}
