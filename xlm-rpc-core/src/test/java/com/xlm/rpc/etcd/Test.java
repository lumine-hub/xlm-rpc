package com.xlm.rpc.etcd;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LocalTime time1 = LocalTime.now();
        Client client = Client.builder().endpoints("http://localhost:2379")
                .build();

        KV kvClient = client.getKVClient();
        ByteSequence key = ByteSequence.from("test_key".getBytes());
        ByteSequence value = ByteSequence.from("test_value".getBytes());

        kvClient.put(key, value).get();

        CompletableFuture<GetResponse> getFuture = kvClient.get(key);

        GetResponse response = getFuture.get();
        System.out.println(response);
        kvClient.delete(key).get();
        LocalTime time2 = LocalTime.now();
        Duration duration = Duration.between(time1, time2);
        System.out.println("时间差: " + duration.toMillis() + " 毫秒");
    }
}
