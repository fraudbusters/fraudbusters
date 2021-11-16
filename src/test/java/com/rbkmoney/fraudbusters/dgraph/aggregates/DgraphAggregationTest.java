package com.rbkmoney.fraudbusters.dgraph.aggregates;

import com.rbkmoney.damsel.fraudbusters.Payment;
import com.rbkmoney.fraudbusters.dgraph.DgraphAbstractIntegrationTest;
import com.rbkmoney.fraudbusters.dgraph.insert.model.Aggregates;
import com.rbkmoney.fraudbusters.factory.properties.OperationProperties;
import com.rbkmoney.fraudbusters.fraud.constant.DgraphTargetAggregationType;
import com.rbkmoney.fraudbusters.fraud.constant.DgraphEntity;
import com.rbkmoney.fraudbusters.fraud.constant.PaymentCheckedField;
import com.rbkmoney.fraudbusters.fraud.model.DgraphAggregationQueryModel;
import com.rbkmoney.fraudbusters.fraud.model.PaymentModel;
import com.rbkmoney.fraudbusters.fraud.payment.resolver.DgraphEntityResolver;
import com.rbkmoney.fraudbusters.fraud.payment.resolver.DgraphQueryConditionResolver;
import com.rbkmoney.fraudbusters.serde.PaymentDeserializer;
import com.rbkmoney.fraudo.model.TimeWindow;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.util.Strings;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.io.StringWriter;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.rbkmoney.fraudbusters.factory.TestDgraphObjectFactory.generatePayment;
import static com.rbkmoney.fraudbusters.util.DgraphTestAggregationUtils.createTestPaymentModel;
import static com.rbkmoney.fraudbusters.util.DgraphTestAggregationUtils.createTestTimeWindow;

@Slf4j
@ActiveProfiles("full-prod")
public class DgraphAggregationTest extends DgraphAbstractIntegrationTest {

    @Autowired
    private DgraphEntityResolver dgraphEntityResolver;

    @Autowired
    private DgraphQueryConditionResolver dgraphQueryConditionResolver;

    private static final String KAFKA_PAYMENT_TOPIC = "payment_event";

    @Test
    public void paymentCountAggregationTest() throws Exception {
        PaymentModel testPaymentModel = createTestPaymentModel();
        OperationProperties operationProperties = OperationProperties.builder()
                .tokenId(testPaymentModel.getCardToken())
                .maskedPan(testPaymentModel.getPan())
                .email(testPaymentModel.getEmail())
                .fingerprint(testPaymentModel.getFingerprint())
                .partyId(testPaymentModel.getPartyId())
                .shopId(testPaymentModel.getShopId())
                .bin(testPaymentModel.getBin())
                .ip(testPaymentModel.getIp())
                .country(testPaymentModel.getBinCountryCode())
                .eventTimeDispersion(true)
                .build();
        prepareGraphDb(operationProperties);

        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setCardToken("token1");
        paymentModel.setPartyId("party1");
        paymentModel.setShopId("party1shop2");
        paymentModel.setBin("bin2");

        var countSuccess = countSuccessPayments(
                PaymentCheckedField.CARD_TOKEN,
                paymentModel,
                createTestTimeWindow(),
                List.of(PaymentCheckedField.PARTY_ID, PaymentCheckedField.SHOP_ID, PaymentCheckedField.BIN));

        //getAggregates(query);

        System.out.println("qwe  " + countSuccess);
    }

    public Integer countSuccessPayments(
            PaymentCheckedField checkedField,
            PaymentModel paymentModel,
            TimeWindow timeWindow,
            List<PaymentCheckedField> list) {
        Instant timestamp = paymentModel.getTimestamp() != null
                ? Instant.ofEpochMilli(paymentModel.getTimestamp())
                : Instant.now();
        Instant startWindowTime = timestamp.minusMillis(timeWindow.getStartWindowTime());
        Instant endWindowTime = timestamp.minusMillis(timeWindow.getEndWindowTime());

        DgraphEntity dgraphEntity = dgraphEntityResolver.resolvePaymentCheckedField(checkedField);
        //TODO: подумать нужно ли так делать
        Map<DgraphEntity, Set<PaymentCheckedField>> dgraphEntityMap =
                dgraphEntityResolver.resolvePaymentCheckedFieldsToMap(checkedField, list);
        return 0;
    }

    private void prepareGraphDb(OperationProperties properties) throws Exception {
        producePayments(KAFKA_PAYMENT_TOPIC, generatePayments(5, properties));
        waitingTopic(KAFKA_PAYMENT_TOPIC, PaymentDeserializer.class);

        properties.setShopId(properties.getShopId() + "-2");
        producePayments(KAFKA_PAYMENT_TOPIC, generatePayments(5, properties));

        properties.setBin(properties.getBin() + "-2");
        properties.setIp(properties.getIp() + "-2");
        producePayments(KAFKA_PAYMENT_TOPIC, generatePayments(5, properties));

        properties.setTokenId(properties.getTokenId() + "-2");
        properties.setShopId(properties.getShopId() + "-3");
        producePayments(KAFKA_PAYMENT_TOPIC, generatePayments(7, properties));

        long currentTimeMillis = System.currentTimeMillis();
        while (getCountOfObjects("Payment") < 17
                || System.currentTimeMillis() - currentTimeMillis < 10_000L) {
        }
    }

    private List<Payment> generatePayments(int count, OperationProperties properties) {
        List<Payment> payments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            payments.add(generatePayment(properties, i));
        }
        return payments;
    }

    void producePayments(String topicName, List<Payment> payments)
            throws InterruptedException, ExecutionException {
        try (Producer<String, Payment> producer = createProducer()) {
            for (Payment payment : payments) {
                ProducerRecord<String, Payment> producerRecord =
                        new ProducerRecord<>(topicName, payment.getId(), payment);
                producer.send(producerRecord).get();
            }
        }
    }

    private Aggregates getCountOfPaymentsByToken2() {
        String query = String.format("""
                query all() {
                  aggregates(func: type(Token)) @filter(eq(tokenId, "token1")) @normalize {
                     payments @cascade {
                      count : count(uid)
                      partyShop @filter(eq(shopId, "party1shop2"))
                      bin @filter(eq(cardBin, "bin2"))
                    }
                  }
                }
                """);

        return getAggregates(query);
    }

}