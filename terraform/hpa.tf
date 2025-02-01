### HPA PARA O SERVIÇO DE produto ###
resource "kubernetes_horizontal_pod_autoscaler" "produto_hpa" {
  metadata {
    name = "produto-hpa"
  }

  spec {
    scale_target_ref {
      kind        = "Deployment"
      name        = kubernetes_deployment.produto-api.metadata[0].name
      api_version = "apps/v1"
    }

    min_replicas = 1
    max_replicas = 10

    metric {
      type = "Resource"
      resource {
        name = "cpu"
        target {
          type                = "Utilization"
          average_utilization = 50
        }
      }
    }
  }
}