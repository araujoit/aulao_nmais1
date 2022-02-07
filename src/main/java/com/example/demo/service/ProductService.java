package com.example.demo.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ProductDTO;
import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;

	/**
	 * Resolução:
	 * "Isso é o que o Martin Fowler chama naquele livro antigo de Arquitetura Corporativa de Mapa de Identidade. Não busca no banco de dados o mesmo objeto mais de uma vez no mesmo contexto"
	 * 
	 * @param pageRequest
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<ProductDTO> find(PageRequest pageRequest) {
		Page<Product> page = repository.findAll(pageRequest);
		// busca os objetos, disponibilizando-os na memória, para que o JPA, de forma inteligente, os obtenha
		repository.findProductsCategories(page.stream().collect(Collectors.toList()));
		return page.map(x -> new ProductDTO(x));
	}
}
