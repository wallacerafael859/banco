package com.example.banco.controller;

import com.example.banco.model.Cliente;
import com.example.banco.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //  LISTAR TODOS OS CLIENTES
    @GetMapping
    public List<Cliente> listarTodosOsClientes() {
        return clienteService.listarTodos();
    }

    //  BUSCAR PELO ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> listarPorId(@PathVariable Integer id) {
        Optional<Cliente> cliente = clienteService.listarPorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // SALVAR CLIENTE
    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.salvar(cliente);
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
    }

    //  DELETAR CLIENTE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Integer id) {
        if (clienteService.listarPorId(id).isPresent()) {
            clienteService.deletar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ATUALIZAR CLIENTE
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable Integer id, @RequestBody Cliente clienteDados) {
        return clienteService.listarPorId(id).map(clienteExistente -> {
            clienteExistente.setNome(clienteDados.getNome());
            clienteExistente.setCpf(clienteDados.getCpf());
            Cliente clienteAtualizado = clienteService.salvar(clienteExistente);
            return ResponseEntity.ok(clienteAtualizado);
        }).orElseGet((() -> ResponseEntity.notFound().build()));
    }
}