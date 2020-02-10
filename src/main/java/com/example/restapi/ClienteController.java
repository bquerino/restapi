package com.example.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clientes;
	
	@PostMapping
	public Cliente add(@Valid @RequestBody Cliente cliente) {
		return clientes.save(cliente);
	}
	
	@GetMapping
	public List<Cliente> list(){
		return clientes.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Long id){
		Cliente cliente = clientes.getOne(id);
		if (cliente == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cliente);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> update(@PathVariable Long id, @Valid @RequestBody Cliente cliente){
		Cliente exist = clientes.getOne(id);
		if (exist == null) {
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(cliente,  exist, "id");
		exist = clientes.save(exist);
		
		return ResponseEntity.ok(exist);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id){
		Cliente cliente = clientes.getOne(id);
		
		if (cliente == null) {
			return ResponseEntity.notFound().build();
		}
		
		clientes.delete(cliente);
		
		return ResponseEntity.noContent().build();
	}
	

}
