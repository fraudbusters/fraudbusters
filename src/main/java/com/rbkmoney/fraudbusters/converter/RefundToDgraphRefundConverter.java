package com.rbkmoney.fraudbusters.converter;

import com.rbkmoney.damsel.domain.PaymentTool;
import com.rbkmoney.damsel.fraudbusters.*;
import com.rbkmoney.fraudbusters.domain.dgraph.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.fraudbusters.constant.ClickhouseUtilsValue.UNKNOWN;

@Component
@RequiredArgsConstructor
public class RefundToDgraphRefundConverter implements Converter<Refund, DgraphRefund> {

    @Override
    public DgraphRefund convert(Refund refund) {
        DgraphRefund dgraphRefund = new DgraphRefund();
        dgraphRefund.setRefundId(refund.getId());
        dgraphRefund.setPaymentId(refund.getPaymentId());
        dgraphRefund.setCreatedAt(refund.getEventTime());
        dgraphRefund.setAmount(refund.getCost().getAmount());
        dgraphRefund.setCurrency(refund.getCost().getCurrency().getSymbolicCode());
        dgraphRefund.setStatus(refund.getStatus().name());
        dgraphRefund.setPayerType(refund.getPayerType() == null ? null : refund.getPayerType().name());
        dgraphRefund.setParty(convertParty(refund));
        dgraphRefund.setShop(convertShop(refund));
        dgraphRefund.setCardToken(convertToken(refund));
        dgraphRefund.setSourcePayment(convertPayment(refund));

        ClientInfo clientInfo = refund.getClientInfo();
        if (clientInfo != null) {
            dgraphRefund.setContactEmail(clientInfo.getEmail() == null ? null : convertEmail(refund));
            dgraphRefund.setFingerprint(clientInfo.getFingerprint() == null ? null : convertFingerprint(refund));
            dgraphRefund.setOperationIp(clientInfo.getIp() == null ? null : convertIp(refund));
        }
        PaymentTool paymentTool = refund.getPaymentTool();
        dgraphRefund.setBin(paymentTool.isSetBankCard() ? convertBin(refund) : null);
        return dgraphRefund;
    }

    private DgraphToken convertToken(Refund refund) {
        DgraphToken dgraphToken = new DgraphToken();
        PaymentTool paymentTool = refund.getPaymentTool();
        dgraphToken.setTokenId(paymentTool.isSetBankCard() ? paymentTool.getBankCard().getToken() : UNKNOWN);
        dgraphToken.setMaskedPan(paymentTool.isSetBankCard() ? paymentTool.getBankCard().getLastDigits() : UNKNOWN);
        dgraphToken.setLastActTime(refund.getEventTime());
        return dgraphToken;
    }

    private DgraphEmail convertEmail(Refund refund) {
        DgraphEmail dgraphEmail = new DgraphEmail();
        dgraphEmail.setUserEmail(refund.getClientInfo().getEmail());
        dgraphEmail.setLastActTime(refund.getEventTime());
        return dgraphEmail;
    }

    private DgraphFingerprint convertFingerprint(Refund refund) {
        DgraphFingerprint dgraphFingerprint = new DgraphFingerprint();
        dgraphFingerprint.setFingerprintData(refund.getClientInfo().getFingerprint());
        dgraphFingerprint.setLastActTime(refund.getEventTime());
        return dgraphFingerprint;
    }

    private DgraphParty convertParty(Refund refund) {
        DgraphParty partyShop = new DgraphParty();
        ReferenceInfo referenceInfo = refund.getReferenceInfo();
        MerchantInfo merchantInfo = refund.getReferenceInfo().getMerchantInfo();
        partyShop.setPartyId(referenceInfo.isSetMerchantInfo() ? merchantInfo.getPartyId() : UNKNOWN);
        return partyShop;
    }

    private DgraphShop convertShop(Refund refund) {
        DgraphShop shop = new DgraphShop();
        ReferenceInfo referenceInfo = refund.getReferenceInfo();
        MerchantInfo merchantInfo = refund.getReferenceInfo().getMerchantInfo();
        shop.setShopId(referenceInfo.isSetMerchantInfo() ? merchantInfo.getShopId() : UNKNOWN);
        return shop;
    }

    private DgraphIp convertIp(Refund refund) {
        DgraphIp dgraphIp = new DgraphIp();
        dgraphIp.setIp(refund.getClientInfo().getIp());
        return dgraphIp;
    }

    private DgraphPayment convertPayment(Refund refund) {
        DgraphPayment dgraphPayment = new DgraphPayment();
        dgraphPayment.setPaymentId(refund.getPaymentId());
        return dgraphPayment;
    }

    private DgraphBin convertBin(Refund refund) {
        DgraphBin dgraphBin = new DgraphBin();
        PaymentTool paymentTool = refund.getPaymentTool();
        dgraphBin.setBin(paymentTool.getBankCard().getBin());
        return dgraphBin;
    }

}
