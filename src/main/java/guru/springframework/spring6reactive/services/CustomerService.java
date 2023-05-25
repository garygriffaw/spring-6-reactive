package guru.springframework.spring6reactive.services;

import guru.springframework.spring6reactive.model.CustomerDTO;
import reactor.core.publisher.Flux;

public interface CustomerService {
    Flux<CustomerDTO> listCustomers();
}
