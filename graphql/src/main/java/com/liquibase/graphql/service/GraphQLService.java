package com.liquibase.graphql.service;

import com.liquibase.graphql.service.datafetcher.AllBookDataFetcher;
import com.liquibase.graphql.service.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

@Service
public class GraphQLService {

    @Value("classpath:book.graphql")
    Resource resource;

    private GraphQL graphQL;
    @Autowired
    private AllBookDataFetcher allBookDataFetcher;
    @Autowired
    private BookDataFetcher getBookByIdDataFetcher;

    // load schema start up
    @PostConstruct
    private void loadSchema() throws Exception{
//        get the schema
        File file = resource.getFile();
//        parse schema
        TypeDefinitionRegistry parse = new SchemaParser().parse(file);
        RuntimeWiring wiring =buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(parse, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query",typewiring-> typewiring
                        .dataFetcher("allBooks",allBookDataFetcher)
                        .dataFetcher("bookById",getBookByIdDataFetcher))
                .build();
    }
    public GraphQL getGraphQL() {
        return graphQL;
    }
}
