package org.example.pipeline;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class MainTest
        extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MainTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MainTest.class);
    }

    /**
     * Check if the JSON document was correctly added
     */
    public void testDocument() {
        Bucket bucket = Database.getBucket("books");

        JsonDocument doc = bucket.get("minecraft");
        assertNotNull(doc);

        JsonObject jsonObject = doc.content();
        assertNotNull(jsonObject);

        System.out.println(jsonObject);

        assertTrue(jsonObject.containsKey("isbn"));
        assertTrue(jsonObject.containsKey("name"));
        assertTrue(jsonObject.containsKey("cost"));

        assertTrue(jsonObject.getString("isbn").equals("978-1-4919-1889-0"));
        assertTrue(jsonObject.getString("name").equals("Minecraft Modding with Forge"));
        assertTrue(jsonObject.getString("cost").equals("29.99"));
    }
}
