package io.kestra.plugin.milvus;

import io.kestra.core.junit.annotations.KestraTest;
import io.kestra.core.models.property.Property;
import io.kestra.core.runners.RunContext;
import io.kestra.core.runners.RunContextFactory;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@KestraTest
public class CreateCollectionTest {
    @Inject
    private RunContextFactory runContextFactory;


    @Test
    void run() throws Exception {
        RunContext runContext = runContextFactory.of(Map.of("variable", "John Doe"));

        CreateCollection createCollection = CreateCollection.builder()
            .connectionUri(Property.ofValue("http://localhost:19530"))
            .token(Property.ofValue("root:Milvus"))
            .database(Property.ofValue("my_db"))
            .collectionName(Property.ofValue("test"))
            .build();

        CreateCollection.Output runOutput = createCollection.run(runContext);


//        Example task = Example.builder()
//            .format(new Property<>("Hello {{ variable }}"))
//            .build();
//
//        Example.Output runOutput = task.run(runContext);

        assertThat(runOutput, notNullValue());
    }

}
