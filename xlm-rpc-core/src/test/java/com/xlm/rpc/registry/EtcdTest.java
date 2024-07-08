package com.xlm.rpc.registry;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class EtcdTest {

    @Test
    public void test() throws Exception {
        Client client = Client.builder().connectTimeout(Duration.ofSeconds(3L)).endpoints("http://localhost:2379")
                .build();

        KV kvClient = client.getKVClient();
        ByteSequence key = ByteSequence.from("test_key".getBytes());
        ByteSequence value = ByteSequence.from("test_value".getBytes());

        kvClient.put(key, value).get();

        CompletableFuture<GetResponse> getFuture = kvClient.get(key);
        String retValue = getFuture.get().getKvs().get(0).getValue().toString(StandardCharsets.UTF_8);
        System.out.println(retValue);

        kvClient.delete(key).get();

    }
}
