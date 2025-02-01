### OUTPUT PARA O SERVIÇO DE produto ###
output "produto_service_url" {
  value = kubernetes_service.produto_service.metadata[0].name
}