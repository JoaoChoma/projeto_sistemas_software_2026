package br.edu.semana16.pedidos.service;

import br.edu.semana16.pedidos.model.Pedido;
import br.edu.semana16.pedidos.repository.PedidoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PedidoService {

  private final PedidoRepository pedidoRepository;

  public PedidoService(PedidoRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
  }

  public List<Pedido> listar() {
    return pedidoRepository.findAll();
  }

  public Pedido buscarPorId(Long id) {
    return pedidoRepository
      .findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido nao encontrado."));
  }

  public Pedido criar(Pedido pedido) {
    pedido.setId(null);
    return pedidoRepository.save(pedido);
  }

  public Pedido atualizar(Long id, Pedido dadosPedido) {
    Pedido pedido = buscarPorId(id);

    pedido.setCliente(dadosPedido.getCliente());
    pedido.setProduto(dadosPedido.getProduto());
    pedido.setQuantidade(dadosPedido.getQuantidade());
    pedido.setValor(dadosPedido.getValor());
    pedido.setStatus(dadosPedido.getStatus());

    return pedidoRepository.save(pedido);
  }

  public void remover(Long id) {
    Pedido pedido = buscarPorId(id);
    pedidoRepository.delete(pedido);
  }
}
