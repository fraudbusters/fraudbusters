package com.rbkmoney.fraudbusters.factory.stream;

import com.rbkmoney.fraudbusters.domain.FraudResult;
import com.rbkmoney.fraudbusters.serde.FraudoModelSerde;
import com.rbkmoney.fraudo.FraudoParser;
import com.rbkmoney.fraudo.aggregator.CountAggregator;
import com.rbkmoney.fraudo.aggregator.SumAggregator;
import com.rbkmoney.fraudo.aggregator.UniqueValueAggregator;
import com.rbkmoney.fraudo.constant.ResultStatus;
import com.rbkmoney.fraudo.factory.FastFraudVisitorFactory;
import com.rbkmoney.fraudo.factory.FraudVisitorFactory;
import com.rbkmoney.fraudo.finder.InListFinder;
import com.rbkmoney.fraudo.model.FraudModel;
import com.rbkmoney.fraudo.resolver.CountryResolver;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@RequiredArgsConstructor
public class ConcreteStreamFactory implements TemplateStreamFactory {

    @Value("${kafka.concrete.stream.topic}")
    private String readTopic;
    @Value("${kafka.result.stream.topic}")
    private String resultTopic;

    private final FraudVisitorFactory fraudVisitorFactory = new FastFraudVisitorFactory();
    private final FraudoModelSerde fraudoModelSerde = new FraudoModelSerde();

    private final CountAggregator countAggregator;
    private final SumAggregator sumAggregator;
    private final UniqueValueAggregator uniqueValueAggregator;
    private final CountryResolver countryResolver;
    private final InListFinder blackListFinder;
    private final InListFinder whiteListFinder;

    @Override
    public KafkaStreams create(Properties streamsConfiguration, FraudoParser.ParseContext parseContext) {
        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(readTopic, Consumed.with(Serdes.String(), fraudoModelSerde))
                .mapValues(fraudModel -> new FraudResult(fraudModel, applyRules(parseContext, fraudModel)))
                .to(resultTopic);
        return new KafkaStreams(builder.build(), streamsConfiguration);
    }

    private ResultStatus applyRules(FraudoParser.ParseContext parseContext, FraudModel fraudModel) {
        return (ResultStatus) fraudVisitorFactory.createVisitor(fraudModel, countAggregator, sumAggregator,
                uniqueValueAggregator, countryResolver, blackListFinder, whiteListFinder).visit(parseContext);
    }
}
