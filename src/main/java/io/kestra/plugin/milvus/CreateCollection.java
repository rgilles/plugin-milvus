package io.kestra.plugin.milvus;

import io.kestra.core.models.annotations.Plugin;
import io.kestra.core.models.property.Property;
import io.kestra.core.models.tasks.RunnableTask;
import io.kestra.core.models.tasks.Task;
import io.kestra.core.runners.RunContext;
import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.service.collection.request.CreateCollectionReq;
import io.milvus.v2.service.database.request.CreateDatabaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Schema(
    title = "Create a collection with field and optional index",
    description = "TODO"
)
@Plugin(
    examples = {
        @io.kestra.core.models.annotations.Example(
            title = "Simple revert",
            code = {"format: \"Text to be reverted\""}
        )
    }
)
public class CreateCollection extends Task  implements RunnableTask<CreateCollection.Output> {

    @Schema(
        title = "Connection URI"
    )
    private Property<@NotBlank String> connectionUri;

    @Schema(
        title = "Name of the collection to create"
    )
    private Property<@NotBlank String> database;

    @Schema(
        title = "Connection URI"
    )
    private Property<@NotBlank String> token;
    @Schema(
        title = "Connection URI"
    )
    private Property<@NotBlank String> collectionName;

    @Override
    public Output run(RunContext runContext) throws Exception {
        String rConnectionUri = runContext.render(connectionUri).as(String.class).orElseThrow();

        String rCollectionName = runContext.render(this.collectionName).as(String.class).orElseThrow();

        String rToken = runContext.render(this.token).as(String.class).orElseThrow();
        String rDatabase = runContext.render(this.database).as(String.class).orElseThrow();;

        ConnectConfig config = ConnectConfig.builder()
            .uri(rConnectionUri)
            .build();
        MilvusClientV2 client = new MilvusClientV2(config);
        CreateDatabaseReq createDatabaseReq = CreateDatabaseReq.builder()
            .databaseName(rDatabase)
            .build();
        client.createDatabase(createDatabaseReq);
        runContext.logger().info("Database {} created", rDatabase);
//        CreateCollectionReq.CollectionSchema collectionSchema = CreateCollectionReq.CollectionSchema.builder()
//            .build();
//        CreateCollectionReq requestCreate = CreateCollectionReq.builder()
//            .collectionName(rCollectionName)
//            .collectionSchema(collectionSchema)
//            .build();
//        client.createCollection(requestCreate);
        runContext.logger().info("Collection {} created", rCollectionName);
        return Output.builder().build();
    }

    /**
     * Input or Output can be nested as you need
     */
    @Builder
    @Getter
    public static class Output implements io.kestra.core.models.tasks.Output {
    }
}
