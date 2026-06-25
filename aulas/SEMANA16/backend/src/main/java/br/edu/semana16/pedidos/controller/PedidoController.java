package br.edu.semana16.pedidos.controller;

import br.edu.semana16.pedidos.model.Pedido;
import br.edu.semana16.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

  private final PedidoService pedidoService;

  public PedidoController(PedidoService pedidoService) {
    this.pedidoService = pedidoService;
  }

  @GetMapping
  public List<Pedido> listar() {
    return pedidoService.listar();
  }

  @GetMapping("/{id}")
  public Pedido buscarPorId(@PathVariable Long id) {
    return pedidoService.buscarPorId(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Pedido criar(@Valid @RequestBody Pedido pedido) {
    return pedidoService.criar(pedido);
  }

  @PutMapping("/{id}")
  public Pedido atualizar(@PathVariable Long id, @Valid @RequestBody Pedido dadosPedido) {
    return pedidoService.atualizar(id, dadosPedido);
  }

  @DeleteMapping("/{id}")
  public void remover(@PathVariable Long id) {
    pedidoService.remover(id);
  }
}
