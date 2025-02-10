### HPA PARA O SERVIÇO DE PRODUTO ###
resource "kubernetes_horizontal_pod_autoscaler" "produto_hpa" {
  metadata {
    name = "produto-hpa"
  }

  spec {
    scale_target_ref {
      api_version = "apps/v1"
      kind        = "Deployment"
      name        = kubernetes_deployment.produto-api.metadata[0].name
    }

    min_replicas                      = 1
    max_replicas                      = 10
    target_cpu_utilization_percentage = 70
  }
}