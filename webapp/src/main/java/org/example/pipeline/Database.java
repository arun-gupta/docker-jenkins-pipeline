package org.example.pipeline;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;

/**
 * @author arungupta
 */
public class Database {

    static CouchbaseCluster cluster;
    static Bucket bucket;

    public static final CouchbaseCluster getCluster() {
        if (null == cluster) {
            cluster = CouchbaseCluster.create();
        }
        return cluster;
    }

    public static Bucket getBucket(String bucketName) {
        if (null == bucket) {
            bucket = getCluster().openBucket(bucketName);
        }
        return bucket;
    }
}
