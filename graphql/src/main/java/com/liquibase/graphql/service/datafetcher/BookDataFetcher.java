package com.liquibase.graphql.service.datafetcher;

import com.liquibase.graphql.model.Book;
import com.liquibase.graphql.repository.BookRepository;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDataFetcher implements DataFetcher<Book> {

    @Autowired
    BookRepository bookRepository;
    @Override
    public Book get(DataFetchingEnvironment environment) throws Exception {
        String id = environment.getArgument("id");
        return bookRepository.findById(Integer.valueOf(id)).get();
    }
}
