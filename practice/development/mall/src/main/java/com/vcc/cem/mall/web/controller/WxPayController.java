package com.vcc.cem.mall.web.controller;

import com.github.binarywang.wxpay.bean.coupon.WxPayCouponInfoQueryRequest;
import com.github.binarywang.wxpay.bean.coupon.WxPayCouponInfoQueryResult;
import com.github.binarywang.wxpay.bean.coupon.WxPayCouponSendRequest;
import com.github.binarywang.wxpay.bean.coupon.WxPayCouponSendResult;
import com.github.binarywang.wxpay.bean.coupon.WxPayCouponStockQueryRequest;
import com.github.binarywang.wxpay.bean.coupon.WxPayCouponStockQueryResult;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxScanPayNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayDownloadBillRequest;
import com.github.binarywang.wxpay.bean.request.WxPayMicropayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayOrderCloseRequest;
import com.github.binarywang.wxpay.bean.request.WxPayOrderQueryRequest;
import com.github.binarywang.wxpay.bean.request.WxPayOrderReverseRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundQueryRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayReportRequest;
import com.github.binarywang.wxpay.bean.request.WxPaySendRedpackRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayBillResult;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderCloseResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderReverseResult;
import com.github.binarywang.wxpay.bean.result.WxPayRedpackQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.bean.result.WxPaySendRedpackResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Date;

/**
 * @author czh
 * @date 2019-12-10
 */
@RestController
@RequestMapping("/pay")
public class WxPayController {

    private WxPayService wxPayService;

    @Autowired
    public WxPayController(WxPayService wxPayService) {
        this.wxPayService=wxPayService;
    }

    @GetMapping("/queryOrder")
    public WxPayOrderQueryResult queryOrder(@RequestParam(required=false) String transactionId,
                                            @RequestParam(required=false) String outTradeNo) throws WxPayException {
        return wxPayService.queryOrder(transactionId, outTradeNo);
    }

    @PostMapping("/queryOrder")
    public WxPayOrderQueryResult queryOrder(@RequestBody WxPayOrderQueryRequest wxPayOrderQueryRequest) throws WxPayException {
        return wxPayService.queryOrder(wxPayOrderQueryRequest);
    }

    @GetMapping("/closeOrder/{outTradeNo}")
    public WxPayOrderCloseResult closeOrder(@PathVariable String outTradeNo) throws WxPayException {
        return wxPayService.closeOrder(outTradeNo);
    }

    @PostMapping("/closeOrder")
    public WxPayOrderCloseResult closeOrder(@RequestBody WxPayOrderCloseRequest wxPayOrderCloseRequest) throws WxPayException {
        return wxPayService.closeOrder(wxPayOrderCloseRequest);
    }

    @PostMapping("/createOrder")
    public <T> T createOrder(@RequestBody WxPayUnifiedOrderRequest request) throws WxPayException {
        return wxPayService.createOrder(request);
    }

    @PostMapping("/unifiedOrder")
    public WxPayUnifiedOrderResult unifiedOrder(@RequestBody WxPayUnifiedOrderRequest request) throws WxPayException {
        return wxPayService.unifiedOrder(request);
    }

    @PostMapping("/refund")
    public WxPayRefundResult refund(@RequestBody WxPayRefundRequest request) throws WxPayException {
        return wxPayService.refund(request);
    }

    @GetMapping("/refundQuery")
    public WxPayRefundQueryResult refundQuery(@RequestParam(required=false) String transactionId,
                                              @RequestParam(required=false) String outTradeNo,
                                              @RequestParam(required=false) String outRefundNo,
                                              @RequestParam(required=false) String refundId) throws WxPayException {
        return wxPayService.refundQuery(transactionId, outTradeNo, outRefundNo, refundId);
    }

    @PostMapping("/refundQuery")
    public WxPayRefundQueryResult refundQuery(@RequestBody WxPayRefundQueryRequest wxPayRefundQueryRequest) throws WxPayException {
        return wxPayService.refundQuery(wxPayRefundQueryRequest);
    }

    @PostMapping("/notify/order")
    public String parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
        final WxPayOrderNotifyResult notifyResult=wxPayService.parseOrderNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }

    @PostMapping("/notify/refund")
    public String parseRefundNotifyResult(@RequestBody String xmlData) throws WxPayException {
        final WxPayRefundNotifyResult result=wxPayService.parseRefundNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }

    @PostMapping("/notify/scanpay")
    public String parseScanPayNotifyResult(String xmlData) throws WxPayException {
        final WxScanPayNotifyResult result=wxPayService.parseScanPayNotifyResult(xmlData);
        // TODO 根据自己业务场景需要构造返回对象
        return WxPayNotifyResponse.success("成功");
    }

    @PostMapping("/sendRedpack")
    public WxPaySendRedpackResult sendRedpack(@RequestBody WxPaySendRedpackRequest request) throws WxPayException {
        return wxPayService.sendRedpack(request);
    }

    @GetMapping("/queryRedpack/{mchBillNo}")
    public WxPayRedpackQueryResult queryRedpack(@PathVariable String mchBillNo) throws WxPayException {
        return wxPayService.queryRedpack(mchBillNo);
    }

    public byte[] createScanPayQrcodeMode1(String productId, File logoFile, Integer sideLength) {
        return wxPayService.createScanPayQrcodeMode1(productId, logoFile, sideLength);
    }

    public String createScanPayQrcodeMode1(String productId) {
        return wxPayService.createScanPayQrcodeMode1(productId);
    }

    public byte[] createScanPayQrcodeMode2(String codeUrl, File logoFile, Integer sideLength) {
        return wxPayService.createScanPayQrcodeMode2(codeUrl, logoFile, sideLength);
    }

    @PostMapping("/report")
    public void report(@RequestBody WxPayReportRequest request) throws WxPayException {
        wxPayService.report(request);
    }

    @GetMapping("/downloadBill/{billDate}/{billType}/{tarType}/{deviceInfo}")
    public WxPayBillResult downloadBill(@PathVariable String billDate, @PathVariable String billType,
                                        @PathVariable String tarType, @PathVariable String deviceInfo) throws WxPayException {
        return wxPayService.downloadBill(billDate, billType, tarType, deviceInfo);
    }

    @PostMapping("/downloadBill")
    public WxPayBillResult downloadBill(WxPayDownloadBillRequest wxPayDownloadBillRequest) throws WxPayException {
        return wxPayService.downloadBill(wxPayDownloadBillRequest);
    }

    @PostMapping("/micropay")
    public WxPayMicropayResult micropay(@RequestBody WxPayMicropayRequest request) throws WxPayException {
        return wxPayService.micropay(request);
    }

    @PostMapping("/reverseOrder")
    public WxPayOrderReverseResult reverseOrder(@RequestBody WxPayOrderReverseRequest request) throws WxPayException {
        return wxPayService.reverseOrder(request);
    }

    @GetMapping("/getSandboxSignKey")
    public String getSandboxSignKey() throws WxPayException {
        return wxPayService.getSandboxSignKey();
    }

    @PostMapping("/sendCoupon")
    public WxPayCouponSendResult sendCoupon(@RequestBody WxPayCouponSendRequest request) throws WxPayException {
        return wxPayService.sendCoupon(request);
    }

    @PostMapping("/queryCouponStock")
    public WxPayCouponStockQueryResult queryCouponStock(@RequestBody WxPayCouponStockQueryRequest request) throws WxPayException {
        return wxPayService.queryCouponStock(request);
    }

    @PostMapping("/queryCouponInfo")
    public WxPayCouponInfoQueryResult queryCouponInfo(@RequestBody WxPayCouponInfoQueryRequest request) throws WxPayException {
        return wxPayService.queryCouponInfo(request);
    }

    @PostMapping("/queryComment")
    public String queryComment(Date beginDate, Date endDate, Integer offset, Integer limit) throws WxPayException {
        return wxPayService.queryComment(beginDate, endDate, offset, limit);
    }
}
