### SERVICE PARA O SERVIÇO DE produto ###
resource "kubernetes_service" "produto_service" {
  metadata {
    name = "produto-service"
    labels = {
      app = "produto"
    }
  }

  spec {
    selector = {
      app = "produto"
    }

    port {
      port        = 80
      target_port = 8080
    }

    type = "ClusterIP"
  }
}