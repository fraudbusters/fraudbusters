package com.rbkmoney.fraudbusters.dgraph.aggregates;

import com.rbkmoney.fraudbusters.config.dgraph.TemplateConfig;
import com.rbkmoney.fraudbusters.fraud.constant.DgraphEntity;
import com.rbkmoney.fraudbusters.fraud.constant.PaymentCheckedField;
import com.rbkmoney.fraudbusters.fraud.payment.aggregator.dgraph.DgraphAggregationQueryBuilderService;
import com.rbkmoney.fraudbusters.fraud.payment.resolver.DgraphEntityResolver;
import com.rbkmoney.fraudbusters.fraud.payment.resolver.DgraphQueryConditionResolver;
import com.rbkmoney.fraudbusters.service.TemplateService;
import com.rbkmoney.fraudbusters.service.TemplateServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

import static com.rbkmoney.fraudbusters.dgraph.aggregates.data.DgraphRefundCountQueryBuilderServiceTestData.*;
import static com.rbkmoney.fraudbusters.util.DgraphTestAggregationUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DgraphRefundCountQueryBuilderServiceTest {

    private TemplateService templateService = new TemplateServiceImpl(new TemplateConfig().velocityEngine());

    private DgraphAggregationQueryBuilderService aggregationQueryBuilderService =
            new DgraphAggregationQueryBuilderService(
                    new DgraphEntityResolver(),
                    new DgraphQueryConditionResolver(),
                    templateService
            );

    @Test
    public void getRefundsCountQueryByTokenRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.TOKEN,
                Map.of(DgraphEntity.TOKEN, Set.of(PaymentCheckedField.CARD_TOKEN))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_TOKEN_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByTokenRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.TOKEN,
                createTestUsualDgraphEntityMap(DgraphEntity.TOKEN, PaymentCheckedField.CARD_TOKEN)
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_TOKEN_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByTokenRootWithFullDatasetTest() {
        String query = getRefundsCountQuery(DgraphEntity.TOKEN, createTestFullDgraphEntityMap());
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_TOKEN_ROOT_WITH_FULL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByEmailRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.EMAIL,
                Map.of(DgraphEntity.EMAIL, Set.of(PaymentCheckedField.EMAIL))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_EMAIL_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByEmailRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.EMAIL,
                createTestUsualDgraphEntityMap(DgraphEntity.EMAIL, PaymentCheckedField.EMAIL)
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_EMAIL_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByEmailRootWithFullDatasetTest() {
        String query = getRefundsCountQuery(DgraphEntity.EMAIL, createTestFullDgraphEntityMap());
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_EMAIL_ROOT_WITH_FULL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByIpRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.IP,
                Map.of(DgraphEntity.IP, Set.of(PaymentCheckedField.IP))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_IP_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByIpRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.IP,
                createTestUsualDgraphEntityMap(DgraphEntity.IP, PaymentCheckedField.IP)
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_IP_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByIpRootWithFullDatasetTest() {
        String query = getRefundsCountQuery(DgraphEntity.IP, createTestFullDgraphEntityMap());
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_IP_ROOT_WITH_FULL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByFingerprintRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.FINGERPRINT,
                Map.of(DgraphEntity.FINGERPRINT, Set.of(PaymentCheckedField.FINGERPRINT))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_FINGERPRINT_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByFingerprintRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.FINGERPRINT,
                createTestUsualDgraphEntityMap(DgraphEntity.FINGERPRINT, PaymentCheckedField.FINGERPRINT)
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_FINGERPRINT_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByFingerprintRootWithFullDatasetTest() {
        String query = getRefundsCountQuery(DgraphEntity.FINGERPRINT, createTestFullDgraphEntityMap());
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_FINGERPRINT_ROOT_WITH_FULL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByCountryBankRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.COUNTRY,
                Map.of(DgraphEntity.COUNTRY, Set.of(PaymentCheckedField.COUNTRY_BANK))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_COUNTRY_BANK_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByCountryBankRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.COUNTRY,
                createTestUsualDgraphEntityMap(DgraphEntity.COUNTRY, PaymentCheckedField.COUNTRY_BANK)
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_COUNTRY_BANK_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByCountryBankRootWithFullDatasetTest() {
        String query = getRefundsCountQuery(DgraphEntity.COUNTRY, createTestFullDgraphEntityMap());
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_COUNTRY_BANK_ROOT_WITH_FULL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByCountryIpRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.IP,
                Map.of(DgraphEntity.IP, Set.of(PaymentCheckedField.COUNTRY_IP))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_COUNTRY_IP_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByCountryIpRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.IP,
                createTestUsualDgraphEntityMap(DgraphEntity.IP, PaymentCheckedField.COUNTRY_IP)
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_COUNTRY_IP_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByCountryIpRootWithFullDatasetTest() {
        String query = getRefundsCountQuery(DgraphEntity.IP, createTestFullDgraphEntityMap());
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_COUNTRY_IP_ROOT_WITH_FULL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByPanRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.TOKEN,
                Map.of(DgraphEntity.TOKEN, Set.of(PaymentCheckedField.PAN))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_PAN_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByPanRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.TOKEN,
                createTestUsualDgraphEntityMap(DgraphEntity.TOKEN, PaymentCheckedField.PAN)
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_PAN_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByPanRootWithFullDatasetTest() {
        String query = getRefundsCountQuery(DgraphEntity.TOKEN, createTestFullDgraphEntityMap());
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_PAN_ROOT_WITH_FULL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByBinRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.BIN,
                Map.of(DgraphEntity.BIN, Set.of(PaymentCheckedField.BIN))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_BIN_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByBinRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.BIN,
                createTestUsualDgraphEntityMap(DgraphEntity.BIN, PaymentCheckedField.BIN)
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_BIN_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByBinRootWithFullDatasetTest() {
        String query = getRefundsCountQuery(DgraphEntity.BIN, createTestFullDgraphEntityMap());
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_BIN_ROOT_WITH_FULL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByCurrencyRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.REFUND,
                Map.of(DgraphEntity.REFUND, Set.of(PaymentCheckedField.CURRENCY))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_CURRENCY_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByCurrencyRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.REFUND,
                createTestUsualDgraphEntityMap(DgraphEntity.REFUND, PaymentCheckedField.CURRENCY)
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_CURRENCY_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByCurrencyRootWithFullDatasetTest() {
        var dgraphEntityMap = createTestFullDgraphEntityMap();
        dgraphEntityMap.put(DgraphEntity.REFUND, Set.of(PaymentCheckedField.CURRENCY));
        String query = getRefundsCountQuery(DgraphEntity.REFUND, dgraphEntityMap);
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_CURRENCY_ROOT_WITH_FULL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByShopIdRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.PARTY_SHOP,
                Map.of(DgraphEntity.PARTY_SHOP, Set.of(PaymentCheckedField.SHOP_ID))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_SHOP_ID_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByShopIdRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.PARTY_SHOP,
                createTestUsualDgraphEntityMap(DgraphEntity.BIN, PaymentCheckedField.BIN)
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_SHOP_ID_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByShopIdRootWithFullDatasetTest() {
        String query = getRefundsCountQuery(DgraphEntity.PARTY_SHOP, createTestFullDgraphEntityMap());
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_SHOP_ID_ROOT_WITH_FULL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByPartyIdRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.PARTY_SHOP,
                Map.of(DgraphEntity.PARTY_SHOP, Set.of(PaymentCheckedField.PARTY_ID))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_PARTY_ID_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByPartyIdRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.PARTY_SHOP,
                Map.of(
                        DgraphEntity.PARTY_SHOP, Set.of(PaymentCheckedField.PARTY_ID),
                        DgraphEntity.BIN, Set.of(PaymentCheckedField.BIN)
                )
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_PARTY_ID_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByPartyIdRootWithFullDatasetTest() {
        String query = getRefundsCountQuery(DgraphEntity.PARTY_SHOP, createTestFullDgraphEntityMap());
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_PARTY_ID_ROOT_WITH_FULL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByMobileRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.PAYMENT,
                Map.of(DgraphEntity.PAYMENT, Set.of(PaymentCheckedField.MOBILE))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_MOBILE_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByMobileRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.PAYMENT,
                createTestUsualDgraphEntityMap(DgraphEntity.PAYMENT, PaymentCheckedField.MOBILE)
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_MOBILE_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByMobileRootWithFullDatasetTest() {
        String query = getRefundsCountQuery(DgraphEntity.PAYMENT, createTestFullDgraphEntityMap());
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_MOBILE_ROOT_WITH_FULL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByRecurrentRootWithMinimalDataTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.PAYMENT,
                Map.of(DgraphEntity.PAYMENT, Set.of(PaymentCheckedField.RECURRENT))
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_RECURRENT_ROOT_WITH_MINIMAL_DATA, query);
    }

    @Test
    public void getRefundsCountQueryByRecurrentRootWithUsualDatasetTest() {
        String query = getRefundsCountQuery(
                DgraphEntity.PAYMENT,
                createTestUsualDgraphEntityMap(DgraphEntity.PAYMENT, PaymentCheckedField.RECURRENT)
        );
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_RECURRENT_ROOT_WITH_USUAL_DATASET, query);
    }

    @Test
    public void getRefundsCountQueryByRecurrentRootWithFullDatasetTest() {
        String query = getRefundsCountQuery(DgraphEntity.PAYMENT, createTestFullDgraphEntityMap());
        assertNotNull(query);
        assertEquals(REFUNDS_COUNT_QUERY_BY_RECURRENT_ROOT_WITH_FULL_DATASET, query);
    }

    private String getRefundsCountQuery(DgraphEntity rootEntity,
                                        Map<DgraphEntity, Set<PaymentCheckedField>> dgraphEntitySetMap) {
        return aggregationQueryBuilderService.getCountQuery(
                rootEntity,
                DgraphEntity.REFUND,
                dgraphEntitySetMap,
                createTestPaymentModel(),
                Instant.parse("2021-10-28T19:40:54.000000Z"),
                Instant.parse("2021-10-28T19:47:54.000000Z"),
                "successful"
        );
    }

}
