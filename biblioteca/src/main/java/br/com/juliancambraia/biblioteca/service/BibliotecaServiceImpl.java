package br.com.juliancambraia.biblioteca.service;

import br.com.juliancambraia.biblioteca.model.Livro;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BibliotecaServiceImpl implements BibliotecaService {

    Logger logger = LoggerFactory.getLogger(BibliotecaServiceImpl.class);

    private RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "add", fallbackMethod = "fallbackForaddBook")
    public String addLivro(Livro livro) {
        logger.error("chamou o método addLivro na classe Service.");
        String response = restTemplate.postForObject("/livros", livro, String.class);
        return response;
    }

    @Override
    @RateLimiter(name = "add", fallbackMethod = "fallbackForRatelimitBook")
    public String addLivroWithRateLimit(Livro livro) {
        logger.error("Dentro do addLivro RateLimit.");
        String response = restTemplate.postForObject("/livros", livro, String.class);
        return response;
    }

    @Override
    @Retry(name = "get", fallbackMethod = "fallbackRetry")
    public List<Livro> getLivrosList() {
        logger.info("Dentro do getLivroList");

        return restTemplate.getForObject("/livros", List.class);
    }

    @Override
    @Bulkhead(name = "get", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "fallbackBulkhead")
    public List<Livro> getLivrosListBulkhead() {
        logger.error("Dentro do getLivroList bulk head");
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return restTemplate.getForObject("/livros", List.class);
    }

    public String fallbackForaddBook(Livro livro, Throwable t) {
        logger.error("Inside circuit breaker fallbackForaddBook, cause - {}", t.toString());
        return "Inside circuit breaker fallback method. Some error occurred while calling service for adding book";
    }

    public String fallbackForRatelimitBook(Livro livro, Throwable t) {
        logger.error("Inside fallbackForRatelimitBook, cause - {}", t.toString());
        return "Inside fallbackForRatelimitBook method. Some error occurred while calling service for adding book";
    }

    public List<Livro> fallbackRetry(Throwable t) {
        logger.error("Inside fallbackRetry, cause - {}", t.toString());
        Livro livro = new Livro();
        livro.setAutor("Default");
        livro.setStatus("Default");
        livro.setTitulo("Default");
        List<Livro> list = Arrays.asList(livro);
        return list;
    }

    public List<Livro> fallbackBulkhead(Throwable t) {
        logger.error("Inside fallbackBulkhead, cause - {}", t.toString());
        Livro livro = new Livro();
        livro.setAutor("DefaultBulkhead");
        livro.setStatus("DefaultBulkhead");
        livro.setTitulo("DefaultBulkhead");
        List<Livro> list = Arrays.asList(livro);
        return list;
    }
}
