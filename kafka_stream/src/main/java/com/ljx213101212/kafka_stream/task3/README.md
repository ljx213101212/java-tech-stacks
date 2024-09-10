## Quick Start

1. Initiate the topic
```commandline
kafka-topics.sh --create --topic task3-1 --bootstrap-server localhost:9092,localhost:9093
kafka-topics.sh --create --topic task3-2 --bootstrap-server localhost:9092,localhost:9093

./kafka-configs.sh  --bootstrap-server localhost:9092,localhost:9093 --entity-type topics --entity-name task3-1 --alter --add-config retention.ms=3600000
./kafka-configs.sh  --bootstrap-server localhost:9092,localhost:9093 --entity-type topics --entity-name task3-2 --alter --add-config retention.ms=10000

```

2. Produce the messages for streaming
```commandline
kafka-console-producer.sh --broker-list localhost:9092,localhost:9093 --topic task3-1 --property "parse.key=true" --property "key.separator=:"
>> 1:1001: today is
>> 1:1002: tomorrow will be
kafka-console-producer.sh --broker-list localhost:9092,localhost:9093 --topic task3-2 --property "parse.key=true" --property "key.separator=:"
>> 1:1001: a good day
>> 1:1002: better

message pattern explaination:
{dummy id (we can always put 1)}:{stream group id}:{message}
```

3. Join the streaming message group by stream group id

> message pattern explaination:
> {dummy id (we can always put 1)}:{stream group id}:{message}

>1:1001: today is


- topic3-1 (stream 1)
```
$ kafka-console-producer.sh --broker-list localhost:9092,localhost:9093 --topic task3-1 --property "parse.key=true" --property "key.separator=:"
1:1001: today is
1:1002: tomorrow will be
```
- topic3-2 (stream 2)
```commandline
$ kafka-console-producer.sh --broker-list localhost:9092,localhost:9093 --topic task3-2 --property "parse.key=true" --property "key.separator=:"
>1:1001: a good day
>1:1002: better
```

- Result (output from console log)
```commandline
Join Key: 1001, Value:  today is +  a good day
Join Key: 1002, Value:  morrow will be +  better
```
